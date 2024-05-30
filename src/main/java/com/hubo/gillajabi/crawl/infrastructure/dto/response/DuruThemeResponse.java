package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.util.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DuruThemeResponse implements ValidatableResponse {

    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {
        private int resultCode;
        private String resultMsg;

        public ResponseStatus getResponseStatus() {
            return ResponseStatus.fromCode(resultCode);
        }
    }

    @Getter
    @Setter
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items {
        private List<Theme> item;
    }

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

    @Override
    public void validate() {
        if (this.getResponse() == null || this.getResponse().getBody() == null) {
            throw new IllegalStateException("유효하지 않은 응답 구조");
        }

        ResponseStatus status = this.getResponse().getHeader().getResponseStatus();
        if (status != ResponseStatus.OK) {
            throw new CrawlException(status.getCode(), status.getDescription());
        }
    }
}

