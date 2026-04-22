package com.xiaoyu.common.core.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends ArrayList<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    private Long total;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 总页数
     */
    private Integer pages;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total, Integer pageSize, Integer pageNum) {
        super(list);
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(List<T> list, Long total, Integer pageSize, Integer pageNum) {
        return new PageResult<>(list, total, pageSize, pageNum);
    }

    /**
     * 判断是否有下一页
     */
    public boolean hasNext() {
        return pageNum < pages;
    }

    /**
     * 判断是否有上一页
     */
    public boolean hasPrevious() {
        return pageNum > 1;
    }
}