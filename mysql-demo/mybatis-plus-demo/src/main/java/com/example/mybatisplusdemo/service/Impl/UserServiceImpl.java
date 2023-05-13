package com.example.mybatisplusdemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplusdemo.entity.User;
import com.example.mybatisplusdemo.mapper.UserMapper;
import com.example.mybatisplusdemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
