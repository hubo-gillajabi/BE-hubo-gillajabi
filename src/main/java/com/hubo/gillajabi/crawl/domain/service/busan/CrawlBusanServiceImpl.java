package com.hubo.gillajabi.crawl.domain.service.busan;
;
import com.hubo.gillajabi.crawl.domain.service.AbstarctCrawlService;
import com.hubo.gillajabi.crawl.infrastructure.config.RoadProperties;
import com.hubo.gillajabi.crawl.infrastructure.config.ApiProperties;
import com.hubo.gillajabi.crawl.domain.constant.CityName;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlBusanServiceImpl extends AbstarctCrawlService {

    private final RoadProperties roadProperties;

    private ApiProperties busanApiProperties;


    @PostConstruct
    private void init() {
        this.busanApiProperties = roadProperties.getEndpoint(CityName.BUSAN);
        if (this.busanApiProperties == null) {
            throw new IllegalStateException("BUSAN Crawl Strategy 가 초기화 되지 않았습니다.");
        }
    }

    @Override
    public List<String> crawlCourse() {
        return List.of("Busan Course 1", "Busan Course 2", "Busan Course 3");
    }

    @Override
    public List<String> crawlTheme(){
        return List.of("Busan Theme 1", "Busan Theme 2", "Busan Theme 3");
    }
}
