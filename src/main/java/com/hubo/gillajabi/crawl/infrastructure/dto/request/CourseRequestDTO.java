package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import lombok.*;
import org.jsoup.Jsoup;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CourseRequestDTO {
    private String courseName;
    private Integer distance;
    private Integer totalRequiredHours;
    private CourseLevel level;
    private String shortDescription;
    private String courseNumber;
    private City city;
    private CourseTheme courseTheme;

    public static CourseRequestDTO of(DuruCourseResponse.Course item, City city, CourseTheme courseTheme) {
        CourseLevel level = CourseLevel.fromValue(item.getCrsLevel());
        String shortDescription = Jsoup.parse(item.getCrsSummary()).text();
        String courseNumber = parseCourseNumber(item.getCrsKorNm());
        return new CourseRequestDTO(
                item.getCrsKorNm(),
                Integer.parseInt(item.getCrsDstnc()),
                Integer.parseInt(item.getCrsTotlRqrmHour()),
                level,
                shortDescription,
                courseNumber,
                city,
                courseTheme
        );
    }

    private static String parseCourseNumber(final String courseName) {
        return courseName.split(" ")[1].replace("코스", "");
    }
}
