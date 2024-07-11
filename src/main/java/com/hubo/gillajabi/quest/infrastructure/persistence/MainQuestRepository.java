package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.quest.domain.entity.MainQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainQuestRepository extends JpaRepository<MainQuest, Long>{


    default MainQuest getEntityById(Long mainQuestId){
        if(mainQuestId != null){
            return findById(mainQuestId).orElseThrow(() -> new IllegalArgumentException("메인 퀘스트를 찾을 수 없습니다."));
        }
        return null;
    }
}
