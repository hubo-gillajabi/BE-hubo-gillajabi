package com.hubo.gillajabi.place.application.resolver;

import com.hubo.gillajabi.place.application.dto.response.PointDto;
import com.hubo.gillajabi.place.application.dto.response.PointResponse;
import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaceResolver {

    private final PlaceService placeService;

    @QueryMapping
    public PointResponse getPointByPlace(@Argument final String keyword,
                                         @Argument final Float latitude,
                                         @Argument final Float longitude,
                                         @Argument final Float radius,
                                         @Argument final Integer courseId) {
        PlaceType placeType = PlaceType.fromKoreanName(keyword);

        List<PointDto> response = placeService.getPointByPlace(placeType, latitude, longitude, radius, courseId);
        return new PointResponse(response);
    }


}
