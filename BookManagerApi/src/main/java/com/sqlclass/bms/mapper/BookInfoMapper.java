package com.sqlclass.bms.mapper;

import com.sqlclass.bms.model.BookInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * BookInfoMapper 接口定义了与图书信息相关的数据库操作。
 * MyBatis 会根据这个接口，通过对应的 XML 映射文件 (BookInfoMapper.xml) 来实现具体的 SQL 操作。
 */
public interface BookInfoMapper {
    /**
     * 根据主键 ID 删除图书信息。
     *
     * @param bookid 图书的主键 ID。
     * @return 返回影响的行数，通常为 1 表示删除成功，0 表示未找到对应记录。
     */
    int deleteByPrimaryKey(Integer bookid);

    /**
     * 插入一条完整的图书信息记录。
     *
     * @param record 包含完整图书信息的 BookInfo 对象。
     * @return 返回影响的行数，通常为 1 表示插入成功。
     */
    int insert(BookInfo record);

    /**
     * 选择性地插入一条图书信息记录。
     * 只会插入 record 对象中非 null 的字段。
     *
     * @param record 包含部分或全部图书信息的 BookInfo 对象。
     * @return 返回影响的行数，通常为 1 表示插入成功。
     */
    int insertSelective(BookInfo record);

    /**
     * 根据主键 ID 查询图书信息。
     *
     * @param bookid 图书的主键 ID。
     * @return 返回对应的图书信息对象，如果未找到则返回 null。
     */
    BookInfo selectByPrimaryKey(Integer bookid);

    /**
     * 根据主键 ID 选择性地更新图书信息。
     * 只会更新 record 对象中非 null 的字段。
     *
     * @param record 包含待更新字段的 BookInfo 对象，主键 ID 必须存在。
     * @return 返回影响的行数，通常为 1 表示更新成功。
     */
    int updateByPrimaryKeySelective(BookInfo record);

    /**
     * 根据主键 ID 更新一条完整的图书信息记录。
     *
     * @param record 包含完整新信息的 BookInfo 对象，主键 ID 必须存在。
     * @return 返回影响的行数，通常为 1 表示更新成功。
     */
    int updateByPrimaryKey(BookInfo record);

    /**
     * 分页查询所有图书信息。
     *
     * @param begin 分页查询的起始位置。
     * @param size  每页查询的数量。
     * @return 返回当前页的图书信息列表。
     */
    List<BookInfo> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 查询图书总数。
     *
     * @return 返回图书信息表中的记录总数。
     */
    Integer selectCount();

    /**
     * 根据搜索条件查询匹配的图书数量。
     *
     * @param searchParam 包含搜索条件的 Map，例如：{'name': 'Java', 'author': 'AuthorName'}。
     * @return 返回符合搜索条件的图书总数。
     */
    int selectCountBySearch(Map<String, Object> searchParam);

    /**
     * 根据搜索条件查询图书信息。
     *
     * @param searchParam 包含搜索条件的 Map，例如：{'name': 'Java', 'author': 'AuthorName'}。
     * @return 返回符合搜索条件的图书信息列表。
     */
    List<BookInfo> selectBySearch(Map<String, Object> searchParam);

    /**
     * 查询所有图书信息。
     *
     * @return 返回包含所有图书信息的列表。
     */
    List<BookInfo> selectAll();

    /**
     * 根据图书类型查询该类型的图书数量。
     *
     * @param map 包含类型 ID 的 Map，例如：{'typeid': 1}。
     * @return 返回指定类型的图书总数。
     */
    int selectCountByType(Map<String, Object> map);

    /**
     * 根据图书类型查询图书信息。
     *
     * @param map 包含类型 ID 和分页参数的 Map，例如：{'typeid': 1, 'begin': 0, 'size': 10}。
     * @return 返回指定类型的图书信息列表。
     */
    List<BookInfo> selectByType(Map<String, Object> map);
}