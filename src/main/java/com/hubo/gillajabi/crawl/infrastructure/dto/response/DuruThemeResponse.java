package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuruThemeResponse extends AbstractDuruResponse<DuruThemeResponse.Theme> {

    @Getter
    @Setter
    public static class Theme {
        private String routeIdx;
        private String themeNm;
        private String linemsg;
        private String themedescs;
        private String brdDiv;
        private String createdtime;
        private String modifiedtime;
    }
}
