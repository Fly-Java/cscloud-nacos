package com.zzp.first.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzp.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
