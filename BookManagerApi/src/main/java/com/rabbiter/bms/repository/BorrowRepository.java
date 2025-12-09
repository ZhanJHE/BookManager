package com.rabbiter.bms.repository;

import com.rabbiter.bms.model.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 借阅Mapper
 */
@Mapper
public interface BorrowRepository {
    /**
     * 根据主键删除
     * @param borrowid 借阅ID
     * @return 删除结果
     */
    int deleteByPrimaryKey(Integer borrowid);

    /**
     * 插入
     * @param record 借阅记录
     * @return 插入结果
     */
    int insert(Borrow record);

    /**
     * 选择性插入
     * @param record 借阅记录
     * @return 插入结果
     */
    int insertSelective(Borrow record);

    /**
     * 根据主键查询
     * @param borrowid 借阅ID
     * @return 借阅记录
     */
    Borrow selectByPrimaryKey(Integer borrowid);

    /**
     * 根据主键选择性更新
     * @param record 借阅记录
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(Borrow record);

    /**
     * 根据主键更新
     * @param record 借阅记录
     * @return 更新结果
     */
    int updateByPrimaryKey(Borrow record);

    /**
     * 分页查询
     * @param begin 开始位置
     * @param size 查询大小
     * @return 借阅记录列表
     */
    List<Borrow> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 查询总数
     * @return 总数
     */
    Integer selectCount();

    /**
     * 根据搜索条件查询总数
     * @param searchParam 搜索参数
     * @return 总数
     */
    int selectCountBySearch(Map<String, Object> searchParam);

    /**
     * 根据搜索条件查询
     * @param searchParam 搜索参数
     * @return 借阅记录列表
     */
    List<Borrow> selectBySearch(Map<String, Object> searchParam);

    /**
     * 根据读者查询总数
     * @param userid 读者ID
     * @return 总数
     */
    Integer selectCountByReader(Integer userid);

    /**
     * 根据读者分页查询
     * @param begin 开始位置
     * @param size 查询大小
     * @param userid 读者ID
     * @return 借阅记录列表
     */
    List<Borrow> selectAllByLimitByReader(@Param("begin") Integer begin, @Param("size") Integer size, @Param("userid") Integer userid);
}