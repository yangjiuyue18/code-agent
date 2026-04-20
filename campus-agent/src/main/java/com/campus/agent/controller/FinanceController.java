package com.campus.agent.controller;

import com.campus.agent.service.FinanceService;
import com.campus.agent.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 金融数据查询控制器
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    /**
     * 金融数据查询接口
     * @param query 自然语言查询（如"贵州茅台今日股价"）
     * @param token JWT认证令牌
     * @param userId 用户ID（用于保存历史）
     * @return 查询结果
     */
    @PostMapping("/query")
    public Result<Map<String, Object>> query(
            @RequestParam String query,
            @RequestParam String token,
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        try {
            // 执行查询
            String result = financeService.query(query, token);

            Map<String, Object> response = new HashMap<>();
            response.put("intent", "FINANCE_QUERY");
            response.put("reply", result);
            response.put("data", null);

            return Result.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 检查是否为金融查询
     * @param query 用户输入
     * @return 是否为金融查询
     */
    @PostMapping("/check")
    public Result<Map<String, Object>> checkFinanceQuery(@RequestParam String query) {
        Map<String, Object> response = new HashMap<>();
        response.put("isFinance", financeService.isFinanceQuery(query));
        return Result.success(response);
    }
}
