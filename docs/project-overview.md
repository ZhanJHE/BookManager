# BookManager 图书管理系统 - 项目概述

## 1. 项目简介

BookManager 是一个基于 Spring Boot 和 Vue.js 的前后端分离图书管理系统，适用于小型图书馆或图书借阅场景。系统提供了完整的图书信息管理、借阅管理、用户管理等功能。

## 2. 技术架构

### 2.1 后端技术栈 (BookManagerApi)

- **框架**: Spring Boot 3.5.8
- **数据持久层**: MyBatis 3.0.3
- **数据库**: MySQL 8.4.6
- **缓存**: Redis
- **Java 版本**: Java 25

### 2.2 前端技术栈 (BookManagerVue)

- **框架**: Vue.js 2.6.10
- **UI 库**: Element UI 2.13.2
- **路由**: Vue Router 3.0.6
- **状态管理**: Vuex 3.1.0
- **HTTP 客户端**: Axios 0.24.0

## 3. 系统功能

### 3.1 核心功能模块

#### 图书管理模块
- 图书信息的增删改查
- 图书封面图片管理
- 图书分类关联

#### 图书分类管理模块
- 图书类型维护（如计算机科学、历史、文学等）
- 分类信息的增删改查

#### 借阅管理模块
- 用户借书操作
- 用户还书操作
- 借阅记录查询

#### 用户管理模块
- 用户注册与登录
- 用户权限管理（管理员/普通用户）
- 密码修改

### 3.2 权限控制

系统区分两种用户角色：
- **管理员 (admin)**: 拥有所有功能权限，可以管理图书、分类、用户等全部信息
- **普通用户 (reader)**: 可以浏览图书、借阅图书、修改个人信息和密码

## 4. 数据库设计

系统包含四个主要数据表：

### 4.1 用户表 (user)
| 字段名 | 类型 | 描述 |
|--------|------|------|
| userId | int | 用户ID，主键，自增 |
| userName | varchar(20) | 用户名 |
| userPassword | varchar(20) | 用户密码 |
| isAdmin | tinyint | 是否为管理员（1是管理员，0非管理员） |

### 4.2 图书分类表 (book_type)
| 字段名 | 类型 | 描述 |
|--------|------|------|
| bookTypeId | int | 图书类型ID，主键，自增 |
| bookTypeName | varchar(20) | 图书类型名称 |
| bookTypeDesc | varchar(255) | 图书类型描述 |

### 4.3 图书信息表 (book_info)
| 字段名 | 类型 | 描述 |
|--------|------|------|
| bookId | int | 图书ID，主键，自增 |
| bookName | varchar(50) | 图书名称 |
| bookAuthor | varchar(50) | 图书作者 |
| bookPrice | decimal(10,2) | 图书价格 |
| bookTypeId | int | 图书类型ID，外键关联book_type表 |
| bookDesc | varchar(255) | 图书描述 |
| isBorrowed | tinyint | 是否借出（1表示借出，0表示已还） |
| bookImg | varchar(255) | 图书封面图片路径 |

### 4.4 借阅记录表 (borrow)
| 字段名 | 类型 | 描述 |
|--------|------|------|
| borrowId | int | 借阅记录ID，主键，自增 |
| userId | int | 用户ID，外键关联user表 |
| bookId | int | 图书ID，外键关联book_info表 |
| borrowTime | datetime | 借书时间 |
| returnTime | datetime | 还书时间（可为空） |

## 5. 系统特点

- **前后端分离架构**: 前端使用Vue.js构建单页应用，后端提供RESTful API接口
- **响应式设计**: 前端界面适配不同屏幕尺寸
- **权限控制**: 基于角色的访问控制，不同用户拥有不同的操作权限
- **数据缓存**: 使用Redis提高系统性能
- **友好界面**: 使用Element UI组件库构建美观易用的操作界面

## 6. 项目结构

```
BookManager/
├── BookManagerApi/           # 后端 Spring Boot 应用程序
│   ├── src/main/java/        # Java 源代码
│   ├── src/main/resources/   # 配置文件和资源
│   ├── pom.xml               # Maven 项目配置文件
│   └── book_manager.sql      # 数据库脚本
├── BookManagerVue/           # 前端 Vue.js 应用程序
│   ├── public/               # 公共文件
│   ├── src/                  # 前端源代码
│   │   ├── api/              # API 请求
│   │   ├── assets/           # 静态资源
│   │   ├── components/       # Vue 组件
│   │   ├── layout/           # 布局组件
│   │   ├── router/           # 路由配置
│   │   ├── store/            # Vuex 状态管理
│   │   └── views/            # 页面视图
│   ├── package.json          # npm 依赖和脚本
│   └── vue.config.js         # Vue CLI 配置文件
└── docs/                     # 项目文档
```