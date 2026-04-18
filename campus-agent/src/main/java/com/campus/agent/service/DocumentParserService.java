package com.campus.agent.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档解析服务
 * 支持解析 Word、Excel、PDF 文档，提取文本内容
 */
@Service
public class DocumentParserService {

    /**
     * 解析文档，返回文本内容
     * @param fileBytes  文件字节数组
     * @param fileName   文件名（用于判断文件类型）
     * @return 解析后的文本内容，如果解析失败返回 null
     */
    public String parseDocument(byte[] fileBytes, String fileName) {
        if (fileBytes == null || fileBytes.length == 0) {
            return null;
        }

        String lowerName = fileName.toLowerCase();

        try {
            if (lowerName.endsWith(".docx")) {
                return parseDocx(fileBytes);
            } else if (lowerName.endsWith(".doc")) {
                return parseDoc(fileBytes);
            } else if (lowerName.endsWith(".xlsx")) {
                return parseXlsx(fileBytes);
            } else if (lowerName.endsWith(".xls")) {
                return parseXls(fileBytes);
            } else if (lowerName.endsWith(".pdf")) {
                return parsePdf(fileBytes);
            } else {
                return "【不支持的文档格式：" + fileName + "】";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "【文档解析失败：" + fileName + "，" + e.getMessage() + "】";
        }
    }

    /**
     * 解析 .docx 文件（Word 2007+）
     */
    private String parseDocx(byte[] fileBytes) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("【Word文档内容】\n");

        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
             XWPFDocument document = new XWPFDocument(bis)) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText();
                if (text != null && !text.trim().isEmpty()) {
                    content.append(text).append("\n");
                }
            }
        }

        return content.toString();
    }

    /**
     * 解析 .doc 文件（Word 97-2003）
     */
    private String parseDoc(byte[] fileBytes) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("【Word文档内容】\n");

        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
             HWPFDocument document = new HWPFDocument(bis);
             WordExtractor extractor = new WordExtractor(document)) {

            String[] paragraphs = extractor.getParagraphText();
            for (String paragraph : paragraphs) {
                // 去掉多余的空白字符
                String text = paragraph.trim();
                if (!text.isEmpty()) {
                    content.append(text).append("\n");
                }
            }
        }

        return content.toString();
    }

    /**
     * 解析 .xlsx 文件（Excel 2007+）
     */
    private String parseXlsx(byte[] fileBytes) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("【Excel文档内容】\n");

        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
             Workbook workbook = new XSSFWorkbook(bis)) {

            int sheetCount = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetCount; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                content.append("【Sheet: ").append(sheetName).append("】\n");

                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(getCellValue(cell));
                    }
                    if (!rowData.isEmpty()) {
                        content.append(String.join(" | ", rowData)).append("\n");
                    }
                }
                content.append("\n");
            }
        }

        return content.toString();
    }

    /**
     * 解析 .xls 文件（Excel 97-2003）
     */
    private String parseXls(byte[] fileBytes) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("【Excel文档内容】\n");

        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
             Workbook workbook = new HSSFWorkbook(bis)) {

            int sheetCount = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetCount; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                content.append("【Sheet: ").append(sheetName).append("】\n");

                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(getCellValue(cell));
                    }
                    if (!rowData.isEmpty()) {
                        content.append(String.join(" | ", rowData)).append("\n");
                    }
                }
                content.append("\n");
            }
        }

        return content.toString();
    }

    /**
     * 获取单元格的值
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numVal = cell.getNumericCellValue();
                    if (numVal == Math.floor(numVal)) {
                        return String.valueOf((long) numVal);
                    } else {
                        return String.valueOf(numVal);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getCellFormula();
                }
            default:
                return "";
        }
    }

    /**
     * 解析 PDF 文件
     */
    private String parsePdf(byte[] fileBytes) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("【PDF文档内容】\n");

        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
             PDDocument document = PDDocument.load(bis)) {

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);

            int pageCount = document.getNumberOfPages();
            for (int i = 1; i <= pageCount; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String pageText = stripper.getText(document);
                if (pageText != null && !pageText.trim().isEmpty()) {
                    content.append("【第 ").append(i).append(" 页】\n");
                    content.append(pageText).append("\n");
                }
            }
        }

        return content.toString();
    }
}
