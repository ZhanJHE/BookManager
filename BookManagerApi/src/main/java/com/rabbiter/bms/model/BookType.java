package com.rabbiter.bms.model;

/**
 * 图书类型
 */
public class BookType {
    /**
     * 图书类型ID
     */
    private Integer booktypeid;

    /**
     * 图书类型名称
     */
    private String booktypename;

    /**
     * 图书类型描述
     */
    private String booktypedesc;

    public Integer getBooktypeid() {
        return booktypeid;
    }

    public void setBooktypeid(Integer booktypeid) {
        this.booktypeid = booktypeid;
    }

    public String getBooktypename() {
        return booktypename;
    }

    public void setBooktypename(String booktypename) {
        this.booktypename = booktypename;
    }

    public String getBooktypedesc() {
        return booktypedesc;
    }

    public void setBooktypedesc(String booktypedesc) {
        this.booktypedesc = booktypedesc;
    }
}