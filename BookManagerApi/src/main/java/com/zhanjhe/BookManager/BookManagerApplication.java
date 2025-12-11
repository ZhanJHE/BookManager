package com.zhanjhe.BookManager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zhanjhe.BookManager.utils.PathUtils;


/**
 * 图书管理系统启动类
 * 这是整个Spring Boot应用程序的入口点
 * 程序从此类的main方法开始执行
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.zhanjhe.BookManager.repository")
public class BookManagerApplication {

    /**
     * Spring Boot应用程序的主入口方法
     * 负责启动整个图书管理系统服务
     * @param args 命令行参数数组，可以在启动时传入额外配置
     */
    public static void main(String[] args) {
        // 打印项目路径信息到控制台，便于调试和部署时确认路径正确性
        System.out.println("Project Path: " + PathUtils.getClassLoadRootPath());
        // 启动Spring Boot应用程序，加载所有相关配置和服务
        SpringApplication.run(BookManagerApplication.class, args);
    }

}