package com.hubo.gillajabi.point.fixture;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.config.FixtureBase;
import com.hubo.gillajabi.course.domain.entity.CourseBookMark;
import com.hubo.gillajabi.course.infrastructure.persistence.CourseBookMarkRepository;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.image.domain.entity.ImageUploadUrl;
import com.hubo.gillajabi.image.infrastructure.presistence.ImageUploadUrlRepository;
import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.constant.RoleStatus;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import com.hubo.gillajabi.point.application.dto.request.UserPointRequest;
import com.hubo.gillajabi.point.domain.entity.UserPoint;
import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import com.hubo.gillajabi.point.domain.service.UserPointSearchService;
import com.hubo.gillajabi.point.domain.service.UserPointService;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointDocumentRepository;
import com.hubo.gillajabi.point.infrastructure.persistence.UserPointRepository;
import com.hubo.gillajabi.track.application.dto.request.TrackStartRequest;
import com.hubo.gillajabi.track.application.dto.response.StartTrackResponse;
import com.hubo.gillajabi.track.domain.entity.PhotoPoint;
import com.hubo.gillajabi.track.domain.entity.TrackRecord;
import com.hubo.gillajabi.track.domain.entity.TrackStatus;
import com.hubo.gillajabi.track.domain.service.TrackService;
import com.hubo.gillajabi.track.infrastructure.persistence.PhotoPointRepository;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackRecordRepository;
import com.hubo.gillajabi.track.infrastructure.persistence.TrackStatusRepository;
import com.hubo.gillajabi.track.infrastructure.timer.TrackStatusTimer;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Track;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.hubo.gillajabi.track.domain.entity.TrackStatus.createByMemberAndTrackId;

@Component
@Profile("test")
public class UserPointFixture extends FixtureBase {

    @Autowired
    private UserPointDocumentRepository userPointDocumentRepository;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private TrackStatusTimer trackStatusTimer;

    @Autowired
    private TrackService trackService;


    public Course createAndLoadCourse() {
        CourseRepository courseRepository = getRepository(CourseRepository.class);
        String randomOriginName = UUID.randomUUID().toString().substring(0, 10);

        City city = createAndLoadCity();

        Course course = Course.builder()
                .originName(randomOriginName)
                .city(city)
                .courseNumber(generateUniqueCourseNumber())
                .shortDescription("shortDescription")
                .build();
        return courseRepository.save(course);
    }

    private String generateUniqueCourseNumber() {
        long timestamp = Instant.now().toEpochMilli();
        String timestampStr = String.valueOf(timestamp);

        return timestampStr.substring(Math.max(0, timestampStr.length() - 10));
    }

    public City createAndLoadCity(){
        CityRepository cityRepository = getRepository(CityRepository.class);

        String randomName = UUID.randomUUID().toString().substring(0, 10);

        City city = City.builder()
                .name(randomName)
                .province(Province.BUSAN)
                .build();
        return cityRepository.save(city);
    }

    public UserPoint createUserPoint(Member member) {
        UserPointRepository userPointRepository = getRepository(UserPointRepository.class);
        PhotoPoint photoPoint = createAndSavePhotoPoint();
        Course course = createAndLoadCourse();

        UserPoint userPoint = UserPoint.builder()
                .member(member)
                .content("content")
                .latitude(BigDecimal.valueOf(37.1234))
                .longitude(BigDecimal.valueOf(127.1234))
                .photoPoint(Arrays.asList(photoPoint))
                .course(course)
                .build();

        return userPointRepository.save(userPoint);
    }

    public UserPoint createUserPoint(Member member, Course course){
        UserPointRepository userPointRepository = getRepository(UserPointRepository.class);
        PhotoPoint photoPoint = createAndSavePhotoPoint();

        UserPoint userPoint = UserPoint.builder()
                .member(member)
                .content("content")
                .latitude(BigDecimal.valueOf(37.1234))
                .longitude(BigDecimal.valueOf(127.1234))
                .photoPoint(Arrays.asList(photoPoint))
                .course(course)
                .build();

        return userPointRepository.save(userPoint);
    }

    private PhotoPoint createAndSavePhotoPoint() {
        PhotoPointRepository photoPointRepository = getRepository(PhotoPointRepository.class);
        PhotoPoint photoPoint = PhotoPoint.builder()
                .latitude(BigDecimal.valueOf(37.1234))
                .longitude(BigDecimal.valueOf(127.1234))
                .photoUrl("photo.com")
                .build();
        return photoPointRepository.save(photoPoint);
    }

    public UserPointDocument createUserPointDocument(Member member) {
        UserPoint userPoint = createUserPoint(member);
        UserPointDocument userPointDocument = UserPointDocument.createByUserPoint(userPoint);
        return userPointDocumentRepository.save(userPointDocument);
    }

    public UserPointDocument createUserPointDocument(Member member, Course course){
        UserPoint userPoint = createUserPoint(member, course);
        UserPointDocument userPointDocument = UserPointDocument.createByUserPoint(userPoint);
        return userPointDocumentRepository.save(userPointDocument);
    }

    public CourseBookMark createCourseBookMark(Member member) {
        Course course = createAndLoadCourse();
        return createCourseBookMark(member, course);
    }

    public CourseBookMark createCourseBookMark(Member member, Course course) {
        CourseBookMarkRepository courseBookMarkRepository = getRepository(CourseBookMarkRepository.class);

        CourseBookMark courseBookMark = CourseBookMark.builder()
                .member(member)
                .course(course)
                .build();

        return courseBookMarkRepository.save(courseBookMark);
    }

    public UserPoint createAndLoadUserPoint(Member member, Course course) {
        UserPointRepository userPointRepository = getRepository(UserPointRepository.class);
        PhotoPoint photoPoint = createAndSavePhotoPoint();

        UserPoint userPoint = UserPoint.builder()
                .member(member)
                .content("content")
                .latitude(BigDecimal.valueOf(37.1234))
                .longitude(BigDecimal.valueOf(127.1234))
                .photoPoint(Arrays.asList(photoPoint))
                .course(course)
                .build();

        return userPointRepository.save(userPoint);
    }

    public ImageUploadUrl createImageUploadUrl(String url){
        ImageUploadUrlRepository imageUploadUrlRepository = getCrudRepository(ImageUploadUrlRepository.class);

        ImageUploadUrl imageUploadUrl = ImageUploadUrl.createByUrl(url);
        return imageUploadUrlRepository.save(imageUploadUrl);
    }

    //startTrack
    public void startTrack(Member member, Course course, ImageUploadUrl imageUploadUrl){
        String userName = member.getNickName();
        TrackRecordRepository trackRecordRepository = getRepository(TrackRecordRepository.class);
        TrackStatusRepository trackStatusRepository = getCrudRepository(TrackStatusRepository.class);

        final TrackRecord trackRecord = TrackRecord.createByMember(member, course);
        trackRecordRepository.save(trackRecord);

        final TrackStatus trackStatus = TrackStatus.createByMemberAndTrackId(member, trackRecord.getId());
        trackStatusRepository.save(trackStatus);

        trackStatusTimer.startTimerForMember(userName);

//        UserPointRequest userPointRequest = new UserPointRequest( "content" ,BigDecimal.valueOf(37.1234), BigDecimal.valueOf(127.1234),  List.of(imageUploadUrl.getImageUploadUrl()));
//        userPointService.saveUserPoint(userName, userPointRequest);
    }

    public UserPointDocument createAndLoadUserPointDocument(Member member) {
        UserPointRepository userPointRepository = getRepository(UserPointRepository.class);
        PhotoPoint photoPoint = createAndSavePhotoPoint();
        Course course = createAndLoadCourse();

        UserPoint userPoint = UserPoint.builder()
                .member(member)
                .content("content")
                .latitude(BigDecimal.valueOf(37.1234))
                .longitude(BigDecimal.valueOf(127.1234))
                .photoPoint(Arrays.asList(photoPoint))
                .course(course)
                .build();

        userPointRepository.save(userPoint);

        UserPointDocument userPointDocument = UserPointDocument.createByUserPoint(userPoint);
        return userPointDocumentRepository.save(userPointDocument);
    }
}