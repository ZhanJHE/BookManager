package com.rabbiter.bms.repository;

import com.rabbiter.bms.model.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 图书信息Mapper
 */
@Mapper
public interface BookInfoRepository {
    /**
     * 根据主键删除
     * @param bookid 图书ID
     * @return 删除结果
     */
    int deleteByPrimaryKey(Integer bookid);

    /**
     * 插入
     * @param record 图书信息
     * @return 插入结果
     */
    int insert(BookInfo record);

    /**
     * 选择性插入
     * @param record 图书信息
     * @return 插入结果
     */
    int insertSelective(BookInfo record);

    /**
     * 根据主键查询
     * @param bookid 图书ID
     * @return 图书信息
     */
    BookInfo selectByPrimaryKey(Integer bookid);

    /**
     * 根据主键选择性更新
     * @param record 图书信息
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(BookInfo record);

    /**
     * 根据主键更新
     * @param record 图书信息
     * @return 更新结果
     */
    int updateByPrimaryKey(BookInfo record);

    /**
     * 分页查询
     * @param begin 开始位置
     * @param size 查询大小
     * @return 图书信息列表
     */
    List<BookInfo> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

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
     * @return 图书信息列表
     */
    List<BookInfo> selectBySearch(Map<String, Object> searchParam);

    /**
     * 查询所有
     * @return 图书信息列表
     */
    List<BookInfo> selectAll();

    /**
     * 根据类型查询总数
     * @param map 查询参数
     * @return 总数
     */
    int selectCountByType(Map<String, Object> map);

    /**
     * 根据类型查询
     * @param map 查询参数
     * @return 图书信息列表
     */
    List<BookInfo> selectByType(Map<String, Object> map);
}