# 开发环境搭建指南

## 1. 环境要求

### 1.1 后端环境要求
- JDK 1.8 或更高版本（项目使用 Java 25）
- MySQL 8.0 或更高版本
- Redis
- Maven 3.0 或更高版本

### 1.2 前端环境要求
- Node.js 8.9 或更高版本
- npm 包管理器

## 2. 后端环境搭建

### 2.1 数据库配置

1. 安装并启动 MySQL 服务
2. 创建数据库：
   ```sql
   CREATE DATABASE book_manager CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;
   ```
3. 导入初始数据：
   ```bash
   mysql -u your_username -p book_manager < BookManagerApi/book_manager.sql
   ```

### 2.2 Redis 配置

1. 安装并启动 Redis 服务
2. 默认配置无需额外设置，如需修改可在 `application.properties` 中调整连接参数

### 2.3 后端项目配置

1. 打开 `BookManagerApi/src/main/resources/application.properties` 文件
2. 根据实际环境修改数据库连接配置：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/book_manager?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Redis 配置（如有需要）：
   ```properties
   spring.data.redis.host=localhost
   spring.data.redis.port=6379
   ```

### 2.4 启动后端服务

在 `BookManagerApi` 目录下执行以下命令：

```bash
mvn spring-boot:run
```

或者使用 Maven Wrapper：

```bash
./mvnw spring-boot:run
```

后端服务默认启动在 `http://localhost:9111`

## 3. 前端环境搭建

### 3.1 安装依赖

进入 `BookManagerVue` 目录，执行：

```bash
npm install
```

### 3.2 解决兼容性问题

由于项目使用较新的 Node.js 版本，可能需要设置环境变量以解决 OpenSSL 兼容性问题：

PowerShell:
```powershell
$env:NODE_OPTIONS="--openssl-legacy-provider"
```

CMD:
```cmd
set NODE_OPTIONS=--openssl-legacy-provider
```

### 3.3 启动前端开发服务器

在 `BookManagerVue` 目录下执行：

```bash
npm run dev
```

前端应用默认启动在 `http://localhost:8080`

## 4. 访问系统

1. 确保后端服务已启动（端口 9111）
2. 确保前端开发服务器已启动（端口 8080）
3. 在浏览器中访问 `http://localhost:8080`
4. 使用默认管理员账号登录：
   - 用户名：admin
   - 密码：123456

## 5. 常见问题

### 5.1 sun.misc.Unsafe 警告

当使用较新的 Java 版本（如 Java 25）运行项目时，可能会看到关于 sun.misc.Unsafe 的警告信息。这些警告不影响系统功能，可以选择忽略，或者使用 LTS 版本的 Java（如 Java 17 或 Java 21）来避免这些问题。

### 5.2 OpenSSL 错误

如果在启动前端时遇到 OpenSSL 相关错误，请确保设置了 `NODE_OPTIONS=--openssl-legacy-provider` 环境变量。

### 5.3 跨域问题

该项目已经配置了后端 CORS 支持，通常不会有跨域问题。如果遇到相关错误，请检查后端配置是否正确。