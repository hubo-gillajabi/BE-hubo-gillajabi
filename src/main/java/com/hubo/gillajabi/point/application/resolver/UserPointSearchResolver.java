package com.hubo.gillajabi.point.application.resolver;

import com.hubo.gillajabi.login.application.annotation.MemberOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.point.application.dto.response.UserPointResponse;
import com.hubo.gillajabi.point.domain.service.UserPointSearchService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@RequiredArgsConstructor
@Validated
public class UserPointSearchResolver {

    private final UserPointSearchService userPointSearchService;

    @QueryMapping
    @MemberOnly
    public UserPointResponse getUsedPointPreviews(@Argument Boolean bookmarked,
                                                  @Argument String cursor,
                                                  @Argument int limit) {
        String username = SecurityUtil.getCurrentUsername();
        return userPointSearchService.getUserPointPreviews(bookmarked, cursor, limit, username);
    }

    @QueryMapping
    @MemberOnly
    public UserPointResponse getUsedPointPreviewsByCourse(
            @Argument Long courseId,
            @Argument @NotNull @Min(-90) @Max(90) Double latitude,
            @Argument @NotNull @Min(-180) @Max(180) Double longitude,
            @Argument @NotNull @Min(0) Double radius) {
        return userPointSearchService.getUserPointPreviewsByCourse(courseId, latitude, longitude, radius);
    }

}
