package com.rabbiter.bms.mapper;

import com.rabbiter.bms.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 用户Mapper
 */
public interface UserMapper {
    /**
     * 根据主键删除
     * @param userid 用户ID
     * @return 删除结果
     */
    int deleteByPrimaryKey(Integer userid);

    /**
     * 插入
     * @param record 用户
     * @return 插入结果
     */
    int insert(User record);

    /**
     * 选择性插入
     * @param record 用户
     * @return 插入结果
     */
    int insertSelective(User record);

    /**
     * 根据主键查询
     * @param userid 用户ID
     * @return 用户
     */
    User selectByPrimaryKey(Integer userid);

    /**
     * 根据主键选择性更新
     * @param record 用户
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键更新
     * @param record 用户
     * @return 更新结果
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据用户名、密码和是否为管理员查询
     * @param username 用户名
     * @param password 密码
     * @param isAdmin 是否为管理员
     * @return 用户
     */
    User selectByUsernameAndPasswordAndIsAdmin(@Param("username") String username,
                                  @Param("password") String password,
                                  @Param("isAdmin") Byte isAdmin);

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 用户
     */
    User selectByUsername(String username);

    /**
     * 分页查询
     * @param begin 开始位置
     * @param size 查询大小
     * @return 用户列表
     */
    List<User> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 查询总数
     * @return 总数
     */
    Integer selectCount();

    /**
     * 查询所有
     * @return 用户列表
     */
    List<User> selectAll();

    /**
     * 根据搜索条件查询总数
     * @param params 搜索参数
     * @return 总数
     */
    int selectCountBySearch(Map<String, Object> params);

    /**
     * 根据搜索条件查询
     * @param params 搜索参数
     * @return 用户列表
     */
    List<User> selectBySearch(Map<String, Object> params);
}