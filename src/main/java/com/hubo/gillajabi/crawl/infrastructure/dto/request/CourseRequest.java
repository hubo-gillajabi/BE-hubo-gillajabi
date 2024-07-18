package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.domain.constant.CourseLevel;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import lombok.*;
import org.jsoup.Jsoup;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CourseRequest {
    private String courseName;
    private Integer distance;
    private Integer totalRequiredHours;
    private CourseLevel level;
    private String shortDescription;
    private String courseNumber;
    private City city;
    private CourseTheme courseTheme;

    public static CourseRequest of(ApiCourseResponse.Course item, City city, CourseTheme courseTheme) {
        CourseLevel level = CourseLevel.fromValue(item.getCrsLevel());
        String crsSummary = item.getCrsSummary();
        String shortDescription = (crsSummary != null) ? Jsoup.parse(crsSummary).text() : "";
        String courseNumber = parseCourseNumber(item.getCrsKorNm());
        return new CourseRequest(
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
        if (courseName == null || courseName.split(" ").length < 2) {
            throw new IllegalArgumentException("잘못된 코스 이름 : " + courseName);
        }
        return courseName.split(" ")[1].replace("코스", "");
    }

}
