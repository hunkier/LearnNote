package com.hunk.user.service;

import com.hunk.thrift.user.UserInfo;
import com.hunk.thrift.user.UserService;
import com.hunk.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return null;
    }

    @Override
    public UserInfo getUserByName(String name) throws TException {
        return userMapper.getUserByName(name);
    }

    @Override
    public void registerUser(UserInfo userinfo) throws TException {
        userMapper.registerUser(userinfo);
    }
}
