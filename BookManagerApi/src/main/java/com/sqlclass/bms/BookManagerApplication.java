package com.sqlclass.bms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sqlclass.bms.utils.PathUtils;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.sqlclass.bms.mapper")
public class BookManagerApplication {

    public static void main(String[] args) {
        System.out.println("Project Path: " + PathUtils.getClassLoadRootPath());
        SpringApplication.run(BookManagerApplication.class, args);
    }

}