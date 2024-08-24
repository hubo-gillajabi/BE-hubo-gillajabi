package com.hubo.gillajabi.place.domain.service;

import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.place.application.dto.response.PointDto;
import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.Place;
import com.hubo.gillajabi.place.infrastructure.persistence.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;


    public List<PointDto> getPointByPlace(PlaceType placeType, Float latitude, Float longitude, Float radius, Integer courseId) {
         // type + courseId 로 조회
        if(courseId != null){
            List<Place> places = placeRepository.findByTypeAndCourseId(placeType, courseId);

            return PointDto.from(places);
        }

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        List<Place> places = placeRepository.findByTypeAndLocationNear(placeType, point, distance);

        return PointDto.from(places);
    }
}
