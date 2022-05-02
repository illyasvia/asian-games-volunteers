package com.example.demo;

import com.example.demo.dao.IUserDao;
import com.example.demo.pojo.User;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootTest
public class MaybatisTest {
    @Autowired
    IUserDao dao;

    @Test
    void test() {
        List<User> user=dao.getUserById(1);
        System.out.println(user.get(0).getSex());
    }
}
