# 图书管理系统

这是一个基于 Spring Boot 和 Vue.js 的图书管理系统。

## 技术栈

### 后端 (BookManagerApi)

*   **框架:** Spring Boot 3.5.8
*   **数据持久层:** MyBatis 3.0.3
*   **数据库:** MySQL
*   **缓存:** Redis
*   **Java 版本:** 25

### 前端 (BookManagerVue)

*   **框架:** Vue.js 2.6.10
*   **UI 库:** Element UI 2.13.2
*   **路由:** Vue Router 3.0.6
*   **状态管理:** Vuex 3.1.0
*   **HTTP 客户端:** Axios 0.24.0

## 如何启动和运行

### 后端

1.  **配置数据库:**
    *   在您的 MySQL 服务器中创建一个新的数据库。
    *   导入位于 `BookManagerApi/book_manager.sql` 的 SQL 文件以创建表和插入初始数据。
    *   在 `BookManagerApi/src/main/resources/application.properties` 文件中更新数据库连接属性 (`spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`)。

2.  **运行后端应用程序:**
    *   导航到 `BookManagerApi` 目录。
    *   使用 Maven 运行应用程序：
        ```bash
        mvn spring-boot:run
        ```
    *   后端服务将在 `http://localhost:9111` 上启动。

    *   注意：项目使用了较新的Java版本(Java 25)，可能会出现与Maven相关的sun.misc.Unsafe警告，但这不影响功能正常使用。

### 前端

1.  **安装依赖:**
    *   导航到 `BookManagerVue` 目录。
    *   安装 npm 依赖：
        ```bash
        npm install
        ```

2.  **运行前端开发服务器:**
    *   运行以下命令：
        ```bash
        npm run dev
        ```
    *   前端应用程序将在 `http://localhost:8080` 上可用。

    *   注意：如果遇到Node.js OpenSSL相关错误，可能需要设置环境变量：
        ```bash
        $env:NODE_OPTIONS="--openssl-legacy-provider" # PowerShell
        # 或者
        set NODE_OPTIONS=--openssl-legacy-provider    # CMD
        ```

## 项目结构

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
└── README.md                 # 本文档
```