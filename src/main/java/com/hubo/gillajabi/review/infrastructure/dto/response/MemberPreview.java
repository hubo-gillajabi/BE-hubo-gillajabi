package com.hubo.gillajabi.review.infrastructure.dto.response;

import com.hubo.gillajabi.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberPreview {

    private String nickname;

    private String profileImageUrl;

    public static MemberPreview createByEntity(Member member) {
        return new MemberPreview(
                member.getNickName(),
                member.getProfileImageUrl()
        );
    }
}
