package com.hubo.gillajabi.track.application.resolver;

import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.track.application.dto.response.TrackGpsPage;
import com.hubo.gillajabi.track.domain.service.TrackService;
import com.hubo.gillajabi.track.infrastructure.dto.response.TrackGpsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TrackResolver {

    private final TrackService trackService;

    @QueryMapping
    @UserOnly
    public TrackGpsPage getTrackGps(
            @Argument Long trackId
    ){
        String username = SecurityUtil.getCurrentUsername();
        TrackGpsDto trackGpsDto = trackService.getTrackGps(username, trackId);

        return new TrackGpsPage(trackGpsDto);
    }
}
