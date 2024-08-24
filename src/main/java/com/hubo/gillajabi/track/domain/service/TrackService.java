package com.hubo.gillajabi.track.domain.service;

import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.location.application.dto.request.LocationHandlingRequest;
import com.hubo.gillajabi.location.domain.service.constant.LocationCollectionType;
import com.hubo.gillajabi.location.domain.service.constant.LocationServiceType;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.track.application.dto.request.TrackStartRequest;
import com.hubo.gillajabi.track.application.dto.request.TrackSendRequest;
import com.hubo.gillajabi.track.application.dto.response.StartTrackResponse;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import com.hubo.gillajabi.track.domain.entity.TrackSnapshot;
import com.hubo.gillajabi.track.domain.entity.TrackStatus;
import com.hubo.gillajabi.track.infrastructure.dto.request.TrackSnapshotRequest;
import com.hubo.gillajabi.track.infrastructure.dto.request.TrackStatusUpdateRequest;
import com.hubo.gillajabi.track.infrastructure.exception.TrackException;
import com.hubo.gillajabi.track.infrastructure.exception.TrackExceptionCode;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackRecordRepository;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackSnapshotRepository;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackStatusRepository;
import com.hubo.gillajabi.track.infrastructure.timer.TrackStatusTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrackService {

    private final TrackRecordRepository trackRecordRepository;

    private final MemberRepository memberRepository;

    private final CourseRepository courseRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final TrackSnapshotRepository trackSnapshotRepository;

    private final TrackStatusRepository trackStatusRepository;

    private final TrackStatusTimer trackStatusTimer;

    @Transactional
    public StartTrackResponse startTrack(final TrackStartRequest request, final String username) {
        final Member member = memberRepository.getEntityByUserName(username);
        checkNotTracking(username);
        final Course course = courseRepository.findById(request.getCourseId()).orElse(null);

        final TrackRecord trackRecord = TrackRecord.createByMember(member, course);
        trackRecordRepository.save(trackRecord);

        final TrackStatus trackStatus = TrackStatus.createByMemberAndTrackId(member, trackRecord.getId());
        trackStatusRepository.save(trackStatus);

        eventPublisher.publishEvent(LocationHandlingRequest.of(member, LocationCollectionType.GPS, LocationServiceType.TRACKING));

        trackStatusTimer.startTimerForMember(username);

        return StartTrackResponse.from(trackRecord);
    }

    private void checkNotTracking(String username) {
        trackStatusTimer.isTracking(username);
    }

    public void restartTrack(String username) {
        checkTrackStatus(username);
        trackStatusTimer.restartTimerForMember(username);
    }

    private void checkTrackStatus(String username) {
        if (trackStatusTimer.isTrackActive(username)) {
            throw new TrackException(TrackExceptionCode.ALREADY_TRACKING);
        }
    }

    private void checkNotTrackStatus(String username) {
        if (!trackStatusTimer.isTrackActive(username)) {
            throw new TrackException(TrackExceptionCode.NOT_TRACKING);
        }
    }

    @Transactional
    public void endTrack(final String username) {
        checkNotTrackStatus(username);
        trackStatusTimer.stopTimerForMember(username);
    }

    @Transactional
    public void pauseTrack(String username) {
        checkNotTrackStatus(username);
        trackStatusTimer.pauseTimerForMember(username);
    }

    @Transactional
    public void sendTrack(String username, TrackSendRequest request) {
        final String trackId = TrackStatus.getTrackStatusKeyByMember(username);

        checkNotTrackStatus(username);

        final TrackStatusUpdateRequest trackStatusUpdateRequest = TrackStatusUpdateRequest.from(request);

        final TrackStatus trackStatus = trackStatusRepository.findById(trackId)
                .orElseThrow(() -> new TrackException(TrackExceptionCode.TRACK_NOT_FOUND));

        trackStatus.updateByRequest(trackStatusUpdateRequest);
        trackStatusRepository.save(trackStatus);

        final TrackSnapshotRequest trackSnapshotRequest = TrackSnapshotRequest.of(request, trackStatus.getTrackId());
        final TrackSnapshot trackSnapshot = TrackSnapshot.createByRequest(trackSnapshotRequest);
        trackSnapshotRepository.save(trackSnapshot);

        trackStatusTimer.restartTimerForMember(username);
    }
}