package com.hubo.gillajabi.place.domain.entity;


import com.hubo.gillajabi.place.domain.constant.PlaceType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "places")
@CompoundIndexes({
        @CompoundIndex(name = "type_courseId", def = "{'type': 1, 'courseId': 1}", unique = true),
        @CompoundIndex(name = "type_location", def = "{'type': 1, 'location': '2dsphere'}", unique = true)
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Place {

    @Id
    private String id;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] location;  // [경도, 위도] 순서

    private String name;

    private PlaceType type;

    private String description;

    private String imageUrl;

    private String mapUrl;

    private Integer courseId;

}
