package com.zhanjhe.BookManager.service;

import java.util.List;
import java.util.Map;

import com.zhanjhe.BookManager.model.BookInfo;


/**
 * 图书信息服务
 */
public interface BookInfoService {
    /**
     * 获取总数
     * @return 总数
     */
    Integer getCount();

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    List<BookInfo> queryBookInfos();

    /**
     * 根据ID查询图书信息
     * @param bookid 图书ID
     * @return 图书信息
     */
    BookInfo queryBookInfoById(Integer bookid);

    /**
     * 获取搜索结果总数
     * @param params 搜索参数
     * @return 搜索结果总数
     */
    Integer getSearchCount(Map<String, Object> params);

    /**
     * 分页搜索图书信息
     * @param params 搜索参数
     * @return 图书信息列表
     */
    List<BookInfo> searchBookInfosByPage(Map<String, Object> params);

    /**
     * 添加图书信息
     * @param bookInfo 图书信息
     * @return 添加结果
     */
    Integer addBookInfo(BookInfo bookInfo);

    /**
     * 删除图书信息
     * @param bookInfo 图书信息
     * @return 删除结果
     */
    Integer deleteBookInfo(BookInfo bookInfo);

    /**
     * 批量删除图书信息
     * @param bookInfos 图书信息列表
     * @return 删除结果
     */
    Integer deleteBookInfos(List<BookInfo> bookInfos);

    /**
     * 更新图书信息
     * @param bookInfo 图书信息
     * @return 更新结果
     */
    Integer updateBookInfo(BookInfo bookInfo);
}