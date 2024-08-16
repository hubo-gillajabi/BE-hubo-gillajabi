package com.hubo.gillajabi.admin.domain.service;

import com.hubo.gillajabi.admin.application.dto.request.MainQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.request.MainQuestUpdateRequest;
import com.hubo.gillajabi.admin.application.dto.request.SubQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.request.SubQuestUpdateRequest;
import com.hubo.gillajabi.admin.application.dto.response.MainQuestResponse;
import com.hubo.gillajabi.admin.application.dto.response.SubQuestResponse;
import com.hubo.gillajabi.admin.infrastructure.dto.request.MainQuestRequest;
import com.hubo.gillajabi.admin.infrastructure.dto.request.SubQuestRequest;
import com.hubo.gillajabi.city.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.city.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import com.hubo.gillajabi.quest.infrastructure.persistence.MainQuestRepository;
import com.hubo.gillajabi.quest.infrastructure.persistence.SubQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminQuestService {

    private final CityRepository cityRepository;
    private final CourseThemeRepository courseThemeRepository;
    private final CourseRepository courseRepository;
    private final MainQuestRepository mainQuestRepository;
    private final SubQuestRepository subQuestRepository;

    @Transactional
    public MainQuestResponse createMainQuest(final MainQuestCreateRequest mainQuestCreateRequest) {
        final City city = cityRepository.getEntityById(mainQuestCreateRequest.cityId());
        final CourseTheme courseTheme = courseThemeRepository.getEntityById(mainQuestCreateRequest.courseThemeId());
        final Course course = courseRepository.getEntityById(mainQuestCreateRequest.courseId());

        final MainQuestRequest mainQuestRequest = MainQuestRequest.of(mainQuestCreateRequest, city, courseTheme, course);
        final MainQuest mainQuest = MainQuest.createMainQuest(mainQuestRequest);
        mainQuestRepository.save(mainQuest);

        return MainQuestResponse.fromEntity(mainQuest);
    }

    @Transactional
    public SubQuestResponse createSubQuest(final SubQuestCreateRequest subQuestCreateRequest) {
        final MainQuest mainQuest = mainQuestRepository.getEntityById(subQuestCreateRequest.mainQuestId());

        final SubQuestRequest subQuestRequest = SubQuestRequest.from(subQuestCreateRequest, mainQuest);
        final SubQuest subQuest = SubQuest.createSubQuest(subQuestRequest);

        subQuestRepository.save(subQuest);

        return SubQuestResponse.fromEntity(subQuest);
    }

    @Transactional
    public void deleteMainQuest(final Long questId) {
        final MainQuest mainQuest = mainQuestRepository.getEntityById(questId);
        mainQuest.changeStatusToDeleted();

        mainQuestRepository.save(mainQuest);
    }

    @Transactional
    public void deleteSubQuest(final Long questId, final Long subQuestId) {
        final MainQuest mainQuest = mainQuestRepository.getEntityById(questId);
        final SubQuest subQuest = subQuestRepository.getEntityById(subQuestId);

        if (mainQuest.hasSubQuest(subQuest)) {
            throw new IllegalArgumentException("메인 퀘스트에 해당 서브 퀘스트가 없습니다.");
        }

        mainQuest.removeSubQuest(subQuest);
        subQuest.changeStatusToDeleted();

        subQuestRepository.save(subQuest);
    }

    @Transactional
    public MainQuestResponse updateMainQuest(final Long questId, final MainQuestUpdateRequest mainQuestUpdateRequest) {
        final City city = cityRepository.getEntityById(mainQuestUpdateRequest.cityId());
        final CourseTheme courseTheme = courseThemeRepository.getEntityById(mainQuestUpdateRequest.courseThemeId());
        final Course course = courseRepository.getEntityById(mainQuestUpdateRequest.courseId());

        final MainQuest mainQuest = mainQuestRepository.getEntityById(questId);
        final MainQuestRequest mainQuestRequest = MainQuestRequest.of(mainQuest, mainQuestUpdateRequest, city, courseTheme, course);

        mainQuest.update(mainQuestRequest);
        mainQuestRepository.save(mainQuest);

        return MainQuestResponse.fromEntity(mainQuest);
    }


    @Transactional
    public SubQuestResponse updateSubQuest(Long questId, Long subQuestId, SubQuestUpdateRequest subQuestUpdateRequest) {
        final MainQuest mainQuest = mainQuestRepository.getEntityById(questId);
        final SubQuest subQuest = subQuestRepository.getEntityById(subQuestId);

        if (mainQuest.hasSubQuest(subQuest)) {
            throw new IllegalArgumentException("메인 퀘스트에 해당 서브 퀘스트가 없습니다.");
        }

        final SubQuestRequest subQuestRequest = SubQuestRequest.of(subQuest, subQuestUpdateRequest, mainQuest);
        subQuest.update(subQuestRequest);
        subQuestRepository.save(subQuest);

        return SubQuestResponse.fromEntity(subQuest);
    }
}
