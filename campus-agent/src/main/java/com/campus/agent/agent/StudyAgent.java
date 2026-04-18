package com.campus.agent.agent;

import com.campus.agent.entity.Schedule;
import com.campus.agent.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 学习管理Agent：课表查询、上课提醒
 */
@Component
public class StudyAgent {

    @Autowired
    private ScheduleMapper scheduleMapper;

    /**
     * 查询课表
     * @param userId  用户ID
     * @param weekday 星期几
     * @return 课表列表
     */
    public List<Schedule> querySchedule(Long userId, Integer weekday) {
        return scheduleMapper.selectByUserAndWeekday(userId, weekday);
    }
}

