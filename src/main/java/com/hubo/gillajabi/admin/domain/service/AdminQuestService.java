package com.hubo.gillajabi.admin.domain.service;

import com.hubo.gillajabi.admin.application.dto.request.MainQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.request.MainQuestUpdateRequest;
import com.hubo.gillajabi.admin.application.dto.request.SubQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.response.MainQuestResponse;
import com.hubo.gillajabi.admin.application.dto.response.SubQuestResponse;
import com.hubo.gillajabi.admin.infrastructure.dto.request.MainQuestRequest;
import com.hubo.gillajabi.admin.infrastructure.dto.request.SubQuestRequest;
import com.hubo.gillajabi.crawl.domain.entity.City;
import com.hubo.gillajabi.crawl.domain.entity.Course;
import com.hubo.gillajabi.crawl.domain.entity.CourseTheme;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CityRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseRepository;
import com.hubo.gillajabi.crawl.infrastructure.persistence.CourseThemeRepository;
import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import com.hubo.gillajabi.quest.infrastructure.persistence.MainQuestRepository;
import com.hubo.gillajabi.quest.infrastructure.persistence.SubQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
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

    //TODO: 에러코드 만들기
    @Transactional
    public MainQuestResponse createMainQuest(MainQuestCreateRequest mainQuestCreateRequest) {
        City city = getEntityIfExists(mainQuestCreateRequest.cityId(), cityRepository, "도시를 찾을 수 없습니다.");
        CourseTheme courseTheme = getEntityIfExists(mainQuestCreateRequest.courseThemeId(), courseThemeRepository, "코스 테마를 찾을 수 없습니다.");
        Course course = getEntityIfExists(mainQuestCreateRequest.courseId(), courseRepository, "코스를 찾을 수 없습니다.");

        MainQuestRequest mainQuestRequest = MainQuestRequest.from(mainQuestCreateRequest, city, courseTheme, course);
        MainQuest mainQuest = MainQuest.createMainQuest(mainQuestRequest);
        mainQuestRepository.save(mainQuest);

        return MainQuestResponse.fromEntity(mainQuest);
    }

    @Transactional
    public SubQuestResponse createSubQuest(SubQuestCreateRequest subQuestCreateRequest) {
        MainQuest mainQuest = getEntityIfExists(subQuestCreateRequest.mainQuestId(), mainQuestRepository, "메인 퀘스트를 찾을 수 없습니다.");

        SubQuestRequest subQuestRequest = SubQuestRequest.from(subQuestCreateRequest, mainQuest);
        SubQuest subQuest = SubQuest.createSubQuest(subQuestRequest);

        subQuestRepository.save(subQuest);

        return SubQuestResponse.fromEntity(subQuest);
    }

    @Transactional
    public void deleteMainQuest(Long questId) {
        MainQuest mainQuest = getEntityIfExists(questId, mainQuestRepository, "메인 퀘스트를 찾을 수 없습니다.");
        mainQuest.changeStatusToDeleted();

        mainQuestRepository.save(mainQuest);
    }

    @Transactional
    public void deleteSubQuest(Long questId, Long subQuestId) {
        //TODO Projection
        MainQuest mainQuest = getEntityIfExists(questId, mainQuestRepository, "메인 퀘스트를 찾을 수 없습니다.");
        SubQuest subQuest = getEntityIfExists(subQuestId, subQuestRepository, "서브 퀘스트를 찾을 수 없습니다.");

        if (!mainQuest.hasSubQuest(subQuest)) {
            throw new IllegalArgumentException("메인 퀘스트에 해당 서브 퀘스트가 없습니다.");
        }

        mainQuest.removeSubQuest(subQuest);
        subQuest.changeStatusToDeleted();

        subQuestRepository.save(subQuest);
    }

    private <T> T getEntityIfExists(Long id, JpaRepository<T, Long> repository, String errorMessage) {
        if (id != null) {
            return repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(errorMessage));
        }
        return null;
    }

    @Transactional
    public MainQuestResponse updateMainQuest(Long questId, MainQuestUpdateRequest mainQuestUpdateRequest) {
        MainQuest mainQuest = getEntityIfExists(questId, mainQuestRepository, "메인 퀘스트를 찾을 수 없습니다.");

        // 대충 로직 설계

        mainQuestRepository.save(mainQuest);

        return MainQuestResponse.fromEntity(mainQuest);
    }
}
