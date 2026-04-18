package com.campus.agent.controller;

import com.campus.agent.service.ChatHistoryService;
import com.campus.agent.service.ChatService;
import com.campus.agent.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * AI对话控制器：核心接口 /agent/chat
 */
@RestController
@RequestMapping("/agent")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatHistoryService chatHistoryService;

    /**
     * AI对话入口（说明书核心接口1）
     * @param content 用户输入内容
     * @param userId  用户ID
     * @return 对话结果
     */
    @PostMapping("/chat")
    public Result<Map<String, Object>> chat(
            @RequestParam String content,
            @RequestParam Long userId,
            @RequestParam(required = false) String docBase64,
            @RequestParam(required = false) String docName) {
        try {
            Map<String, Object> result = chatService.chat(content, userId, docBase64, docName);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("对话失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户对话历史
     * @param userId 用户ID
     * @return 历史消息列表
     */
    @GetMapping("/history")
    public Result<List<Map<String, String>>> getHistory(@RequestParam Long userId) {
        try {
            List<Map<String, String>> history = chatHistoryService.loadHistoryForQwen(userId);
            return Result.success(history);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取历史失败：" + e.getMessage());
        }
    }

    /**
     * 清除用户对话历史
     * @param userId 用户ID
     */
    @PostMapping("/clear-history")
    public Result<Map<String, Object>> clearHistory(@RequestParam Long userId) {
        try {
            chatHistoryService.clearHistory(userId);
            return Result.success(new java.util.HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("清除失败：" + e.getMessage());
        }
    }

    /**
     * 导出聊天记录
     * @param userId 用户ID
     * @param format 格式：md(默认) 或 txt
     */
    @GetMapping("/export")
    public Result<Map<String, String>> exportHistory(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "md") String format) {
        try {
            String content;
            if ("txt".equalsIgnoreCase(format)) {
                content = chatHistoryService.exportHistoryAsText(userId);
            } else {
                content = chatHistoryService.exportHistoryAsMarkdown(userId);
            }
            Map<String, String> result = new java.util.HashMap<>();
            result.put("content", content);
            result.put("format", format);
            result.put("filename", "聊天记录_" + java.time.LocalDate.now() + "." + format);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败：" + e.getMessage());
        }
    }
}
