# 数据库设计文档

## 1. 概述

本文档详细描述了 BookManager 图书管理系统的数据库设计方案，包括各个数据表的结构、字段说明、关系设计等内容。

## 2. 数据库环境

- **数据库系统**: MySQL 8.0+
- **字符集**: utf8mb3
- **排序规则**: utf8mb3_general_ci

## 3. 数据表设计

### 3.1 用户表 (user)

存储系统用户信息，包括管理员和普通用户。

```sql
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `userPassword` varchar(20) CHARACTERATE utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `isAdmin` tinyint NOT NULL COMMENT '1是管理员，0非管理员',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1006 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;
```

**字段说明**:

| 字段名 | 类型 | 允许空 | 默认值 | 主键 | 说明 |
|--------|------|--------|--------|------|------|
| userId | int | 否 | 无 | 是 | 用户ID，自增长主键 |
| userName | varchar(20) | 否 | 无 | 否 | 用户名，唯一标识 |
| userPassword | varchar(20) | 否 | 无 | 否 | 用户密码 |
| isAdmin | tinyint | 否 | 无 | 否 | 用户角色标识，1为管理员，0为普通用户 |

### 3.2 图书类型表 (book_type)

存储图书分类信息。

```sql
CREATE TABLE `book_type` (
  `bookTypeId` int NOT NULL AUTO_INCREMENT,
  `bookTypeName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `bookTypeDesc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`bookTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;
```

**字段说明**:

| 字段名 | 类型 | 允许空 | 默认值 | 主键 | 说明 |
|--------|------|--------|--------|------|------|
| bookTypeId | int | 否 | 无 | 是 | 图书类型ID，自增长主键 |
| bookTypeName | varchar(20) | 否 | 无 | 否 | 图书类型名称 |
| bookTypeDesc | varchar(255) | 否 | 无 | 否 | 图书类型描述 |

### 3.3 图书信息表 (book_info)

存储图书详细信息。

```sql
CREATE TABLE `book_info` (
  `bookId` int NOT NULL AUTO_INCREMENT,
  `bookName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `bookAuthor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `bookPrice` decimal(10, 2) NOT NULL,
  `bookTypeId` int NOT NULL,
  `bookDesc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '书籍描述',
  `isBorrowed` tinyint NOT NULL COMMENT '1表示借出，0表示已还',
  `bookImg` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '书籍图片',
  PRIMARY KEY (`bookId`) USING BTREE,
  INDEX `fk_book_info_book_type_1`(`bookTypeId` ASC) USING BTREE,
  CONSTRAINT `book_info_ibfk_1` FOREIGN KEY (`bookTypeId`) REFERENCES `book_type` (`bookTypeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;
```

**字段说明**:

| 字段名 | 类型 | 允许空 | 默认值 | 主键 | 说明 |
|--------|------|--------|--------|------|------|
| bookId | int | 否 | 无 | 是 | 图书ID，自增长主键 |
| bookName | varchar(50) | 否 | 无 | 否 | 图书名称 |
| bookAuthor | varchar(50) | 否 | 无 | 否 | 图书作者 |
| bookPrice | decimal(10,2) | 否 | 无 | 否 | 图书价格 |
| bookTypeId | int | 否 | 无 | 否 | 图书类型ID，外键关联book_type表 |
| bookDesc | varchar(255) | 否 | 无 | 否 | 图书描述 |
| isBorrowed | tinyint | 否 | 无 | 否 | 借阅状态，1表示借出，0表示在馆 |
| bookImg | varchar(255) | 是 | NULL | 否 | 图书封面图片路径 |

### 3.4 借阅记录表 (borrow)

存储图书借阅和归还记录。

```sql
CREATE TABLE `borrow` (
  `borrowId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `bookId` int NOT NULL,
  `borrowTime` datetime NOT NULL,
  `returnTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`borrowId`) USING BTREE,
  INDEX `fk_borrow_user_1`(`userId` ASC) USING BTREE,
  INDEX `fk_borrow_book_info_1`(`bookId` ASC) USING BTREE,
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book_info` (`bookId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;
```

**字段说明**:

| 字段名 | 类型 | 允许空 | 默认值 | 主键 | 说明 |
|--------|------|--------|--------|------|------|
| borrowId | int | 否 | 无 | 是 | 借阅记录ID，自增长主键 |
| userId | int | 否 | 无 | 否 | 用户ID，外键关联user表 |
| bookId | int | 否 | 无 | 否 | 图书ID，外键关联book_info表 |
| borrowTime | datetime | 否 | 无 | 否 | 借书时间 |
| returnTime | datetime | 是 | NULL | 否 | 还书时间，未还书时为空 |

## 4. 表关系图

```
+-----------+          +-------------+
|   user    |          |  book_type  |
+-----------+          +-------------+
| userId (PK)|        | bookTypeId(PK)|
| userName   |        | bookTypeName |
| userPwd    |        | bookTypeDesc |
| isAdmin    |        +-------------+
+-----------+
      |
      | (1:N)
      |
+-----------+
|  borrow   |
+-----------+
| borrowId  |
| userId (FK)-------+
| bookId (FK)------------------+
| borrowTime|                  |
| returnTime|                  |
+-----------+                  |
      |                        |
      | (N:1)                  | (N:1)
      |                        |
+-----------+          +-------------+
| book_info |          |  book_type  |
+-----------+          +-------------+
| bookId (PK)|        | bookTypeId(PK)|
| bookName   |        | bookTypeName |
| bookAuthor |        | bookTypeDesc |
| bookPrice  |        +-------------+
| bookTypeId(FK)-------------+
| bookDesc   |
| isBorrowed |
| bookImg    |
+-----------+
```

## 5. 索引设计

### 5.1 主键索引
每个表都有一个主键索引，确保每条记录的唯一性。

### 5.2 外键索引
- book_info.bookTypeId 上建立了外键索引，提高关联查询效率
- borrow.userId 和 borrow.bookId 上建立了外键索引，提高借阅查询效率

### 5.3 其他索引
可根据实际查询需求添加其他索引，如：
- user.userName 上的唯一索引，防止用户名重复
- book_info.bookName 上的索引，提高图书名称查询效率

## 6. 数据初始化

系统包含初始数据用于演示和测试：

1. **用户数据**:
   - 管理员账户: admin/123456
   - 普通用户示例: 张三/123456, 李四/123456

2. **图书类型数据**:
   - 计算机科学
   - 历史
   - 文学
   - 科幻
   - 哲学
   - 小说
   - 外语学习
   - 自然科学
   - 艺术
   - 社会科学

3. **图书数据**:
   - 包含多本示例图书，覆盖各种类型

4. **借阅数据**:
   - 包含部分借阅和归还记录，用于演示借阅功能

## 7. 备份与恢复

### 7.1 备份策略
建议定期备份数据库，可使用以下命令：

```bash
mysqldump -u username -p book_manager > book_manager_backup_$(date +"%Y%m%d").sql
```

### 7.2 恢复方法
```bash
mysql -u username -p book_manager < book_manager_backup.sql
```

## 8. 性能优化建议

1. 根据查询模式添加适当的索引
2. 定期分析和优化慢查询
3. 考虑对大表进行分区处理
4. 合理设置数据库缓存参数
5. 定期清理过期的借阅记录