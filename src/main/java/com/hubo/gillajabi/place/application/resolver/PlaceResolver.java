package com.hubo.gillajabi.place.application.resolver;

import com.hubo.gillajabi.place.application.dto.response.PointDto;
import com.hubo.gillajabi.place.application.dto.response.PointResponse;
import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.service.PlaceService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class PlaceResolver {

    private final PlaceService placeService;

    @QueryMapping
    public PointResponse getPointByPlace(@Argument @NotNull final String keyword,
                                         @Argument @NotNull @Min(-90) @Max(90) Double latitude,
                                         @Argument @NotNull @Min(-180) @Max(180) Double longitude,
                                         @Argument @NotNull final Double radius,
                                         @Argument final Integer courseId) {
        PlaceType placeType = PlaceType.fromKoreanName(keyword);

        List<PointDto> response = placeService.getPointByPlace(placeType, latitude, longitude, radius, courseId);
        return new PointResponse(response);
    }


}
