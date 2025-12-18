# 部署指南

## 1. 生产环境要求

### 1.1 服务器要求
- JDK 1.8 或更高版本
- MySQL 8.0 或更高版本
- Redis
- nginx（用于前端静态资源配置）

### 1.2 推荐配置
- CPU: 2核或以上
- 内存: 4GB或以上
- 硬盘: 20GB或以上可用空间

## 2. 后端部署

### 2.1 打包后端应用

在 `BookManagerApi` 目录下执行 Maven 打包命令：

```bash
mvn clean package
```

打包完成后会在 `target` 目录下生成 `BookManagerApi-1.0.5.jar` 文件。

### 2.2 配置生产环境

创建生产环境配置文件 `application-prod.properties`：

```properties
# 服务器配置
server.port=9111

# 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/book_manager?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
spring.datasource.username=your_prod_username
spring.datasource.password=your_prod_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Redis配置
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.timeout=10000ms

# 日志配置
logging.level.root=INFO
logging.level.com.sqlclass.bms=INFO
logging.file.name=/var/log/bookmanager/bookmanager.log
```

### 2.3 启动后端服务

使用以下命令启动后端服务：

```bash
java -jar BookManagerApi-1.0.5.jar --spring.profiles.active=prod
```

为了保持服务在后台运行，可以使用以下命令：

```bash
nohup java -jar BookManagerApi-1.0.5.jar --spring.profiles.active=prod > bookmanager.log 2>&1 &
```

## 3. 前端部署

### 3.1 构建前端项目

在 `BookManagerVue` 目录下执行构建命令：

```bash
npm run build:prod
```

构建完成后会在 `dist` 目录下生成所有静态资源文件。

### 3.2 部署前端静态资源

将 `dist` 目录下的所有文件复制到 nginx 的静态资源目录中，例如 `/usr/share/nginx/html/bookmanager/`。

### 3.3 配置 nginx

编辑 nginx 配置文件（通常位于 `/etc/nginx/nginx.conf` 或 `/etc/nginx/sites-available/default`）：

```nginx
server {
    listen 80;
    server_name your_domain.com;  # 替换为你的域名
    
    location / {
        root /usr/share/nginx/html/bookmanager;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    location /api/ {
        proxy_pass http://localhost:9111/;  # 后端服务地址
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

重启 nginx 服务：

```bash
sudo systemctl restart nginx
```

## 4. 数据库部署

### 4.1 创建生产数据库

```sql
CREATE DATABASE book_manager CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;
```

### 4.2 导入初始数据

```bash
mysql -u your_username -p book_manager < BookManagerApi/book_manager.sql
```

## 5. 使用 Docker 部署（可选）

### 5.1 创建 Docker Compose 文件

创建 `docker-compose.yml` 文件：

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: bookmanager-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: book_manager
      MYSQL_USER: bookmanager
      MYSQL_PASSWORD: user_password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./book_manager.sql:/docker-entrypoint-initdb.d/book_manager.sql

  redis:
    image: redis:latest
    container_name: bookmanager-redis
    ports:
      - "6379:6379"

  backend:
    image: openjdk:17-jdk-slim
    container_name: bookmanager-backend
    depends_on:
      - mysql
      - redis
    ports:
      - "9111:9111"
    volumes:
      - ./BookManagerApi-1.0.5.jar:/app/app.jar
      - ./application-docker.properties:/app/config/application.properties
    command: java -jar /app/app.jar --spring.config.location=file:/app/config/application.properties

  frontend:
    image: nginx:alpine
    container_name: bookmanager-frontend
    depends_on:
      - backend
    ports:
      - "80:80"
    volumes:
      - ./dist:/usr/share/nginx/html
      - ./nginx.conf:/etc/nginx/conf.d/default.conf

volumes:
  mysql_data:
```

### 5.2 启动服务

```bash
docker-compose up -d
```

## 6. 监控与维护

### 6.1 日志查看

后端应用日志位置：
```bash
tail -f /var/log/bookmanager/bookmanager.log
```

### 6.2 服务状态检查

检查服务是否正常运行：
```bash
# 检查后端服务
curl http://localhost:9111/user/info

# 检查前端页面
curl http://your_domain.com
```

### 6.3 常见问题处理

1. **服务启动失败**: 检查端口占用情况和配置文件是否正确
2. **数据库连接失败**: 检查数据库服务是否启动，用户名密码是否正确
3. **Redis连接失败**: 检查Redis服务是否启动，网络连接是否正常
4. **内存溢出**: 调整JVM参数，增加堆内存大小

## 7. 安全建议

1. 使用 HTTPS 加密传输
2. 定期更新系统和软件版本
3. 设置防火墙规则，只开放必要端口
4. 定期备份数据库
5. 使用强密码策略
6. 限制管理员权限操作