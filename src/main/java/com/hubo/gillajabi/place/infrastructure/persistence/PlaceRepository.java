package com.hubo.gillajabi.place.infrastructure.persistence;

import com.hubo.gillajabi.place.domain.constant.PlaceType;
import com.hubo.gillajabi.place.domain.entity.PlaceDocument;
import org.springframework.data.geo.Distance;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlaceRepository extends MongoRepository<PlaceDocument, String>{

    List<PlaceDocument> findByTypeAndCourseId(PlaceType type, Integer courseId);

    List<PlaceDocument> findByTypeAndLocationNear(PlaceType type, GeoJsonPoint location, Distance distance);
}



//    @Query("{'type': ?0, 'location': {$near: {$geometry: {type: 'Point', coordinates: [?1, ?2]}, $maxDistance: ?3}}}")
//    List<Place> findByTypeAndLocationNear(PlaceType type, double longitude, double latitude, double maxDistanceMeters);