package com.hubo.gillajabi.crawl.domain.entity;

import com.hubo.gillajabi.crawl.domain.constant.Province;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private City(final String name,
                 final Province province,
                 final String description) {
        this.name = name;
        this.province = province;
        this.description = description;
    }

    public static City of(final String name, final Province province, final String description) {
        return new City(name, province, description);
    }

    public static City of(final String name, final Province province) {
        return new City(name, province, null);
    }


}
