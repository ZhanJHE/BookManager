package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BookInfoMapper;
import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.model.BookInfo;
import com.rabbiter.bms.service.BookInfoService;
import org.springframework.stereotype.Service;

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
    private BookInfoMapper bookInfoMapper;

    @Resource
    private BorrowMapper borrowMapper;

    /**
     * 获取总数
     * @return 总数
     */
    @Override
    public Integer getCount() {
        return bookInfoMapper.selectCount();
    }

    /**
     * 查询所有图书信息
     * @return 图书信息列表
     */
    @Override
    public List<BookInfo> queryBookInfos() {
        return bookInfoMapper.selectAll();
    }

    /**
     * 根据ID查询图书信息
     * @param bookid 图书ID
     * @return 图书信息
     */
    @Override
    public BookInfo queryBookInfoById(Integer bookid) {
        return bookInfoMapper.selectByPrimaryKey(bookid);
    }

    /**
     * 获取搜索结果总数
     * @param params 搜索参数
     * @return 搜索结果总数
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookInfoMapper.selectCountBySearch(params);
    }

    /**
     * 分页搜索图书信息
     * @param params 搜索参数
     * @return 图书信息列表
     */
    @Override
    public List<BookInfo> searchBookInfosByPage(Map<String, Object> params) {
        return bookInfoMapper.selectBySearch(params);
    }

    /**
     * 添加图书信息
     * @param bookInfo 图书信息
     * @return 添加结果
     */
    @Override
    public Integer addBookInfo(BookInfo bookInfo) {
        return bookInfoMapper.insertSelective(bookInfo);
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
            if(borrowMapper.selectCountBySearch(map) > 0) {
                return -1;
            }
            count = bookInfoMapper.deleteByPrimaryKey(bookInfo.getBookid());
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
        return bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
    }

}