package com.zhanjhe.BookManager.service.impl;

import org.springframework.stereotype.Service;

import com.zhanjhe.BookManager.model.BookInfo;
import com.zhanjhe.BookManager.repository.BookInfoRepository;
import com.zhanjhe.BookManager.repository.BorrowRepository;
import com.zhanjhe.BookManager.service.BookInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 图书信息服务实现
 */
@Service
public class BookInfoServiceImpl implements BookInfoService {

    @Resource
    private BookInfoRepository bookInfoRepository;

    @Resource
    private BorrowRepository borrowRepository;

    /**
     * 获取总数
     * @return 总数
     */
    @Override
    public Integer getCount() {
        return bookInfoRepository.selectCount();
    }

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    @Override
    public List<BookInfo> queryBookInfos() {
        return bookInfoRepository.selectAll();
    }

    /**
     * 根据ID查询图书信息
     * @param bookid 图书ID
     * @return 图书信息
     */
    @Override
    public BookInfo queryBookInfoById(Integer bookid) {
        return bookInfoRepository.selectByPrimaryKey(bookid);
    }

    /**
     * 获取搜索结果总数
     * @param params 搜索参数
     * @return 搜索结果总数
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookInfoRepository.selectCountBySearch(params);
    }

    /**
     * 分页搜索图书信息
     * @param params 搜索参数
     * @return 图书信息列表
     */
    @Override
    public List<BookInfo> searchBookInfosByPage(Map<String, Object> params) {
        return bookInfoRepository.selectBySearch(params);
    }

    /**
     * 添加图书信息
     * @param bookInfo 图书信息
     * @return 添加结果
     */
    @Override
    public Integer addBookInfo(BookInfo bookInfo) {
        return bookInfoRepository.insertSelective(bookInfo);
    }

    /**
     * 删除图书信息
     * @param bookInfo 图书信息
     * @return 删除结果
     */
    @Override
    public Integer deleteBookInfo(BookInfo bookInfo) {
        int count = 0;
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("bookId", bookInfo.getBookid());
            if(borrowRepository.selectCountBySearch(map) > 0) {
                return -1;
            }
            count = bookInfoRepository.deleteByPrimaryKey(bookInfo.getBookid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 批量删除图书信息
     * @param bookInfos 图书信息列表
     * @return 删除结果
     */
    @Override
    public Integer deleteBookInfos(List<BookInfo> bookInfos) {
        int count = 0;
        for(BookInfo bookInfo : bookInfos) {
            count += deleteBookInfo(bookInfo);
        }
        return count;
    }

    /**
     * 更新图书信息
     * @param bookInfo 图书信息
     * @return 更新结果
     */
    @Override
    public Integer updateBookInfo(BookInfo bookInfo) {
        return bookInfoRepository.updateByPrimaryKeySelective(bookInfo);
    }

}