package com.campus.agent.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.agent.entity.Remind;
import com.campus.agent.mapper.RemindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 提醒定时任务：每分钟扫描待提醒任务
 */
@Component
public class RemindJob {

    @Autowired
    private RemindMapper remindMapper;

    /**
     * 定时任务：每分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkRemind() {
        LocalDateTime now = LocalDateTime.now();
        // 查询：未完成 + 提醒时间<=当前时间
        LambdaQueryWrapper<Remind> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Remind::getStatus, 0)
                .le(Remind::getRemindTime, now);
        List<Remind> remindList = remindMapper.selectList(wrapper);

        // 打印提醒（可扩展为WebSocket推送到前端）
        if (!remindList.isEmpty()) {
            System.out.println("=====【校园小管家提醒】=====" + now);
            for (Remind remind : remindList) {
                System.out.println("用户ID：" + remind.getUserId() + " | 提醒内容：" + remind.getContent());
                // 标记为已完成
                remind.setStatus(1);
                remindMapper.updateById(remind);
            }
            System.out.println("===========================");
        }
    }
}

