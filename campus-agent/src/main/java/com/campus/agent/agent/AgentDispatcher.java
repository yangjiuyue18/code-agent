package com.campus.agent.agent;

import com.campus.agent.service.ChatHistoryService;
import com.campus.agent.service.DocumentParserService;
import com.campus.agent.service.FinanceService;
import com.campus.agent.service.QwenService;
import com.campus.agent.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度中枢：统一分配任务，管理子Agent执行
 */
@Component
public class AgentDispatcher {

    @Autowired
    private StudyAgent studyAgent;
    @Autowired
    private LifeAgent lifeAgent;
    @Autowired
    private RemindAgent remindAgent;
    @Autowired
    private InfoAgent infoAgent;
    @Autowired
    private IntentResolver intentResolver;
    @Autowired
    private QwenService qwenService;
    @Autowired
    private ChatHistoryService chatHistoryService;
    @Autowired
    private FinanceService financeService;
    @Autowired
    private DocumentParserService documentParserService;

    /**
     * 调度核心方法
     * @param content 用户输入
     * @param userId  用户ID
     * @return 执行结果（文本/卡片数据）
     */
    public Map<String, Object> dispatch(String content, Long userId) {
        // 0. 先保存用户消息
        chatHistoryService.saveMessage(userId, "user", content);

        // 1. 意图解析
        Map<String, Object> resolveResult = intentResolver.resolve(content);
        IntentType intentType = (IntentType) resolveResult.get("intent");
        String keywords = (String) resolveResult.get("keywords");

        // 2. 根据意图调度对应子Agent
        Map<String, Object> result = new HashMap<>();
        result.put("intent", intentType.name());
        result.put("keywords", keywords);

        // 3. 优先检查是否为金融查询（关键词匹配）
        if (financeService.isFinanceQuery(content)) {
            result.put("intent", "FINANCE_QUERY");
            result.put("data", null);
            result.put("reply", "🔍 正在查询金融数据，请稍候...");
            // 金融查询的结果会在 ChatService 中被替换，所以这里先不保存
            return result;
        }

        switch (intentType) {
            case STUDY_SCHEDULE:
                result.put("data", studyAgent.querySchedule(userId, DateUtil.getCurrentWeekday()));
                result.put("reply", "已为你查询到今日课表，请查看～");
                chatHistoryService.saveMessage(userId, "assistant", "已为你查询到今日课表，请查看～");
                break;
            case LIFE_SERVICE:
                com.campus.agent.entity.Knowledge lifeResult = lifeAgent.queryLifeService(keywords);
                if (lifeResult != null) {
                    result.put("data", lifeResult);
                    result.put("reply", lifeResult.getAnswer());
                } else {
                    // 知识库未匹配到 → fallback到通义千问AI对话（带历史上下文）
                    List<Map<String, String>> history = chatHistoryService.loadHistoryForQwen(userId);
                    String lifeSystemPrompt = "你是校园生活小管家。用户咨询生活服务类问题（食堂、快递、报修、校医等），"
                        + "但知识库中没有找到匹配的答案。请根据你的知识友好地回复用户，"
                        + "如果不确定可以建议用户咨询相关部门或拨打校园服务热线。"
                        + "请直接输出纯文本回复，不要使用Markdown格式，直接用自然语言回答即可。";
                    String lifeAiReply = qwenService.chatWithHistory(history, content, lifeSystemPrompt);
                    result.put("data", null);
                    result.put("reply", lifeAiReply);
                    // 保存本轮对话记录
                    chatHistoryService.saveMessage(userId, "assistant", lifeAiReply);
                }
                break;
            case REMIND_ADD:
                boolean addFlag = remindAgent.addRemind(userId, keywords);
                result.put("data", addFlag);
                result.put("reply", addFlag ? "提醒创建成功～" : "提醒创建失败，请重试！");
                chatHistoryService.saveMessage(userId, "assistant", addFlag ? "提醒创建成功～" : "提醒创建失败，请重试！");
                break;
            case CAMPUS_INFO:
                result.put("data", infoAgent.queryInfo(keywords));
                String campusReply = infoAgent.queryInfo(keywords) != null ? infoAgent.queryInfo(keywords).getAnswer() : "暂无相关资讯～";
                result.put("reply", campusReply);
                chatHistoryService.saveMessage(userId, "assistant", campusReply);
                break;
            default:
                // 未知意图 → fallback到通义千问AI自由对话（带历史上下文 + 校园管家系统提示词）
                List<Map<String, String>> history = chatHistoryService.loadHistoryForQwen(userId);
                String systemPrompt = "你是校园生活小管家，一个友好、热情、专业的校园助手。"
                    + "你的职责是帮助大学生解决校园生活中的各种问题，包括但不限于："
                    + "学习咨询（课表查询、考试安排、图书馆使用等）、"
                    + "生活服务（食堂菜单、快递取件、报修服务、校医等）、"
                    + "校园资讯（校历安排、放假通知、办事指南等）、"
                    + "日常闲聊和情感陪伴。"
                    + "请用简洁友好的语气回复，如果不确定的问题建议用户咨询相关部门。"
                    + "请直接输出纯文本回复，不要使用Markdown格式，直接用自然语言回答即可。";
                String aiReply = qwenService.chatWithHistory(history, content, systemPrompt);
                result.put("data", null);
                result.put("reply", aiReply);
                // 保存本轮对话记录（用于下一轮上下文）
                chatHistoryService.saveMessage(userId, "assistant", aiReply);
                break;
        }
        return result;
    }

    /**
     * 带图片的调度方法（多模态AI对话）
     * @param content 用户输入文本
     * @param userId  用户ID
     * @param imageBase64 图片Base64
     * @return AI回复结果
     */
    public Map<String, Object> dispatchWithImage(String content, Long userId, String imageBase64) {
        return dispatchWithImage(content, userId, imageBase64, null, null);
    }

    /**
     * 带图片和文档的调度方法（多模态AI对话 + 文档解析）
     * @param content 用户输入文本
     * @param userId  用户ID
     * @param imageBase64 图片Base64（可选）
     * @param docBase64   文档Base64（可选）
     * @param docName     文档名称（可选）
     * @return AI回复结果
     */
    public Map<String, Object> dispatchWithImage(String content, Long userId, String imageBase64, String docBase64, String docName) {
        Map<String, Object> result = new HashMap<>();
        result.put("intent", "UNKNOWN");
        result.put("keywords", "");

        // 如果有文档，解析内容
        String docContent = null;
        if (docBase64 != null && !docBase64.isEmpty() && docName != null && !docName.isEmpty()) {
            try {
                byte[] docBytes = Base64.getDecoder().decode(docBase64);
                docContent = documentParserService.parseDocument(docBytes, docName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 加载历史上下文
        List<Map<String, String>> history = chatHistoryService.loadHistoryForQwen(userId);

        // 构建系统提示词
        String systemPrompt = "你是校园生活小管家，一个友好、热情、专业的校园助手。";
        if (imageBase64 != null && !imageBase64.isEmpty()) {
            systemPrompt += "用户发送了一张图片，请仔细观察图片内容，并结合你的知识给出友好、准确的回答。";
            systemPrompt += "如果图片是文字截图、表格、课程表、食堂菜单等，请识别并提取信息回答用户。";
        }
        if (docContent != null && !docContent.isEmpty()) {
            systemPrompt += "用户还上传了一个文档，内容如下：\n" + docContent;
        }
        systemPrompt += "请根据上述内容回答用户的问题。请直接输出纯文本回复，不要使用Markdown格式。";

        // 构建用户消息（包含文档内容）
        String userMessage = content != null ? content : "";
        if (docContent != null && !docContent.isEmpty()) {
            userMessage = docContent + "\n\n【用户问题】" + content;
        }

        // 调用AI
        String aiReply;
        if (imageBase64 != null && !imageBase64.isEmpty()) {
            // 有图片，调用VL模型
            if (history != null && !history.isEmpty()) {
                aiReply = qwenService.chatWithImageAndHistory(history, userMessage, imageBase64, systemPrompt);
            } else {
                aiReply = qwenService.chatWithImage(userMessage, imageBase64, systemPrompt);
            }
        } else {
            // 只有文档，调用普通对话
            if (history != null && !history.isEmpty()) {
                aiReply = qwenService.chatWithHistory(history, userMessage, systemPrompt);
            } else {
                aiReply = qwenService.chatWithSystem(userMessage, systemPrompt);
            }
        }

        result.put("data", null);
        result.put("reply", aiReply);

        // 保存本轮对话记录
        String combinedContent = content != null ? content : "";
        if (docName != null) {
            combinedContent += " [文档:" + docName + "]";
        }
        if (imageBase64 != null && !imageBase64.isEmpty()) {
            combinedContent += " [图片]";
        }
        chatHistoryService.saveMessage(userId, "assistant", aiReply);

        return result;
    }
}

