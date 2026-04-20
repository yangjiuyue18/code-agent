package com.campus.agent.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.agent.entity.Remind;
import com.campus.agent.mapper.RemindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提醒服务
 */
@Service
public class RemindService {

    @Autowired
    private RemindMapper remindMapper;

    /**
     * 添加提醒
     * @param remind 提醒实体
     * @return 是否成功
     */
    public boolean addRemind(Remind remind) {
        return remindMapper.insert(remind) > 0;
    }

    /**
     * 查询用户提醒列表
     * @param userId 用户ID
     * @return 提醒列表
     */
    public List<Remind> getRemindList(Long userId) {
        LambdaQueryWrapper<Remind> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Remind::getUserId, userId).orderByDesc(Remind::getRemindTime);
        return remindMapper.selectList(wrapper);
    }

    /**
     * 更新提醒状态
     * @param remind 提醒实体
     * @return 是否成功
     */
    public boolean updateRemind(Remind remind) {
        return remindMapper.updateById(remind) > 0;
    }

    /**
     * 删除提醒
     * @param id 提醒ID
     * @return 是否成功
     */
    public boolean deleteRemind(Long id) {
        return remindMapper.deleteById(id) > 0;
    }
}

