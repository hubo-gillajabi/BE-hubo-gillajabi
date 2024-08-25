package com.hubo.gillajabi.course.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GpxInfo {
    private Long id;
    private String gpx;
    private Long courseDetailId;
    private Long courseId;
}