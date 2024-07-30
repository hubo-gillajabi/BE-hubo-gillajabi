package com.hubo.gillajabi.weather.application.dto;

import com.hubo.gillajabi.crawl.domain.constant.MediumTermSkyCondition;
import com.hubo.gillajabi.crawl.domain.constant.PrecipitationForm;
import com.hubo.gillajabi.crawl.domain.constant.SkyCondition;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherCurrentDto;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.WeatherTermDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class WeekendWeatherInfoDTO {
    private Float lowTemperature;

    private Float highTemperature;

    private PrecipitationForm precipitationForm;

    private SkyCondition skyCondition;


    public static WeekendWeatherInfoDTO from(WeatherCurrentDto weatherCurrentDto) {
        return new WeekendWeatherInfoDTO(
                weatherCurrentDto.getLowTemperature(),
                weatherCurrentDto.getHighTemperature(),
                weatherCurrentDto.getPrecipitationForm(),
                weatherCurrentDto.getSkyCondition()
        );
    }

    public static List<WeekendWeatherInfoDTO> from(WeatherTermDTO weekendWeather) {
        List<WeekendWeatherInfoDTO> result = new ArrayList<>();

        result.add(convertToWeekendWeatherInfo(
                weekendWeather.getLowTemperature3(),
                weekendWeather.getHighTemperature3(),
                weekendWeather.getSkyCondition3()
        ));
        result.add(convertToWeekendWeatherInfo(
                weekendWeather.getLowTemperature4(),
                weekendWeather.getHighTemperature4(),
                weekendWeather.getSkyCondition4()
        ));
        result.add(convertToWeekendWeatherInfo(
                weekendWeather.getLowTemperature5(),
                weekendWeather.getHighTemperature5(),
                weekendWeather.getSkyCondition5()
        ));
        result.add(convertToWeekendWeatherInfo(
                weekendWeather.getLowTemperature6(),
                weekendWeather.getHighTemperature6(),
                weekendWeather.getSkyCondition6()
        ));
        result.add(convertToWeekendWeatherInfo(
                weekendWeather.getLowTemperature7(),
                weekendWeather.getHighTemperature7(),
                weekendWeather.getSkyCondition7()
        ));

        return result;
    }

    private static WeekendWeatherInfoDTO convertToWeekendWeatherInfo(
            int lowTemperature, int highTemperature, MediumTermSkyCondition mediumTermCondition) {
        PrecipitationForm precipitationForm = convertToPrecipitationForm(mediumTermCondition);
        SkyCondition skyCondition = convertToSkyCondition(mediumTermCondition);
        return new WeekendWeatherInfoDTO((float) lowTemperature, (float) highTemperature, precipitationForm, skyCondition);
    }

    private static PrecipitationForm convertToPrecipitationForm(MediumTermSkyCondition condition) {
        return switch (condition) {
            case CLEAR, MOSTLY_CLOUDY, CLOUDY -> PrecipitationForm.NONE;
            case MOSTLY_CLOUDY_WITH_RAIN, CLOUDY_WITH_RAIN -> PrecipitationForm.RAIN;
            case MOSTLY_CLOUDY_WITH_SNOW, CLOUDY_WITH_SNOW -> PrecipitationForm.SNOW;
            case MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW, CLOUDY_WITH_RAIN_AND_SNOW -> PrecipitationForm.RAIN_AND_SNOW;
            case MOSTLY_CLOUDY_WITH_SHOWERS, CLOUDY_WITH_SHOWERS -> PrecipitationForm.SHOWER;
        };
    }

    private static SkyCondition convertToSkyCondition(MediumTermSkyCondition condition) {
        return switch (condition) {
            case CLEAR -> SkyCondition.CLEAR;
            case MOSTLY_CLOUDY, MOSTLY_CLOUDY_WITH_RAIN, MOSTLY_CLOUDY_WITH_SNOW, MOSTLY_CLOUDY_WITH_RAIN_AND_SNOW, MOSTLY_CLOUDY_WITH_SHOWERS ->
                    SkyCondition.MOSTLY_CLOUDY;
            case CLOUDY, CLOUDY_WITH_RAIN, CLOUDY_WITH_SNOW, CLOUDY_WITH_RAIN_AND_SNOW, CLOUDY_WITH_SHOWERS ->
                    SkyCondition.CLOUDY;
        };
    }
}
