package com.campus.agent.service;

import com.campus.agent.entity.Schedule;
import com.campus.agent.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课表服务
 */
@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    /**
     * 查询今日课表
     * @param userId  用户ID
     * @param weekday 星期几
     * @return 课表列表
     */
    public List<Schedule> getTodaySchedule(Long userId, Integer weekday) {
        return scheduleMapper.selectByUserAndWeekday(userId, weekday);
    }

    /**
     * 添加课程
     * @param schedule 课程信息
     * @return 是否成功
     */
    public boolean addCourse(Schedule schedule) {
        return scheduleMapper.insert(schedule) > 0;
    }

    /**
     * 更新课程
     * @param schedule 课程信息
     * @return 是否成功
     */
    public boolean updateCourse(Schedule schedule) {
        return scheduleMapper.updateById(schedule) > 0;
    }

    /**
     * 删除课程
     * @param id 课程ID
     * @return 是否成功
     */
    public boolean deleteCourse(Long id) {
        return scheduleMapper.deleteById(id) > 0;
    }

    /**
     * 获取指定星期课表
     * @param userId  用户ID
     * @param weekday 星期几
     * @return 课表列表
     */
    public List<Schedule> getScheduleByWeekday(Long userId, Integer weekday) {
        return scheduleMapper.selectByUserAndWeekday(userId, weekday);
    }
}

