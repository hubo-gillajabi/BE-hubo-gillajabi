package com.hubo.gillajabi.review.application.dto.response;

import com.hubo.gillajabi.global.common.dto.PageInfo;
import com.hubo.gillajabi.review.infrastructure.dto.response.PostPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostPreviewResponse {

    private List<PostPreview> postPreviewResponses;

    private PageInfo pageInfo;
}
