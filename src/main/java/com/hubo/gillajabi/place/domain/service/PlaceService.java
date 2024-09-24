package com.hubo.gillajabi.place.domain.service;

import com.hubo.gillajabi.place.application.dto.response.PointDto;
import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.PlaceDocument;
import com.hubo.gillajabi.place.infrastructure.persistence.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;


    public List<PointDto> getPointByPlace(final PlaceType placeType, final Double latitude, final Double longitude,
                                          final Double radius, final Integer courseId) {
        // type + courseId 로 조회
        if (courseId != null) {
            List<PlaceDocument> placeDocuments = placeRepository.findByTypeAndCourseId(placeType, courseId);

            return PointDto.from(placeDocuments);
        }

        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        List<PlaceDocument> placeDocuments = placeRepository.findByTypeAndLocationNear(placeType, geoJsonPoint, distance);

        return PointDto.from(placeDocuments);
    }
}
