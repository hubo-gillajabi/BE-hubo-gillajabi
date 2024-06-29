package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiThemeResponse;
import lombok.*;
import org.jsoup.Jsoup;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CourseThemeRequest {

    private String name;
    private String shortDescription;
    private String description;

    public static CourseThemeRequest from(ApiThemeResponse.Theme item) {
        return new CourseThemeRequest(item.getThemeNm(), item.getLinemsg(), extractText(item.getThemedescs()));
    }

    private static String extractText(String htmlContent) {
        return Jsoup.parse(htmlContent).text();
    }
}
