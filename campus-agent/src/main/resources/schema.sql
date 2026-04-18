-- ============================================================
-- campus-agent 多轮对话历史表
-- 用于存储用户与AI的对话上下文，实现多轮记忆功能
-- ============================================================

CREATE TABLE IF NOT EXISTS `chat_history` (
    `id`          BIGINT       AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    `user_id`     BIGINT       NOT NULL            COMMENT '用户ID',
    `role`        VARCHAR(20)  NOT NULL            COMMENT '角色：user(用户) / assistant(AI)',
    `content`     TEXT         NOT NULL            COMMENT '消息内容',
    `create_time` DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='多轮对话历史记录表';
