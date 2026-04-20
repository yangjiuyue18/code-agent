package com.campus.agent.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 金融数据查询服务 - NeoData API
 * 支持：股票行情、财务报表、基金净值、指数、板块、宏观经济、外汇、大宗商品
 */
@Service
public class FinanceService {

    private final RestTemplate restTemplate = new RestTemplate();

    // NeoData API 端点（代理）
    private static final String NEODATA_ENDPOINT = "https://copilot.tencent.com/agenttool/v1/neodata";

    /**
     * 自然语言金融数据查询
     * @param query 自然语言查询，如"贵州茅台今日股价"
     * @param token JWT认证令牌
     * @return 格式化的金融数据结果
     */
    public String query(String query, String token) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("query", query);
            requestBody.put("data_type", "all");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(requestBody), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    NEODATA_ENDPOINT,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseAndFormatResult(response.getBody());
            }
            return "查询失败，请稍后重试～";

        } catch (Exception e) {
            e.printStackTrace();
            return "金融数据查询出错：" + e.getMessage();
        }
    }

    /**
     * 解析并格式化API返回结果
     */
    private String parseAndFormatResult(String jsonStr) {
        try {
            JSONObject result = JSON.parseObject(jsonStr);

            // 检查响应状态
            if (!"200".equals(result.getString("code"))) {
                return "查询失败：" + result.getString("msg");
            }

            JSONObject data = result.getJSONObject("data");
            if (data == null) {
                return "未获取到数据，请换个问法～";
            }

            StringBuilder sb = new StringBuilder();

            // 处理结构化API数据
            JSONObject apiData = data.getJSONObject("apiData");
            if (apiData != null) {
                formatApiData(sb, apiData);
            }

            // 处理文档数据（财经资讯、研报等）
            JSONObject docData = data.getJSONObject("docData");
            if (docData != null && !docData.isEmpty()) {
                if (sb.length() > 0) sb.append("\n\n");
                sb.append("📰 相关资讯：\n");
                formatDocData(sb, docData);
            }

            return sb.length() > 0 ? sb.toString() : "未获取到相关数据，请换个问法～";

        } catch (Exception e) {
            e.printStackTrace();
            return "数据解析出错，请稍后重试～";
        }
    }

    /**
     * 格式化结构化API数据
     */
    private void formatApiData(StringBuilder sb, JSONObject apiData) {
        // 获取标的列表
        JSONArray entities = apiData.getJSONArray("entity");
        if (entities != null && !entities.isEmpty()) {
            sb.append("📊 ");
            for (int i = 0; i < Math.min(entities.size(), 5); i++) {
                JSONObject entity = entities.getJSONObject(i);
                String name = entity.getString("name");
                String code = entity.getString("code");
                if (name != null) {
                    sb.append(name);
                    if (code != null) sb.append("(").append(code).append(")");
                    if (i < entities.size() - 1) sb.append("、");
                }
            }
            sb.append("\n\n");
        }

        // 获取API召回内容
        JSONArray apiRecall = apiData.getJSONArray("apiRecall");
        if (apiRecall != null && !apiRecall.isEmpty()) {
            for (int i = 0; i < Math.min(apiRecall.size(), 3); i++) {
                JSONObject recall = apiRecall.getJSONObject(i);
                String type = recall.getString("type");
                String desc = recall.getString("desc");
                String content = recall.getString("content");

                if (content != null && !content.isEmpty()) {
                    sb.append("🔹 ").append(desc != null ? desc : getTypeName(type)).append("：\n");
                    sb.append(content).append("\n\n");
                }
            }
        }
    }

    /**
     * 格式化文档数据
     */
    private void formatDocData(StringBuilder sb, JSONObject docData) {
        JSONArray docRecall = docData.getJSONArray("docRecall");
        if (docRecall != null && !docRecall.isEmpty()) {
            for (int i = 0; i < Math.min(docRecall.size(), 2); i++) {
                JSONObject group = docRecall.getJSONObject(i);
                String extQuery = group.getString("extQuery");
                JSONArray docs = group.getJSONArray("docList");

                if (docs != null && !docs.isEmpty()) {
                    for (int j = 0; j < Math.min(docs.size(), 2); j++) {
                        JSONObject doc = docs.getJSONObject(j);
                        String title = doc.getString("title");
                        if (title != null) {
                            sb.append("• ").append(title);
                            if (j < docs.size() - 1) sb.append("\n");
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取类型中文名称
     */
    private String getTypeName(String type) {
        if (type == null) return "数据";
        switch (type) {
            case "basic_info": return "行情信息";
            case "product_info": return "基金产品";
            case "manager_info": return "基金经理";
            case "company_info": return "基金公司";
            case "stock_big_event": return "股票大事";
            case "hk_stock_profile": return "港股概况";
            case "plate_stock_info": return "板块龙头";
            case "fund_rank_info": return "基金排行";
            case "fund_history": return "资金历史";
            case "fund_aggregation": return "龙虎榜单";
            default: return "相关数据";
        }
    }

    /**
     * 判断是否为金融查询意图（关键词匹配）
     */
    public boolean isFinanceQuery(String message) {
        if (message == null || message.isEmpty()) return false;

        String lowerMsg = message.toLowerCase();

        // 金融相关关键词
        String[] financeKeywords = {
            "股价", "股票", "行情", "涨跌", "上证", "深证", "创业板",
            "基金", "净值", "基金经理", "申购", "赎回",
            "债券", "可转债", "国债",
            "期货", "原油", "黄金", "大宗商品",
            "汇率", "美元", "欧元", "人民币", "外汇",
            "GDP", "CPI", "PPI", "利率", "降息", "加息", "通胀",
            "财报", "营收", "净利润", "每股收益", "市盈率",
            "市值", "估值", "PE", "PB",
            "板块", "龙头", "概念股", "资金流向", "北向", "南向",
            "龙虎榜", "涨停", "跌停", "停牌",
            "IPO", "新股", "增发", "配股",
            "分红", "送股", "股息",
            "研报", "评级", "目标价", "增持", "减持",
            "贵州茅台", "腾讯", "阿里巴巴", "宁德时代", "比亚迪"
        };

        for (String keyword : financeKeywords) {
            if (lowerMsg.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
