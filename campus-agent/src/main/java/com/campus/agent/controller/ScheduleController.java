package com.campus.agent.controller;

import com.campus.agent.entity.Schedule;
import com.campus.agent.service.ScheduleService;
import com.campus.agent.utils.DateUtil;
import com.campus.agent.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课表控制器：查询、添加、修改、删除课表
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 获取今日课表
     * @param userId 用户ID
     * @return 今日课表列表
     */
    @GetMapping("/today")
    public Result<List<Schedule>> getTodaySchedule(@RequestParam Long userId) {
        try {
            List<Schedule> list = scheduleService.getTodaySchedule(userId, DateUtil.getCurrentWeekday());
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询课表失败：" + e.getMessage());
        }
    }

    /**
     * 获取指定星期课表
     * @param userId  用户ID
     * @param weekday 星期几(1-7)
     * @return 课表列表
     */
    @GetMapping("/weekday")
    public Result<List<Schedule>> getScheduleByWeekday(
            @RequestParam Long userId,
            @RequestParam Integer weekday) {
        try {
            List<Schedule> list = scheduleService.getScheduleByWeekday(userId, weekday);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询课表失败：" + e.getMessage());
        }
    }

    /**
     * 添加课程
     * @param schedule 课程信息
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Map<String, Object>> addCourse(@RequestBody Schedule schedule) {
        try {
            boolean success = scheduleService.addCourse(schedule);
            Map<String, Object> result = new HashMap<>();
            if (success) {
                result.put("id", schedule.getId());
                return Result.success(result);
            } else {
                return Result.error("添加课程失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加课程失败：" + e.getMessage());
        }
    }

    /**
     * 更新课程
     * @param schedule 课程信息
     * @return 结果
     */
    @PostMapping("/update")
    public Result<Map<String, Object>> updateCourse(@RequestBody Schedule schedule) {
        try {
            boolean success = scheduleService.updateCourse(schedule);
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新课程失败：" + e.getMessage());
        }
    }

    /**
     * 删除课程
     * @param id 课程ID
     * @return 结果
     */
    @PostMapping("/delete")
    public Result<Map<String, Object>> deleteCourse(@RequestParam Long id) {
        try {
            boolean success = scheduleService.deleteCourse(id);
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除课程失败：" + e.getMessage());
        }
    }
}

