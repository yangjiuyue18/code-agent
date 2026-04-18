package com.campus.agent.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 日期工具类
 */
public class DateUtil {
    /**
     * 获取当前星期几（1-7，周一为1，周日为7）
     */
    public static Integer getCurrentWeekday() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        int weekday = dayOfWeek.getValue();
        // 适配数据库：周日转为7（默认DayOfWeek周日为7，无需修改）
        return weekday;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentWeekday()); // 测试
    }
}

