package com.hubo.gillajabi.review.application.resolver;

import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.review.application.dto.response.PostPreviewResponse;
import com.hubo.gillajabi.review.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostResolver {

    private final PostService postService;

//    @UserOnly
    @QueryMapping
    public PostPreviewResponse getPostPreviews(
            @Argument("keyword") String keyword,
            @Argument("bookmarked") Boolean bookmarked,
            @Argument("page") Long page,
            @Argument("size") Long size) {
        String username = SecurityUtil.getCurrentUsername();
        return postService.getPostPreviews(keyword, username, bookmarked, page, size);
    }


}
