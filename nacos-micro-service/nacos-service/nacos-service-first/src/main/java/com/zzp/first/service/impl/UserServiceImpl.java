package com.zzp.first.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzp.common.entity.User;
import com.zzp.first.mapper.UserMapper;
import com.zzp.first.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String consumerService() {
        return "consumerService";
    }

    @Override
    public List<User> getUser() {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getName, "s"));
        log.info("userList:" + userList);
        return userList;
    }

}