package com.campus.agent.agent;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 意图解析器：识别用户意图，提取关键信息
 */
@Component
public class IntentResolver {

    /**
     * 解析用户输入
     * @param content 用户输入内容
     * @return Map：key=intent(意图), key=keywords(关键词)
     */
    public Map<String, Object> resolve(String content) {
        Map<String, Object> result = new HashMap<>();
        content = content.trim().toLowerCase();
        IntentType intentType = IntentType.UNKNOWN;
        String keywords = content;

        // 1. 匹配学习Agent：课表、上课、课程、今日课表、明日课表
        if (content.contains("课表") || content.contains("上课") || content.contains("课程")) {
            intentType = IntentType.STUDY_SCHEDULE;
        }
        // 2. 匹配提醒Agent：提醒、记得、帮我记、下课后、明天上午
        else if (content.contains("提醒") || content.contains("记得") || content.contains("帮我记")) {
            intentType = IntentType.REMIND_ADD;
            // 提取提醒内容（去除“提醒我”等前缀）
            keywords = content.replace("提醒我", "").replace("帮我提醒", "").replace("记得", "").trim();
        }
        // 3. 匹配生活Agent：食堂、快递、电话、报修、菜鸟驿站、吃什么
        else if (content.contains("食堂") || content.contains("快递") || content.contains("电话")
                || content.contains("报修") || content.contains("吃什么") || content.contains("菜鸟驿站")) {
            intentType = IntentType.LIFE_SERVICE;
        }
        // 4. 匹配资讯Agent：校历、放假、考试、办事指南、校园通知
        else if (content.contains("校历") || content.contains("放假") || content.contains("考试")
                || content.contains("办事") || content.contains("通知")) {
            intentType = IntentType.CAMPUS_INFO;
        }

        result.put("intent", intentType);
        result.put("keywords", keywords);
        return result;
    }
}

