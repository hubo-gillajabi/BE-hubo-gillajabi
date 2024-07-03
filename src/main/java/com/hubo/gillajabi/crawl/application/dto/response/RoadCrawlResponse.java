package com.hubo.gillajabi.crawl.application.dto.response;

import com.hubo.gillajabi.crawl.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoadCrawlResponse {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "CrawlResponse.Course", description = "코스 크롤링 갯수 DTO")
    public static class CourseResult {
        private Integer cityCount;
        private Integer courseCount;
        private Integer courseDetailCount;
        private Integer gpxInfoCount;

        public static CourseResult of(final List<City> cities, final List<Course> courses,
                                      final List<CourseDetail> courseDetails, final List<GpxInfo> gpxInfos) {
            CourseResult courseResult = new CourseResult();
            courseResult.setCityCount(cities.size());
            courseResult.setCourseCount(courses.size());
            courseResult.setCourseDetailCount(courseDetails.size());
            courseResult.setGpxInfoCount(gpxInfos.size());
            return courseResult;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "CrawlResponse.Theme", description = "코스 테마 갯수 DTO")
    public static class ThemeResult{
        private Integer themeCount;

        public static ThemeResult from(final List<CourseTheme> themes) {
            ThemeResult theme = new ThemeResult();
            theme.setThemeCount(themes.size());
            return theme;
        }
    }


}
