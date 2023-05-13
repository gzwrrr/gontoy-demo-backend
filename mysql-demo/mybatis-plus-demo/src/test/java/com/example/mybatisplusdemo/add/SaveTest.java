package com.example.mybatisplusdemo.add;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.example.mybatisplusdemo.entity.User;
import com.example.mybatisplusdemo.mapper.UserMapper;
import com.example.mybatisplusdemo.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class SaveTest {

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setAge(18);
        user.setName("zhangsan");
        user.setEmail("1698685542@qq.com");
        Assert.isTrue(userService.save(user), "保存单个用户失败");
    }


    @Test
    public void saveBatch() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(18 + i);
            user.setName("zhangsan" + i);
            user.setEmail("1698685542@qq.com" + i);
            list.add(user);
        }
        Assert.isTrue(userService.saveBatch(list), "保存多个用户失败");
    }


}
