package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 配置Mybatis
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.example.demo.dao")
public class MybatisConfig {
}
