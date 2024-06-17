package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CourseThemeRequestDTO {

    private String name;
    private String shortDescription;
    private String description;

    public static CourseThemeRequestDTO from(DuruThemeResponse.Theme item) {
        return new CourseThemeRequestDTO(item.getThemeNm(), item.getLinemsg(), item.getThemedescs());
    }

}
