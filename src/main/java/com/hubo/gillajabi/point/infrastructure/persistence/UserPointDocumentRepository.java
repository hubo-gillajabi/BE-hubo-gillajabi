package com.hubo.gillajabi.point.infrastructure.persistence;

import com.hubo.gillajabi.point.domain.entity.UserPointDocument;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


public interface UserPointDocumentRepository extends MongoRepository<UserPointDocument, String> {

    @Query("{'_id': {'$gt': ?0}}")
    Slice<UserPointDocument> findAllByIdAfter(String cursor, Pageable pageable);

    default Slice<UserPointDocument> findAllWithCursor(String cursor, Pageable pageable) {
        return cursor == null ? findAll(pageable) : findAllByIdAfter(cursor, pageable);
    }

    @Query("{'courseId': {'$in': ?0}, '_id': {'$gt': ?1}}")
    Slice<UserPointDocument> findByCourseIdInAndIdAfter(List<Long> bookmarkedCourseIds, String cursor, Pageable pageable);

    default Slice<UserPointDocument> findByCourseIdInWithCursor(List<Long> bookmarkedCourseIds, String cursor, Pageable pageable) {
        return cursor == null ? findByCourseIdIn(bookmarkedCourseIds, pageable) :
                findByCourseIdInAndIdAfter(bookmarkedCourseIds, cursor, pageable);
    }

    @Query("{'courseId': {'$in': ?0}}")
    Slice<UserPointDocument> findByCourseIdIn(List<Long> bookmarkedCourseIds, Pageable pageable);

    @Query("{'userPointId': ?0}")
    UserPointDocument findByUserPointId(Long id);

    List<UserPointDocument> findByCourseIdAndLocationNear(Long courseId, GeoJsonPoint location, Distance distance);

    List<UserPointDocument> findByLocationNear(GeoJsonPoint location, Distance distance);
}