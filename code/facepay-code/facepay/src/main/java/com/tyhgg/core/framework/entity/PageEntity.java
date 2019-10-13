package com.tyhgg.core.framework.entity;

import java.io.Serializable;

public class PageEntity implements Serializable{


    /**
     *  实现分页的功能
     */
    private static final long serialVersionUID = 1L;
    
    public static final  int PAGE_SHOW_COUNT = 20;
    private int pageNum = 1;
    private int pageSize = 0;
    private int totalCount = 0;
    private String orderDirection;

    public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
        return pageSize > 0 ? pageSize : PAGE_SHOW_COUNT;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderDirection() {
        return "desc".equals(orderDirection) ? "desc" : "asc";
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 开始计数数，用于分页查询
     * @方法名: getStartIndex
     * @方法描述: 
     * @return int
     */
    public int getStartIndex() {
        int pageNn = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
        return pageNn * this.getPageSize();
    }
    
    /**
     * 计算总页数
     * 
     * @param totalCount
     * @param pageSize
     * @return int
     */
    public int getTotalPage() {
        int totalPage = 0;
        int size=this.getPageSize();
        int tempPage = totalCount % size;
        // 判断当前查询出来总共有多少页
        if (tempPage > 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
        }
        return totalPage;
    }

}
