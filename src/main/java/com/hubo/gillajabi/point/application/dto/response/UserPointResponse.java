package com.hubo.gillajabi.point.application.dto.response;

import com.hubo.gillajabi.global.dto.CursorPageInfo;
import com.hubo.gillajabi.point.infrastructure.dto.response.UserPointPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserPointResponse {

    private List<UserPointPreview> userPointPreviews;
    private CursorPageInfo pageInfo;

    public UserPointResponse(List<UserPointPreview> userPointPreviews){
        this.userPointPreviews = userPointPreviews;
    }
}
