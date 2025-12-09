package com.rabbiter.bms.repository;

import com.rabbiter.bms.model.BookType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 图书类型Mapper
 */
@Mapper
public interface BookTypeRepository {
    /**
     * 根据主键删除
     * @param booktypeid 图书类型ID
     * @return 删除结果
     */
    int deleteByPrimaryKey(Integer booktypeid);

    /**
     * 插入
     * @param record 图书类型
     * @return 插入结果
     */
    int insert(BookType record);

    /**
     * 选择性插入
     * @param record 图书类型
     * @return 插入结果
     */
    int insertSelective(BookType record);

    /**
     * 根据主键查询
     * @param booktypeid 图书类型ID
     * @return 图书类型
     */
    BookType selectByPrimaryKey(Integer booktypeid);

    /**
     * 根据主键选择性更新
     * @param record 图书类型
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(BookType record);

    /**
     * 根据主键更新
     * @param record 图书类型
     * @return 更新结果
     */
    int updateByPrimaryKey(BookType record);

    /**
     * 分页查询
     * @param begin 开始位置
     * @param size 查询大小
     * @return 图书类型列表
     */
    List<BookType> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 查询总数
     * @return 总数
     */
    Integer selectCount();

    /**
     * 查询所有
     * @return 图书类型列表
     */
    List<BookType> selectAll();

    /**
     * 根据搜索条件查询总数
     * @param searchParam 搜索参数
     * @return 总数
     */
    int selectCountBySearch(Map<String, Object> searchParam);

    /**
     * 根据搜索条件查询
     * @param searchParam 搜索参数
     * @return 图书类型列表
     */
    List<BookType> selectBySearch(Map<String, Object> searchParam);
}