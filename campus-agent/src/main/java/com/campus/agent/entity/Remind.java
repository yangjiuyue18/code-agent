package com.campus.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("remind")
public class Remind {
    @TableId(type = IdType.AUTO)
    private Long id; // 提醒ID
    private Long userId; // 关联用户ID
    private String content; // 提醒内容
    private LocalDateTime remindTime; // 提醒时间
    private Integer status; // 状态（0-未完成，1-已完成）
}

