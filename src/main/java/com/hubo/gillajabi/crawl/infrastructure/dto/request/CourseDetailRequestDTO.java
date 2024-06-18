package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.hubo.gillajabi.crawl.domain.constant.CycleType;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruCourseResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetailRequestDTO {
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

    public static CourseDetailRequestDTO of(final DuruCourseResponse.Course item, final String startPoint, final String endPoint,
                                            final String startPointTransport, final String endPointTransport) {
        return new CourseDetailRequestDTO(
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
