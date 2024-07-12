package com.hubo.gillajabi.city.application.resolver;

import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.city.domain.service.CityService;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CityResolver {

    private final CityService cityService;

    @UserOnly
    @QueryMapping
    public List<City> allCities(@Argument int offset, @Argument int limit) {
        return cityService.getAllCities(offset, limit);
    }

    @UserOnly
    @QueryMapping
    public City cityDetails(@Argument String cityId) {
        Long id = Long.parseLong(cityId);
        return cityService.getCityDetails(id);
    }
}
