package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import lombok.Getter;
import lombok.Setter;

public class ApiWeatherMediumTermResponse {

    @Getter
    @Setter
    public static class Temperature {
        private String regId;
        private int taMin3;
        private int taMin3Low;
        private int taMin3High;
        private int taMax3;
        private int taMax3Low;
        private int taMax3High;
        private int taMin4;
        private int taMin4Low;
        private int taMin4High;
        private int taMax4;
        private int taMax4Low;
        private int taMax4High;
        private int taMin5;
        private int taMin5Low;
        private int taMin5High;
        private int taMax5;
        private int taMax5Low;
        private int taMax5High;
        private int taMin6;
        private int taMin6Low;
        private int taMin6High;
        private int taMax6;
        private int taMax6Low;
        private int taMax6High;
        private int taMin7;
        private int taMin7Low;
        private int taMin7High;
        private int taMax7;
        private int taMax7Low;
        private int taMax7High;
    }

    @Getter
    @Setter
    public static class Detail {
        private String regId;
        // 강수 확률
        private int rnSt3Am;
        private int rnSt3Pm;
        private int rnSt4Am;
        private int rnSt4Pm;
        private int rnSt5Am;
        private int rnSt5Pm;
        private int rnSt6Am;
        private int rnSt6Pm;
        private int rnSt7Am;
        private int rnSt7Pm;
        // 하늘 상태
        private String wf3Am;
        private String wf3Pm;
        private String wf4Am;
        private String wf4Pm;
        private String wf5Am;
        private String wf5Pm;
        private String wf6Am;
        private String wf6Pm;
        private String wf7Am;
        private String wf7Pm;
    }

}
