package com.zhanjhe.BookManager.service;

import java.util.List;
import java.util.Map;

import com.zhanjhe.BookManager.model.User;


/**
 * 用户服务
 */
public interface UserService {

    /**
     * 登录
     * @param user 用户
     * @return 用户
     */
    User login(User user);

    /**
     * 保存用户
     * @param token token
     * @param user 用户
     */
    void saveUser(String token, User user);

    /**
     * 获取用户
     * @param token token
     * @return 用户
     */
    User getUser(String token);

    /**
     * 移除用户
     * @param token token
     */
    void removeUser(String token);

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    Integer register(String username, String password);

    /**
     * 设置密码
     * @param id 用户ID
     * @param password 密码
     */
    void setPassword(Integer id, String password);

    /**
     * 获取总数
     * @return 总数
     */
    Integer getCount();

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> queryUsers();

    /**
     * 获取搜索结果总数
     * @param searchParam 搜索参数
     * @return 搜索结果总数
     */
    int getSearchCount(Map<String, Object> searchParam);

    /**
     * 分页搜索用户
     * @param params 搜索参数
     * @return 用户列表
     */
    List<User> searchUsersByPage(Map<String, Object> params);

    /**
     * 添加用户
     * @param user 用户
     * @return 添加结果
     */
    Integer addUser(User user);

    /**
     * 删除用户
     * @param user 用户
     * @return 删除结果
     */
    Integer deleteUser(User user);

    /**
     * 批量删除用户
     * @param users 用户列表
     * @return 删除结果
     */
    Integer deleteUsers(List<User> users);

    /**
     * 更新用户
     * @param user 用户
     * @return 更新结果
     */
    Integer updateUser(User user);
}