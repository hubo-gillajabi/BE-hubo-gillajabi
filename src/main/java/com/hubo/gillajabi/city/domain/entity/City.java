package com.hubo.gillajabi.city.domain.entity;

import com.hubo.gillajabi.course.domain.entity.CourseImage;
import com.hubo.gillajabi.crawl.domain.constant.Province;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CityRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "province"})})
@Builder
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

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<CityImage> cityImages = new ArrayList<>();

    public static City createCity(final CityRequest cityRequest) {
        return new City(null, cityRequest.getName(), cityRequest.getProvince(), cityRequest.getDescription(),null
                ,null,null,null,new ArrayList<>());
    }

    public void addImages(List<String> imageUrls) {
        imageUrls.forEach(url -> {
            CityImage cityImage = CityImage.createForCity(url, this);
            this.cityImages.add(cityImage);
        });
    }

    public void removeImage(CityImage cityImage) {
        this.cityImages.remove(cityImage);
    }
}
