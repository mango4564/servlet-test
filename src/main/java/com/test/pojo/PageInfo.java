package com.test.pojo;

import java.util.List;

/**
 * @author 专治八阿哥的孟老师
 */
public class PageInfo {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private List data;


    public PageInfo(Integer pageNum, Integer pageSize, Integer totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = (this.totalCount % this.pageSize == 0) ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public List getData() {
        return data;
    }
}
