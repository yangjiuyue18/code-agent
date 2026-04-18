package com.campus.agent.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通义千问大模型服务类
 * 调用阿里云 DashScope API 实现AI自由对话
 */
@Service
public class QwenService {

    @Value("${qwen.api-key:}")
    private String apiKey;

    @Value("${qwen.model:qwen-plus}")
    private String model;

    @Value("${qwen.vl-model:qwen-vl-plus}")
    private String vlModel;

    @Value("${qwen.url:https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 带系统提示词的对话（校园管家模式）
     * @param userMessage 用户输入
     * @param systemPrompt 系统提示词
     * @return AI回复内容
     */
    public String chatWithSystem(String userMessage, String systemPrompt) {
        List<Map<String, String>> messages = new ArrayList<>();

        // 系统提示词
        Map<String, String> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", systemPrompt);
        messages.add(systemMsg);

        // 用户消息
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        return callQwenApi(messages);
    }

    /**
     * 普通对话（无系统提示词）
     * @param userMessage 用户输入
     * @return AI回复内容
     */
    public String chat(String userMessage) {
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);
        return callQwenApi(messages);
    }

    /**
     * 多轮对话（带历史上下文，带系统提示词）
     * @param history  历史消息列表（每条含 role + content）
     * @param userMessage 用户当前输入
     * @param systemPrompt 系统提示词
     * @return AI回复内容
     */
    public String chatWithHistory(List<Map<String, String>> history, String userMessage, String systemPrompt) {
        List<Map<String, String>> messages = new ArrayList<>();

        // 系统提示词
        Map<String, String> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", systemPrompt);
        messages.add(systemMsg);

        // 历史消息
        if (history != null) {
            messages.addAll(history);
        }

        // 当前用户消息
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        return callQwenApi(messages);
    }

    /**
     * 多轮对话（带历史上下文，无系统提示词）
     * @param history  历史消息列表
     * @param userMessage 用户当前输入
     * @return AI回复内容
     */
    public String chatWithHistory(List<Map<String, String>> history, String userMessage) {
        List<Map<String, String>> messages = new ArrayList<>();

        // 历史消息
        if (history != null) {
            messages.addAll(history);
        }

        // 当前用户消息
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        return callQwenApi(messages);
    }

    /**
     * 调用通义千问API核心方法
     * @param messages 消息列表
     * @return AI回复文本
     */
    private String callQwenApi(List<Map<String, String>> messages) {
        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", messages);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // 发送请求
            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(requestBody), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 解析响应，提取AI回复
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JSONObject json = JSON.parseObject(response.getBody());
                JSONArray choices = json.getJSONArray("choices");
                if (choices != null && !choices.isEmpty()) {
                    JSONObject choice = choices.getJSONObject(0);
                    JSONObject message = choice.getJSONObject("message");
                    if (message != null) {
                        String content = message.getString("content");
                        // Qwen3系列默认开启思考模式，过滤掉 <think>...</think> 内部推理过程
                        if (content != null) {
                            content = content.replaceAll("(?s)<think>.*?</think>", "").trim();
                        }
                        return content;
                    }
                }
            }

            return "抱歉，AI暂时无法回复，请稍后重试～";

        } catch (Exception e) {
            e.printStackTrace();
            return "AI服务调用出错：" + e.getMessage();
        }
    }

    /**
     * 图片理解：用户发送图片+文字，AI看图回答（带系统提示词）
     * @param userMessage 用户输入（文本）
     * @param imageBase64 图片Base64编码（data:image/xxx;base64,前缀）
     * @param systemPrompt 系统提示词
     * @return AI回复
     */
    public String chatWithImage(String userMessage, String imageBase64, String systemPrompt) {
        List<Map<String, Object>> messages = new ArrayList<>();

        if (systemPrompt != null && !systemPrompt.isEmpty()) {
            Map<String, Object> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);
            messages.add(systemMsg);
        }

        // 构建图片理解消息内容：[{type:"text", text:...}, {type:"image_url", image_url:{url:...}}]
        Map<String, String> textPart = new HashMap<>();
        textPart.put("type", "text");
        textPart.put("text", userMessage);
        Map<String, String> imageUrlPart = new HashMap<>();
        imageUrlPart.put("url", imageBase64);
        Map<String, Object> imageUrlWrapper = new HashMap<>();
        imageUrlWrapper.put("type", "image_url");
        imageUrlWrapper.put("image_url", imageUrlPart);

        Map<String, Object> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", new Object[]{textPart, imageUrlWrapper});
        messages.add(userMsg);

        return callQwenApiWithVision(messages, vlModel);
    }

    /**
     * 图片理解：用户发送图片+文字，AI看图回答（无系统提示词）
     * @param userMessage 用户输入（文本）
     * @param imageBase64 图片Base64
     * @return AI回复
     */
    public String chatWithImage(String userMessage, String imageBase64) {
        return chatWithImage(userMessage, imageBase64, null);
    }

    /**
     * 图片理解 + 多轮对话历史
     * @param history  历史对话（每条含 role + content）
     * @param userMessage 用户当前输入
     * @param imageBase64 图片Base64
     * @param systemPrompt 系统提示词
     * @return AI回复
     */
    public String chatWithImageAndHistory(
            List<Map<String, String>> history,
            String userMessage,
            String imageBase64,
            String systemPrompt) {
        List<Map<String, Object>> messages = new ArrayList<>();

        if (systemPrompt != null && !systemPrompt.isEmpty()) {
            Map<String, Object> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);
            messages.add(systemMsg);
        }

        // 转换历史消息
        for (Map<String, String> h : history) {
            Map<String, Object> histMsg = new HashMap<>();
            histMsg.put("role", h.get("role"));
            histMsg.put("content", h.get("content"));
            messages.add(histMsg);
        }

        // 当前用户消息（含图片）
        Map<String, String> textPart = new HashMap<>();
        textPart.put("type", "text");
        textPart.put("text", userMessage);
        Map<String, String> imageUrlPart = new HashMap<>();
        imageUrlPart.put("url", imageBase64);
        Map<String, Object> imageUrlWrapper = new HashMap<>();
        imageUrlWrapper.put("type", "image_url");
        imageUrlWrapper.put("image_url", imageUrlPart);

        Map<String, Object> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", new Object[]{textPart, imageUrlWrapper});
        messages.add(userMsg);

        return callQwenApiWithVision(messages, vlModel);
    }

    /**
     * 调用支持视觉的大模型API（多模态）
     */
    private String callQwenApiWithVision(List<Map<String, Object>> messages, String model) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", messages);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(requestBody), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JSONObject json = JSON.parseObject(response.getBody());
                JSONArray choices = json.getJSONArray("choices");
                if (choices != null && !choices.isEmpty()) {
                    JSONObject choice = choices.getJSONObject(0);
                    JSONObject message = choice.getJSONObject("message");
                    if (message != null) {
                        String content = message.getString("content");
                        if (content != null) {
                            content = content.replaceAll("(?s)<think>.*?</think>", "").trim();
                        }
                        return content;
                    }
                }
            }
            return "抱歉，AI暂时无法回复，请稍后重试～";
        } catch (Exception e) {
            e.printStackTrace();
            return "AI服务调用出错：" + e.getMessage();
        }
    }
}
