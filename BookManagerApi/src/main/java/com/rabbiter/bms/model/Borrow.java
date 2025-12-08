package com.rabbiter.bms.model;

import java.util.Date;


/**
 * 借阅记录
 */
public class Borrow {
    /**
     * 借阅ID
     */
    private Integer borrowid;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 图书ID
     */
    private Integer bookid;

    /**
     * 图书名称
     */
    private String bookname;

    /**
     * 借阅时间
     */
    private Date borrowtime;

    /**
     * 借阅时间字符串
     */
    private String borrowtimestr;

    /**
     * 归还时间
     */
    private Date returntime;

    /**
     * 归还时间字符串
     */
    private String returntimestr;

    public Integer getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(Integer borrowid) {
        this.borrowid = borrowid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Date getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(Date borrowtime) {
        this.borrowtime = borrowtime;
    }

    public String getBorrowtimestr() {
        return borrowtimestr;
    }

    public void setBorrowtimestr(String borrowtimestr) {
        this.borrowtimestr = borrowtimestr;
    }

    public Date getReturntime() {
        return returntime;
    }

    public void setReturntime(Date returntime) {
        this.returntime = returntime;
    }

    public String getReturntimestr() {
        return returntimestr;
    }

    public void setReturntimestr(String returntimestr) {
        this.returntimestr = returntimestr;
    }
}