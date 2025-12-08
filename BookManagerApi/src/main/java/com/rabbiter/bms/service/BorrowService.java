package com.rabbiter.bms.service;

import com.rabbiter.bms.model.Borrow;

import java.util.List;
import java.util.Map;


/**
 * 借阅服务
 */
public interface BorrowService {
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
     * 分页搜索借阅记录
     * @param params 搜索参数
     * @return 借阅记录列表
     */
    List<Borrow> searchBorrowsByPage(Map<String, Object> params);

    /**
     * 添加借阅记录
     * @param borrow 借阅记录
     * @return 添加结果
     */
    Integer addBorrow(Borrow borrow);

    /**
     * 添加借阅记录2
     * @param borrow 借阅记录
     * @return 添加结果
     */
    Integer addBorrow2(Borrow borrow);

    /**
     * 删除借阅记录
     * @param borrow 借阅记录
     * @return 删除结果
     */
    Integer deleteBorrow(Borrow borrow);

    /**
     * 批量删除借阅记录
     * @param borrows 借阅记录列表
     * @return 删除结果
     */
    Integer deleteBorrows(List<Borrow> borrows);

    /**
     * 更新借阅记录
     * @param borrow 借阅记录
     * @return 更新结果
     */
    Integer updateBorrow(Borrow borrow);

    /**
     * 更新借阅记录2
     * @param borrow 借阅记录
     * @return 更新结果
     */
    Integer updateBorrow2(Borrow borrow);

    /**
     * 根据ID查询借阅记录
     * @param borrowid 借阅ID
     * @return 借阅记录
     */
    Borrow queryBorrowsById(Integer borrowid);
}