package com.project.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageResultDto<T> {
    private List<T> data;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalCount;

    public PageResultDto(List<T> data, int currentPage, int pageSize, int totalPages, long totalCount) {
        this.data = data;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }
}