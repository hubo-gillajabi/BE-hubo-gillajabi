package com.hubo.gillajabi.crawl.infrastructure.dto.response;

import com.hubo.gillajabi.crawl.infrastructure.exception.CrawlException;
import com.hubo.gillajabi.crawl.infrastructure.util.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AbstractDuruResponse<T> implements ValidatableResponse{

    private Response<T> response;

    @Getter
    @Setter
    public static class Response<T> {
        private Header header;
        private Body<T> body;
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
    public static class Body<T> {
        private Items<T> items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items<T> {
        private List<T> item;
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
