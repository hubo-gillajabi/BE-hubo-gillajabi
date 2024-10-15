package com.hubo.gillajabi.city.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "city_image")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CityImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    public static CityImage createForCity(String url, City city) {
        return new CityImage(null, url, city);
    }

    public void removeCity() {
        city.removeImage(this);
        this.city = null;
    }
}
