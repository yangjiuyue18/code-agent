package com.campus.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("knowledge")
public class Knowledge {
    @TableId(type = IdType.AUTO)
    private Long id; // 知识库ID
    private String question; // 问题
    private String answer; // 答案
    private String type; // 类型
}

