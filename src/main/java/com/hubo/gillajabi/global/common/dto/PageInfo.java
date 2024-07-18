package com.hubo.gillajabi.global.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageInfo {
    private int totalPages;
    private int totalElements;
    private int size;
    private int number;

    public PageInfo(int totalPages, int totalElements, int size, int number) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.number = number;
    }

    public static PageInfo from(Page<?> page) {
        return new PageInfo(
                page.getTotalPages(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber()
        );
    }
}