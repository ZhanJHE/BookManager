package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.mapper.UserMapper;
import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private BorrowMapper borrowMapper;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userMapper.selectByUsernameAndPasswordAndIsAdmin(user.getUsername(), user.getUserpassword(), user.getIsadmin());
    }

    /**
     * 保存用户
     *
     * @param token
     * @param user
     */
    @Override
    public void saveUser(String token, User user) {
        // 设置redisTemplate对象key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // key是token，value是用户保存到redis中，超时时间1小时
        redisTemplate.opsForValue().set(token, user, 1, TimeUnit.HOURS);
    }

    /**
     * 获取用户
     *
     * @param token
     * @return
     */
    @Override
    public User getUser(String token) {
        // 根据token得到user
        return (User) redisTemplate.opsForValue().get(token);
    }

    /**
     * 移除用户
     *
     * @param token
     */
    @Override
    public void removeUser(String token) {
        // 移除token
        redisTemplate.delete(token);
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Integer register(String username, String password) {
        User tmp = userMapper.selectByUsername(username);
        if(tmp != null) return 0;  //账号重复

        User user = new User();
        user.setUsername(username);
        user.setUserpassword(password);
        user.setIsadmin((byte)0);
        return userMapper.insertSelective(user);
    }

    /**
     * 设置密码
     *
     * @param id
     * @param password
     */
    @Override
    public void setPassword(Integer id, String password) {
        User user = new User();
        user.setUserid(id);
        user.setUserpassword(password);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 获取总数
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return userMapper.selectCount();
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    /**
     * 根据查询参数获取总数
     *
     * @param params
     * @return
     */
    @Override
    public int getSearchCount(Map<String, Object> params) {
        return userMapper.selectCountBySearch(params);
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<User> searchUsersByPage(Map<String, Object> params) {
        return userMapper.selectBySearch(params);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public Integer addUser(User user) {
        return userMapper.insertSelective(user);
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @Override
    public Integer deleteUser(User user) {
        if(user.getUserid() == 1) return -2;
        Map<String, Object> map = new HashMap<>();
        map.put("userid", user.getUserid());
        if(borrowMapper.selectCountBySearch(map) > 0) {
            return -1;
        }
        return userMapper.deleteByPrimaryKey(user.getUserid());
    }

    /**
     * 批量删除用户
     *
     * @param users
     * @return
     */
    @Override
    public Integer deleteUsers(List<User> users) {
        int count = 0;
        for(User user : users) {
            count += deleteUser(user);
        }
        return count;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public Integer updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

}