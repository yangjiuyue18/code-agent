package com.campus.agent.controller;

import com.campus.agent.agent.AgentDispatcher;
import com.campus.agent.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图片对话 Controller
 * 处理用户发送图片+文字的 AI 对话请求
 */
@RestController
@RequestMapping("/agent")
@CrossOrigin(origins = "*")
public class ImageChatController {

    @Autowired
    private AgentDispatcher agentDispatcher;

    /**
     * 带图片的对话接口（支持同时传递文档）
     * @param content     用户输入文本
     * @param userId      用户ID
     * @param imageBase64 图片Base64（前端传 data:image/xxx;base64,xxxx 格式）
     * @param docBase64   文档Base64（可选）
     * @param docName     文档名称（可选）
     * @return AI回复结果
     */
    @PostMapping("/chat-with-image")
    public Result<?> chatWithImage(
            @RequestParam(required = false) String content,
            @RequestParam Long userId,
            @RequestParam(required = false) String imageBase64,
            @RequestParam(required = false) String docBase64,
            @RequestParam(required = false) String docName) {
        try {
            return Result.success(agentDispatcher.dispatchWithImage(content, userId, imageBase64, docBase64, docName));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("AI服务异常：" + e.getMessage());
        }
    }
}
