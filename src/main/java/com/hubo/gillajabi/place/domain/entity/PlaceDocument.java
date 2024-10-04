package com.hubo.gillajabi.place.domain.entity;


import com.hubo.gillajabi.place.domain.constant.PlaceType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "places")
@CompoundIndexes({
        @CompoundIndex(name = "places_courseId", def = "{'type': 1, 'courseId': 1}"),
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PlaceDocument {

    @Id
    private String id;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "places_location_2dsphere")
    private GeoJsonPoint location;

    private String name;

    private PlaceType type;

    private String description;

    private String imageUrl;

    private String mapUrl;

    private Integer courseId;

}
