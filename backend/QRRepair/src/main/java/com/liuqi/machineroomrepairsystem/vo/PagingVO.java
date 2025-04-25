package com.liuqi.machineroomrepairsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询结果集
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingVO <T>{
    private long total; // 总记录数
    private int totalPages; // 总页数
    private int currentPage; // 当前页码
    private int pageSize; // 每一页大小
    private List<T> data;
}
