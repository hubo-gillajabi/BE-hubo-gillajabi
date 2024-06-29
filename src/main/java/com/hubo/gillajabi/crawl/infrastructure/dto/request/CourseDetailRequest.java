package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.domain.constant.CycleType;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiCourseResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetailRequest {
    private String courseName;
    private String tourInfo;
    private String courseDescription;
    private String startPoint;
    private String endPoint;
    private String startPointTransport;
    private String endPointTransport;
    private Integer totalTimeRequired;
    private String gpxPath;


    private CycleType cycleType;

    public static CourseDetailRequest of(final ApiCourseResponse.Course item, final String startPoint, final String endPoint,
                                         final String startPointTransport, final String endPointTransport) {
        return new CourseDetailRequest(
                item.getCrsKorNm(),
                item.getCrsTourInfo(),
                item.getCrsContents(),
                startPoint,
                endPoint,
                startPointTransport,
                endPointTransport,
                Integer.valueOf(item.getCrsTotlRqrmHour()),
                item.getGpxpath(),
                CycleType.fromValue(item)
        );
    }
}
