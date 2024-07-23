package com.hubo.gillajabi.city.domain.entity;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "province"})})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Province province;

    @Column
    private String description;

    @Column
    private Integer nx;

    @Column
    private Integer ny;

    @Column(length = 8)
    private String cityCode;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Course> courses;

    public static City createCity(final CityRequest cityRequest) {
        return new City(null, cityRequest.getName(), cityRequest.getProvince(), cityRequest.getDescription(),null
                ,null,null,null);
    }
}
