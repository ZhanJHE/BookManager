package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.repository.BorrowRepository;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.service.BorrowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 借阅服务实现
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Resource
    private BorrowRepository borrowRepository;

    /**
     * 获取总数
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return borrowRepository.selectCount();
    }

    /**
     * 根据查询参数获取总数
     *
     * @param params
     * @return
     */
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return borrowRepository.selectCountBySearch(params);
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<Borrow> searchBorrowsByPage(Map<String, Object> params) {
        List<Borrow> borrows = borrowRepository.selectBySearch(params);
        // 添加string类型的时间显示
        for(Borrow borrow : borrows) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(borrow.getBorrowtime() != null) borrow.setBorrowtimestr(simpleDateFormat.format(borrow.getBorrowtime()));
            if(borrow.getReturntime() != null) borrow.setReturntimestr(simpleDateFormat.format(borrow.getReturntime()));
        }
        return borrows;
    }

    /**
     * 添加借阅
     *
     * @param borrow
     * @return
     */
    @Override
    public Integer addBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr()));
            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowRepository.insertSelective(borrow);
    }

    /**
     * 添加借阅2
     *
     * @param borrow
     * @return
     */
    @Override
    public Integer addBorrow2(Borrow borrow) {
        return borrowRepository.insertSelective(borrow);
    }

    /**
     * 删除借阅
     *
     * @param borrow
     * @return
     */
    @Override
    public Integer deleteBorrow(Borrow borrow) {
        // 先查询有没有还书
        Borrow borrow1 = borrowRepository.selectByPrimaryKey(borrow.getBorrowid());
        if(borrow1.getReturntime() == null) return 0;
        return borrowRepository.deleteByPrimaryKey(borrow.getBorrowid());
    }

    /**
     * 批量删除借阅
     *
     * @param borrows
     * @return
     */
    @Override
    public Integer deleteBorrows(List<Borrow> borrows) {
        int count = 0;
        for(Borrow borrow : borrows) {
            count += deleteBorrow(borrow);
        }
        return count;
    }

    /**
     * 更新借阅
     *
     * @param borrow
     * @return
     */
    @Override
    public Integer updateBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr()));
            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowRepository.updateByPrimaryKeySelective(borrow);
    }

    /**
     * 更新借阅2
     *
     * @param borrow
     * @return
     */
    @Override
    public Integer updateBorrow2(Borrow borrow) {
        return borrowRepository.updateByPrimaryKeySelective(borrow);
    }

    /**
     * 根据ID查询借阅
     *
     * @param borrowid
     * @return
     */
    @Override
    public Borrow queryBorrowsById(Integer borrowid) {
        return borrowRepository.selectByPrimaryKey(borrowid);
    }

}