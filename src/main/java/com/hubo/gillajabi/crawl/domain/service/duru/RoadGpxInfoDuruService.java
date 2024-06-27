package com.hubo.gillajabi.crawl.domain.service.duru;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.hubo.gillajabi.crawl.domain.entity.CourseDetail;
import com.hubo.gillajabi.crawl.domain.entity.GpxInfo;
import com.hubo.gillajabi.crawl.domain.service.ResponseCrawlService;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiDuruGpxResponse;
import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.persistence.GpxInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoadGpxInfoDuruService {

    private final GpxInfoRepository gpxInfoRepository;
    private final ResponseCrawlService responseCrawlService;
    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public List<GpxInfo> saveGpxInfo(List<CourseDetail> courseDetails) {
        List<GpxInfo> gpxInfos = new ArrayList<>();
        courseDetails.forEach(courseDetail -> {
            try {
                GpxInfo gpxInfo = processCourseDetailAndSave(courseDetail);
                if (gpxInfo != null) {
                    gpxInfos.add(gpxInfo);
                }
            } catch (Exception e) {
                throw new CrawlException(500, "공공데이터 DURU 호출 중 예기치 못한 오류 발생 : {} " +  e.getMessage());
            }
        });
        return gpxInfos;
    }

    private GpxInfo processCourseDetailAndSave(CourseDetail courseDetail) throws IOException {
        //TODO: kmz 추가
        if (shouldSkip(courseDetail.getGpxPath())) {
            log.info("지원 하지 않는 타입: {}", courseDetail.getGpxPath());
            return null;
        }

        ApiDuruGpxResponse gpxResponse = fetchGpxResponse(courseDetail.getGpxPath());
        String jsonGpx = convertToJson(gpxResponse);
        return saveGpxInfo(jsonGpx, courseDetail);
    }

    private boolean shouldSkip(String path) {
        return path.endsWith(".kmz");
    }

    private ApiDuruGpxResponse fetchGpxResponse(String gpxUrl) throws IOException {
        URI uri = URI.create(gpxUrl);
        String response = responseCrawlService.fetchApiResponse(uri);
        return xmlMapper.readValue(response, ApiDuruGpxResponse.class);
    }

    private String convertToJson(ApiDuruGpxResponse gpxResponse) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(gpxResponse);
    }

    private GpxInfo saveGpxInfo(String jsonGpx, CourseDetail courseDetail) {
        GpxInfo gpxInfo = GpxInfo.of(jsonGpx, courseDetail);
        return gpxInfoRepository.save(gpxInfo);
    }
}
