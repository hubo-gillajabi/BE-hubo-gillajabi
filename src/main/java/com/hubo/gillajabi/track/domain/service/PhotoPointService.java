package com.hubo.gillajabi.track.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.track.application.dto.request.PhotoPointRequest;
import com.hubo.gillajabi.track.infrastructure.persistence.PhotoPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoPointService {

    private final PhotoPointRepository photoPointRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void uploadPhotoPoint(final PhotoPointRequest request, final String username) {
        Member member = memberRepository.getEntityByUserName(username);


    }
}
