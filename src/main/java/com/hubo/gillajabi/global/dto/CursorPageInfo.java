package com.hubo.gillajabi.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorPageInfo {
    private String nextCursor;
    private Boolean hasNextPage;
}