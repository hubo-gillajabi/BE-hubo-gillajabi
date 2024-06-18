package com.hubo.gillajabi.crawl.infrastructure.dto.response;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
public class DuruGpxResponse {
    @JacksonXmlProperty(localName = "metadata")
    private Metadata metadata;

    @JacksonXmlProperty(localName = "trk")
    private Trk trk;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
    public static class Metadata {
        @JacksonXmlProperty(localName = "name")
        private String name;

        @JacksonXmlProperty(localName = "desc")
        private String desc;

        @JacksonXmlProperty(localName = "author")
        private Author author;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
    public static class Author {
        @JacksonXmlProperty(localName = "name")
        private String name;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
    public static class Trk {

        @JacksonXmlProperty(localName = "name")
        private String name;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "trkseg")
        private List<Trkseg> trksegs;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
    public static class Trkseg {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "trkpt")
        private List<Trkpt> trkpts;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 속성 무시
    public static class Trkpt {
        @JacksonXmlProperty(isAttribute = true)
        private double lat;

        @JacksonXmlProperty(isAttribute = true)
        private double lon;

        @JacksonXmlProperty
        private double ele;
    }
}