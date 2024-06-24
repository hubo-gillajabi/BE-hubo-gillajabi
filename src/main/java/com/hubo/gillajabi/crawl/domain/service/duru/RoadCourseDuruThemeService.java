package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.ApiThemeResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoadCourseDuruThemeService {

    private final CourseThemeRepository courseThemeRepository;

    public List<CourseTheme> saveDuruTheme(List<ApiThemeResponse.Theme> items) {
        List<CourseTheme> courseThemes = createOrUpdateCourseThemes(items);
        courseThemeRepository.saveAll(courseThemes);

        return courseThemes;
    }

    private List<CourseTheme> createOrUpdateCourseThemes(List<ApiThemeResponse.Theme> items) {
        List<CourseTheme> courseThemes = new ArrayList<>();
        for (ApiThemeResponse.Theme item : items) {
            CourseTheme courseTheme = findOrCreateCourseTheme(item);
            courseThemes.add(courseTheme);
        }
        return courseThemes;
    }

    private CourseTheme findOrCreateCourseTheme(ApiThemeResponse.Theme item) {
        return courseThemeRepository.findByName(item.getThemeNm())
                .map(existingTheme -> updateExistingCourseTheme(existingTheme, item))
                .orElseGet(() -> createNewCourseTheme(item));
    }

    private CourseTheme updateExistingCourseTheme(CourseTheme existingTheme, ApiThemeResponse.Theme item) {
        existingTheme.update(item.getThemedescs(), item.getLinemsg());
        return existingTheme;
    }

    private CourseTheme createNewCourseTheme(ApiThemeResponse.Theme item) {
        CourseThemeRequestDTO requestDTO = CourseThemeRequestDTO.from(item);
        return CourseTheme.createCourseTheme(requestDTO);
    }
}
