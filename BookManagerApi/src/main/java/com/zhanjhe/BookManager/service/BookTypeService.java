package com.zhanjhe.BookManager.service;

import java.util.List;
import java.util.Map;

import com.zhanjhe.BookManager.model.BookType;


/**
 * 图书类型服务
 */
public interface BookTypeService {
    /**
     * 获取总数
     * @return 总数
     */
    Integer getCount();

    /**
     * 获取搜索结果总数
     * @param params 搜索参数
     * @return 搜索结果总数
     */
    Integer getSearchCount(Map<String, Object> params);

    /**
     * 分页搜索图书类型
     * @param params 搜索参数
     * @return 图书类型列表
     */
    List<BookType> searchBookTypesByPage(Map<String, Object> params);

    /**
     * 添加图书类型
     * @param bookType 图书类型
     * @return 添加结果
     */
    Integer addBookType(BookType bookType);

    /**
     * 删除图书类型
     * @param bookType 图书类型
     * @return 删除结果
     */
    Integer deleteBookType(BookType bookType);

    /**
     * 批量删除图书类型
     * @param bookTypes 图书类型列表
     * @return 删除结果
     */
    Integer deleteBookTypes(List<BookType> bookTypes);

    /**
     * 更新图书类型
     * @param bookType 图书类型
     * @return 更新结果
     */
    Integer updateBookType(BookType bookType);

    /**
     * 查询所有图书类型
     * @return 图书类型列表
     */
    List<BookType> queryBookTypes();

}