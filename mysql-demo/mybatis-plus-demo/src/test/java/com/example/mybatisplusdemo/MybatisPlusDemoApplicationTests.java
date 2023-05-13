package com.example.mybatisplusdemo;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.example.mybatisplusdemo.entity.User;
import com.example.mybatisplusdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(userList.size() > 0, "列表长度为 0");
        userList.forEach(System.out::println);
    }



}
