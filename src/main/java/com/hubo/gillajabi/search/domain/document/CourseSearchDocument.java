package com.hubo.gillajabi.search.domain.document;

import com.hubo.gillajabi.crawl.domain.constant.PrecipitationForm;
import com.hubo.gillajabi.crawl.domain.constant.SkyCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

@Getter
@Setter
@Setting(settingPath = "elasticsearch-settings.json")
@Document(indexName = "course_search")
public class CourseSearchDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private List<String> images;

    @Field(type = FieldType.Nested)
    private City city;

    @Field(type = FieldType.Nested)
    private List<Theme> themes;  // '부산' 으로 검색한 경우 '갈맷길, 남파랑길 등' 코스들이 배열로  나온다.

    @Field(type = FieldType.Nested)
    private Theme theme; // '갈맷길' 로 검색한경우 부산-갈맷길, 서울-갈맷길 등 해당되는 도시와 조립되어 하나로 나온다.

    @Field(type = FieldType.Nested)
    private WeatherInfo weather;

    @Builder
    @Getter
    public static class WeatherInfo {
        @Field(type = FieldType.Float)
        private Float lowestTemperature;  // 최저 기온

        @Field(type = FieldType.Float)
        private Float highestTemperature;  // 최고 기온

        @Field(type = FieldType.Keyword)
        private PrecipitationForm precipitationForm;

        @Field(type = FieldType.Keyword)
        private final SkyCondition skyCondition;
    }

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "my_nori_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword),
                    @InnerField(suffix = "prefix", type = FieldType.Text, analyzer = "my_nori_analyzer",
                            indexPrefixes = @IndexPrefixes(minChars = 1, maxChars = 10))
            }
    )
    private List<String> tags;

    @Builder
    @Getter
    public static class City {
        @Field(type = FieldType.Keyword)
        private Long id;

        @MultiField(
                mainField = @Field(type = FieldType.Text, analyzer = "my_nori_analyzer"),
                otherFields = {
                        @InnerField(suffix = "keyword", type = FieldType.Keyword),
                        @InnerField(suffix = "prefix", type = FieldType.Text, analyzer = "my_nori_analyzer",
                                indexPrefixes = @IndexPrefixes(minChars = 1, maxChars = 10))
                }
        )
        private String name;

        @Field(type = FieldType.Keyword)
        private String province;
    }

    @Builder
    @Getter
    public static class Theme {
        @Field(type = FieldType.Keyword)
        private Long id;

        @MultiField(
                mainField = @Field(type = FieldType.Text, analyzer = "my_nori_analyzer"),
                otherFields = {
                        @InnerField(suffix = "keyword", type = FieldType.Keyword),
                        @InnerField(suffix = "prefix", type = FieldType.Text, analyzer = "my_nori_analyzer",
                                indexPrefixes = @IndexPrefixes(minChars = 1, maxChars = 10))
                }
        )
        private String name;

        @Field(type = FieldType.Text)
        private String shortDescription;
    }


    // 부산 (이름)
    // 이미지 리스트
    // list : 코스 테마 { 설명 : }
    // ex ) 갈맷길 - 바다를 따라 걷는 코스
    // ex ) 남파랑길 - 역사를 따라 걷는 코스
    // 코스 태그 리스트
    // 지역 날씨
    // 유저가 city id를 알수 있어야함

    // 코스 (부산 - 갈맷길 )
    // 이미지 리스트
    // 코스 테마 설명
    // 코스 태그 리스트
    // 지역 날씨
    // 유저가 부산-갈맷길 을 알수 있어야함

}