package com.campus.agent.controller;

import com.campus.agent.entity.Remind;
import com.campus.agent.service.RemindService;
import com.campus.agent.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提醒控制器：添加提醒、查询提醒列表
 */
@RestController
@RequestMapping("/remind")
public class RemindController {

    @Autowired
    private RemindService remindService;

    /**
     * 添加提醒（说明书核心接口2）
     * @param remind 提醒实体
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result<Boolean> addRemind(@RequestBody Remind remind) {
        try {
            boolean flag = remindService.addRemind(remind);
            return Result.success(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加提醒失败：" + e.getMessage());
        }
    }

    /**
     * 查询用户提醒列表
     * @param userId 用户ID
     * @return 提醒列表
     */
    @GetMapping("/list")
    public Result<List<Remind>> getRemindList(@RequestParam Long userId) {
        try {
            List<Remind> list = remindService.getRemindList(userId);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询提醒失败：" + e.getMessage());
        }
    }
}

