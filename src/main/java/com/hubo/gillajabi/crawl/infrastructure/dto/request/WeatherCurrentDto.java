package com.hubo.gillajabi.crawl.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hubo.gillajabi.crawl.domain.constant.PrecipitationForm;
import com.hubo.gillajabi.crawl.domain.constant.SkyCondition;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiWeatherCurrentResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Slf4j
public class WeatherCurrentDto {

    private Float liveTemperature;

    // 최저 기온
    private Float lowTemperature;

    // 최고 기온
    private Float highTemperature;

    // 날씨
    private SkyCondition skyCondition;

    // 기상 특보 TODO: 수정 (예 : 폭염 경보)
    private String weatherAlert;

    // 습도 reh % 0~100
    private Integer humidity;

    // 강수형태
    private PrecipitationForm precipitationForm;

    // 강수확률
    private Integer precipitationProbability;

    // 강수량 mm
    private Float precipitationAmount;

    // 적설량
    private Float snowfallAmount;

    // 풍속 m/s
    private Float windSpeed;

    public void updateWeatherDtoFromCurrent( ApiWeatherCurrentResponse.Current current) {
        try {
            switch (current.getCategory()) {
                case "POP":
                    this.setPrecipitationProbability(parseSafeInt(current.getFcstValue()));
                    break;
                case "PTY":
                    this.setPrecipitationForm(PrecipitationForm.fromCode(parseSafeInt(current.getFcstValue())));
                    break;
                case "PCP":
                    this.setPrecipitationAmount(parseSafeFloat(current.getFcstValue(), "강수없음"));
                    break;
                case "REH":
                    this.setHumidity(parseSafeInt(current.getFcstValue()));
                    break;
                case "SNO":
                    this.setSnowfallAmount(parseSafeFloat(current.getFcstValue(), "적설없음"));
                    break;
                case "SKY":
                    this.setSkyCondition(SkyCondition.fromCode(parseSafeInt(current.getFcstValue())));
                    break;
                case "TMP":
                    this.setLiveTemperature(Float.parseFloat(current.getFcstValue()));
                    break;
                case "TMN":
                    this.setLowTemperature(Float.parseFloat(current.getFcstValue()));
                    break;
                case "TMX":
                    this.setHighTemperature(Float.parseFloat(current.getFcstValue()));
                    break;
                case "WSD":
                    this.setWindSpeed(Float.parseFloat(current.getFcstValue()));
                    break;
                default: // 처리할 카테고리가 아닐 경우
                    break;
            }
        } catch (NumberFormatException e) {
            log.info("날씨 데이터 파싱 중 오류 발생: " + e.getMessage());
        }
    }

    private float parseSafeFloat(String value, String defaultValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue.equals("강수없음") || defaultValue.equals("적설없음") ? 0.0f : Float.parseFloat(defaultValue);
        }
    }

    private int parseSafeInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}


