package com.hubo.gillajabi.global.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageInfo {
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;

    public PageInfo(int totalPages, long totalElements, int size, int number) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.number = number;
    }

    public static PageInfo from(Page<?> page) {
        return new PageInfo(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber()
        );
    }
}