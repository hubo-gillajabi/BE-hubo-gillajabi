package com.hubo.gillajabi.review.infrastructure.dto.request;

import com.hubo.gillajabi.review.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateEvent {

    private final Post post;


}
