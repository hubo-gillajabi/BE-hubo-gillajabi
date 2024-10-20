package com.hubo.gillajabi.crawl.domain.service.duru;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import com.hubo.gillajabi.crawl.domain.service.PrimaryCrawlingService;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseDetailRequest;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiDuruGpxResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.GpxInfoRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoadGpxInfoDuruServiceTest {

    @Mock
    private GpxInfoRepository gpxInfoRepository;

    @Mock
    private PrimaryCrawlingService responseCrawlService;

    @InjectMocks
    private RoadGpxInfoDuruService roadGpxInfoDuruService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    @BeforeEach
    public void setUp() {
        CourseDetailRequest courseDetailRequest = fixtureMonkey.giveMeOne(CourseDetailRequest.class);
        courseDetailRequest.setGpxPath("http://gpxpath.com.kmz");
    }

    public void mockApiResponseService() throws JsonProcessingException {
        // 외부 의존성을 모킹한다
        ApiDuruGpxResponse apiDuruGpxResponse = fixtureMonkey.giveMeBuilder(ApiDuruGpxResponse.class)
                .size("trk.trkseg", 1)     // trkseg 리스트의 크기를 1로 제한
                .size("trk.trkseg[*].trkpt", 2) // trkpt 리스트의 크기를 2로 제한
                .sample();


        // XML로 변환
        XmlMapper xmlMapper = new XmlMapper();
        String xmlResponse = xmlMapper.writeValueAsString(apiDuruGpxResponse);

        // API 응답을 모킹
        when(responseCrawlService.fetchApiResponse(any(URI.class)))
                .thenReturn(xmlResponse);
    }

    @Test
    @DisplayName("List<CourseDetail>를 받아서 새로운 GpxInfo 객체를 생성하고 저장한다.")
    public void testSaveGpxInfoWithValidCourseDetails() throws JsonProcessingException {
        // given
        List<CourseDetailRequest> requestDTOs = new ArrayList<>();

        CourseDetailRequest courseDetailRequest = fixtureMonkey.giveMeOne(CourseDetailRequest.class);
        courseDetailRequest.setGpxPath("http://gpxpath.com");

        requestDTOs.add(courseDetailRequest);

        List<CourseDetail> mockCourseDetails = requestDTOs.stream()
                .map(CourseDetail::createCourseDetail)
                .toList();

        when(gpxInfoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        mockApiResponseService();

        // when
        List<GpxInfo> savedGpxInfos = roadGpxInfoDuruService.saveGpxInfo(mockCourseDetails);

        // then
        assertEquals(mockCourseDetails.size(), savedGpxInfos.size());
    }

    @Test
    @DisplayName("gpxPath가 kmz로 끝나는 경우 패스한다")
    public void gpxPath가_kmz로_끝나는_경우() {
        //given
        List<CourseDetailRequest> requestDTOs = new ArrayList<>();

        CourseDetailRequest courseDetailRequest = fixtureMonkey.giveMeOne(CourseDetailRequest.class);
        courseDetailRequest.setGpxPath("http://gpxpath.com.kmz");

        requestDTOs.add(courseDetailRequest);

        List<CourseDetail> mockCourseDetails = requestDTOs.stream()
                .map(CourseDetail::createCourseDetail)
                .toList();

        //when
        List<GpxInfo> savedGpxInfos = roadGpxInfoDuruService.saveGpxInfo(mockCourseDetails);

        //then
        assertEquals(0, savedGpxInfos.size());
    }
}
