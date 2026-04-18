package com.campus.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("schedule")
public class Schedule {
    @TableId(type = IdType.AUTO)
    private Long id; // 课表ID
    private Long userId; // 关联用户ID
    private String courseName; // 课程名称
    private String time; // 上课时间
    private String place; // 上课地点
    private Integer weekday; // 星期几（1-7）
}
