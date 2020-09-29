package com.zzp.first.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzp.common.entity.User;
import com.zzp.first.feign.RemoteOrderService;
import com.zzp.first.mapper.admin.UserMapper;
import com.zzp.first.service.UserService;
import com.zzp.first.utils.RedisServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedisServiceUtil redisServiceUtil;
    @Resource
    private RemoteOrderService remoteOrderService;

    @Override
//    @GlobalTransactional
    public List<User> getUser() {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getName, "s"));
        log.info("userList:" + userList);
//        redisServiceUtil.setnx("sanguo8", "kaihua", 1000L);

        remoteOrderService.createOrder(BigDecimal.valueOf(400));
        return userList;
    }

}
