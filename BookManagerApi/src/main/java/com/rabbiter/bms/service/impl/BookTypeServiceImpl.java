package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BookInfoMapper;
import com.rabbiter.bms.service.BookTypeService;
import com.rabbiter.bms.mapper.BookTypeMapper;
import com.rabbiter.bms.model.BookType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书类型服务实现
 */
@Service
public class BookTypeServiceImpl implements BookTypeService {

    @Resource
    private BookTypeMapper bookTypeMapper;

    @Resource
    private BookInfoMapper bookInfoMapper;

    /**
     * 获取总数
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return bookTypeMapper.selectCount();
    }

    /**
     * 根据查询参数获取总数
     *
     * @param params
     * @return
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookTypeMapper.selectCountBySearch(params);
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<BookType> searchBookTypesByPage(Map<String, Object> params) {
        return bookTypeMapper.selectBySearch(params);
    }

    /**
     * 添加图书类型
     *
     * @param bookType
     * @return
     */
    @Override
    public Integer addBookType(BookType bookType) {
        return bookTypeMapper.insertSelective(bookType);
    }

    /**
     * 删除图书类型
     *
     * @param bookType
     * @return
     */
    @Override
    public Integer deleteBookType(BookType bookType) {
        int count = 0;
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("booktypeid", bookType.getBooktypeid());
            if(bookInfoMapper.selectCountByType(map) > 0) {
                return -1;
            }
            count = bookTypeMapper.deleteByPrimaryKey(bookType.getBooktypeid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 批量删除图书类型
     *
     * @param bookTypes
     * @return
     */
    @Override
    public Integer deleteBookTypes(List<BookType> bookTypes) {
        int count = 0;
        for(BookType bookType : bookTypes) {
            count += deleteBookType(bookType);
        }
        return count;
    }

    /**
     * 更新图书类型
     *
     * @param bookType
     * @return
     */
    @Override
    public Integer updateBookType(BookType bookType) {
        return bookTypeMapper.updateByPrimaryKeySelective(bookType);
    }

    /**
     * 查询所有图书类型
     *
     * @return
     */
    @Override
    public List<BookType> queryBookTypes() {
        return bookTypeMapper.selectAll();
    }
}