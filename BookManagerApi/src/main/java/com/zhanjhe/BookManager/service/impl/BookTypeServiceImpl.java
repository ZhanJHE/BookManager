package com.zhanjhe.BookManager.service.impl;

import org.springframework.stereotype.Service;

import com.zhanjhe.BookManager.model.BookType;
import com.zhanjhe.BookManager.repository.BookInfoRepository;
import com.zhanjhe.BookManager.repository.BookTypeRepository;
import com.zhanjhe.BookManager.service.BookTypeService;

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
    private BookTypeRepository bookTypeRepository;

    @Resource
    private BookInfoRepository bookInfoRepository;

    /**
     * 获取总数
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return bookTypeRepository.selectCount();
    }

    /**
     * 根据查询参数获取总数
     *
     * @param params
     * @return
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return bookTypeRepository.selectCountBySearch(params);
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<BookType> searchBookTypesByPage(Map<String, Object> params) {
        return bookTypeRepository.selectBySearch(params);
    }

    /**
     * 添加图书类型
     *
     * @param bookType
     * @return
     */
    @Override
    public Integer addBookType(BookType bookType) {
        return bookTypeRepository.insertSelective(bookType);
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
            if(bookInfoRepository.selectCountByType(map) > 0) {
                return -1;
            }
            count = bookTypeRepository.deleteByPrimaryKey(bookType.getBooktypeid());
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
        return bookTypeRepository.updateByPrimaryKeySelective(bookType);
    }

    /**
     * 查询所有图书类型
     *
     * @return
     */
    @Override
    public List<BookType> queryBookTypes() {
        return bookTypeRepository.selectAll();
    }
}