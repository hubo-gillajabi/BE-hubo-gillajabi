package com.hubo.gillajabi.point.domain.service;

import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import com.hubo.gillajabi.point.domain.entity.UserPoint;
import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointDocumentRepository;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointRepository;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import com.hubo.gillajabi.track.domain.entity.TrackStatus;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackExceptionCode;
import com.hubo.gillajabi.track.infrastructure.persistence.PhotoPointRepository;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackRecordRepository;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackStatusRepository;
import com.hubo.gillajabi.track.infrastructure.timer.TrackStatusTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPointService {

    private final MemberRepository memberRepository;

    private final UserPointRepository userPointRepository;

    private final UserPointDocumentRepository userPointDocumentRepository;

    private final TrackStatusRepository trackStatusRepository;

    private final PhotoPointRepository photoPointRepository;

    private final TrackRecordRepository trackRecordRepository;

    private final TrackStatusTimer trackStatusTimer;

    /*
     * 사용자의 포인트를 저장한다.
     * 1. 사용자의 트랙이 활성화 되어있는지 확인한다.
     * 2. 사용자의 트랙이 활성화 되어있지 않다면 예외를 발생시킨다.
     * 3. 사용자의 트랙이 활성화 되어있다면 사용자의 포인트를 저장한다.
     * 4. 사용자의 포인트를 저장한 후 사용자의 트랙을 재시작한다.
     */
    @Transactional
    public void saveUserPoint(final String username, final UserPointRequest request) {
        final Member member = memberRepository.getEntityByUserName(username);

        if (!trackStatusTimer.isTrackActive(username)) {
            throw new TrackException(TrackExceptionCode.NOT_TRACKING);
        }

        final TrackStatus trackStatus = trackStatusRepository.getEntityById(TrackStatus.getTrackStatusKeyByMember(username));
        final Long trackId = trackStatus.getTrackId();

        final TrackRecord trackRecord = trackRecordRepository.getEntityById(trackId);

        final UserPoint userPoint = UserPoint.createByMemberAndTrackRecordAndRequest(member, request, trackRecord);
        final List<PhotoPoint> photoPoint = PhotoPoint.createByImageUrlAndTrackRecordAndUserPoint(request, trackRecord, userPoint);
        trackRecord.addPhotoPoint(photoPoint);
        userPoint.addPhotoPoint(photoPoint);

        userPointRepository.save(userPoint);

        final UserPointDocument userPointDocument = UserPointDocument.createByMemberAndUserPoint(member, userPoint);
        userPointDocumentRepository.save(userPointDocument);

        trackStatusTimer.restartTimerForMember(username);
    }

    @Transactional
    public void deleteUserPoint(final String username, final Long id) {
        final Member member = memberRepository.getEntityByUserName(username);

        final UserPoint userPoint = userPointRepository.getEntityById(id);
        userPoint.isOwner(member);

        userPoint.changeStatusToDeleted();

        final UserPointDocument userPointDocument = userPointDocumentRepository.findByUserPointId(id);
        userPointDocumentRepository.delete(userPointDocument);
    }
}
