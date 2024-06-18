package com.hubo.gillajabi.crawl.domain.service.duru;


import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CourseThemeServiceTest {

    @Mock
    private CourseThemeRepository courseThemeRepository;

    @InjectMocks
    private CourseDuruThemeService courseDuruThemeService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    @Test
    @DisplayName("테마를 저장한다")
    public void 테마를_저장한다() {
        // given
        List<DuruThemeResponse.Theme> items = new ArrayList<>();
        items.add(fixtureMonkey.giveMeOne(DuruThemeResponse.Theme.class));

        // when
        List<CourseTheme> courseThemes = courseDuruThemeService.saveDuruTheme(items);

        // then
        assertEquals(1, courseThemes.size());

    }


}
