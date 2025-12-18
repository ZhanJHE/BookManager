# API 接口文档

## 1. 概述

本文档描述了 BookManager 图书管理系统的后端 RESTful API 接口规范。所有接口均返回 JSON 格式数据。

### 1.1 响应格式

所有接口返回的数据格式如下：

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

字段说明：
- `code`: 状态码，200 表示成功，其他值表示失败
- `msg`: 响应消息
- `data`: 返回的具体数据

### 1.2 认证方式

大部分接口需要认证，通过 Session 方式进行身份验证。

## 2. 用户相关接口

### 2.1 用户登录

**接口地址**: `POST /user/login`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userName | string | 是 | 用户名 |
| userPassword | string | 是 | 密码 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "登录成功",
  "data": {
    "userId": 1,
    "userName": "admin",
    "isAdmin": 1
  }
}
```

### 2.2 用户注册

**接口地址**: `POST /user/register`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userName | string | 是 | 用户名 |
| userPassword | string | 是 | 密码 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "注册成功",
  "data": null
}
```

### 2.3 获取当前用户信息

**接口地址**: `GET /user/info`

**响应示例**:
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "userId": 1,
    "userName": "admin",
    "isAdmin": 1
  }
}
```

### 2.4 用户退出登录

**接口地址**: `POST /user/logout`

**响应示例**:
```json
{
  "code": 200,
  "msg": "退出成功",
  "data": null
}
```

### 2.5 修改密码

**接口地址**: `PUT /user/updatePassword`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| oldPassword | string | 是 | 原密码 |
| newPassword | string | 是 | 新密码 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "密码修改成功",
  "data": null
}
```

### 2.6 获取所有用户（仅管理员）

**接口地址**: `GET /user/findAll`

**响应示例**:
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "userId": 1,
      "userName": "admin",
      "userPassword": "123456",
      "isAdmin": 1
    },
    {
      "userId": 2,
      "userName": "张三",
      "userPassword": "123456",
      "isAdmin": 0
    }
  ]
}
```

### 2.7 删除用户（仅管理员）

**接口地址**: `DELETE /user/delete/{userId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | int | 是 | 用户ID |

**响应示例**:
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

## 3. 图书类型相关接口

### 3.1 获取所有图书类型

**接口地址**: `GET /booktype/findAll`

**响应示例**:
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "bookTypeId": 1,
      "bookTypeName": "计算机科学",
      "bookTypeDesc": "关于计算机技术与应用的书籍"
    },
    {
      "bookTypeId": 2,
      "bookTypeName": "历史",
      "bookTypeDesc": "探索人类历史和文化的书籍"
    }
  ]
}
```

### 3.2 添加图书类型（仅管理员）

**接口地址**: `POST /booktype/add`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bookTypeName | string | 是 | 类型名称 |
| bookTypeDesc | string | 是 | 类型描述 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "添加成功",
  "data": null
}
```

### 3.3 更新图书类型（仅管理员）

**接口地址**: `PUT /booktype/update`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bookTypeId | int | 是 | 类型ID |
| bookTypeName | string | 是 | 类型名称 |
| bookTypeDesc | string | 是 | 类型描述 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 3.4 删除图书类型（仅管理员）

**接口地址**: `DELETE /booktype/delete/{bookTypeId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bookTypeId | int | 是 | 类型ID |

**响应示例**:
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

## 4. 图书信息相关接口

### 4.1 分页查询图书信息

**接口地址**: `GET /bookinfo/findByPage`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页条数，默认10 |
| bookName | string | 否 | 图书名称（模糊查询） |
| bookTypeId | int | 否 | 图书类型ID |

**响应示例**:
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "total": 2,
    "rows": [
      {
        "bookId": 1,
        "bookName": "道德经",
        "bookAuthor": "龙子韬",
        "bookPrice": 257.56,
        "bookTypeId": 7,
        "bookDesc": "一部道家哲学经典，阐述了宇宙本源、自然法则以及无为而治的思想。",
        "isBorrowed": 0,
        "bookImg": "/files/cover_461.png",
        "bookTypeName": "外语学习"
      }
    ]
  }
}
```

### 4.2 添加图书（仅管理员）

**接口地址**: `POST /bookinfo/add`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bookName | string | 是 | 图书名称 |
| bookAuthor | string | 是 | 图书作者 |
| bookPrice | number | 是 | 图书价格 |
| bookTypeId | int | 是 | 图书类型ID |
| bookDesc | string | 是 | 图书描述 |
| bookImg | string | 否 | 图书封面图片路径 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "添加成功",
  "data": null
}
```

### 4.3 更新图书（仅管理员）

**接口地址**: `PUT /bookinfo/update`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bookId | int | 是 | 图书ID |
| bookName | string | 是 | 图书名称 |
| bookAuthor | string | 是 | 图书作者 |
| bookPrice | number | 是 | 图书价格 |
| bookTypeId | int | 是 | 图书类型ID |
| bookDesc | string | 是 | 图书描述 |
| bookImg | string | 否 | 图书封面图片路径 |

**响应示例**:
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 4.4 删除图书（仅管理员）

**接口地址**: `DELETE /bookinfo/delete/{bookId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| bookId | int | 是 | 图书ID |

**响应示例**:
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

## 5. 借阅相关接口

### 5.1 获取借阅信息

**接口地址**: `GET /borrow/findAll`

**响应示例**:
```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "borrowId": 48,
      "userId": 1,
      "bookId": 1,
      "borrowTime": "2025-12-18 23:11:20",
      "returnTime": "2025-12-18 23:11:55",
      "userName": "admin",
      "bookName": "道德经"
    }
  ]
}
```

### 5.2 借书

**接口地址**: `POST /borrow/add`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | int | 是 | 用户ID |
| bookId | int | 是 | 图书ID |

**响应示例**:
```json
{
  "code": 200,
  "msg": "借阅成功",
  "data": null
}
```

### 5.3 还书

**接口地址**: `PUT /borrow/update`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| borrowId | int | 是 | 借阅记录ID |

**响应示例**:
```json
{
  "code": 200,
  "msg": "归还成功",
  "data": null
}
```

## 6. 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 500 | 系统内部错误 |
| 401 | 未认证 |
| 403 | 权限不足 |
| 1001 | 用户名或密码错误 |
| 1002 | 用户名已存在 |
| 1003 | 原密码错误 |
| 1004 | 图书已借出 |
| 1005 | 图书已归还 |