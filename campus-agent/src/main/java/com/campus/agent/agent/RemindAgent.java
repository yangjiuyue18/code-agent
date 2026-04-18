package com.campus.agent.agent;

import com.campus.agent.entity.Remind;
import com.campus.agent.mapper.RemindMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 智能提醒Agent：添加提醒、提醒管理
 */
@Component
public class RemindAgent {

    @Autowired
    private RemindMapper remindMapper;

    /**
     * 新增提醒（默认3小时后提醒，轻量化实现）
     * @param userId  用户ID
     * @param content 提醒内容
     * @return 是否创建成功
     */
    public boolean addRemind(Long userId, String content) {
        Remind remind = new Remind();
        remind.setUserId(userId);
        remind.setContent(content);
        remind.setRemindTime(LocalDateTime.now().plusHours(3)); // 默认3小时后
        remind.setStatus(0);
        return remindMapper.insert(remind) > 0;
    }
}

