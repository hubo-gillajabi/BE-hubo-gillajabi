package com.hubo.gillajabi.crawl.domain.service.duru;

import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.dto.request.CourseThemeRequestDTO;
import com.hubo.gillajabi.crawl.infrastructure.dto.response.DuruThemeResponse;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseDuruThemeService {

    private final CourseThemeRepository courseThemeRepository;

    public List<CourseTheme> saveDuruTheme(List<DuruThemeResponse.Theme> items) {
        List<CourseTheme> courseThemes = createOrUpdateCourseThemes(items);
        courseThemeRepository.saveAll(courseThemes);

        return courseThemes;
    }

    private List<CourseTheme> createOrUpdateCourseThemes(List<DuruThemeResponse.Theme> items) {
        List<CourseTheme> courseThemes = new ArrayList<>();
        for (DuruThemeResponse.Theme item : items) {
            CourseTheme courseTheme = findOrCreateCourseTheme(item);
            courseThemes.add(courseTheme);
        }
        return courseThemes;
    }

    private CourseTheme findOrCreateCourseTheme(DuruThemeResponse.Theme item) {
        return courseThemeRepository.findByName(item.getThemeNm())
                .map(existingTheme -> updateExistingCourseTheme(existingTheme, item))
                .orElseGet(() -> createNewCourseTheme(item));
    }

    private CourseTheme updateExistingCourseTheme(CourseTheme existingTheme, DuruThemeResponse.Theme item) {
        existingTheme.update(item.getThemedescs(), item.getLinemsg());
        return existingTheme;
    }

    private CourseTheme createNewCourseTheme(DuruThemeResponse.Theme item) {
        CourseThemeRequestDTO requestDTO = CourseThemeRequestDTO.from(item);
        return CourseTheme.createCourseTheme(requestDTO);
    }
}
