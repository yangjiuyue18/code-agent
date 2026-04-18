package com.campus.agent.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.agent.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {
    // 根据用户ID和星期几查询课表
    @Select("select * from schedule where user_id = #{userId} and weekday = #{weekday} order by time")
    List<Schedule> selectByUserAndWeekday(Long userId, Integer weekday);
}

