package com.hubo.gillajabi.quest.infrastructure.persistence;

import com.hubo.gillajabi.quest.domain.entity.SubQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubQuestRepository extends JpaRepository<SubQuest, Long> {



    default SubQuest getEntityById(Long subQuestId){
        if(subQuestId != null){
            return findById(subQuestId).orElseThrow(() -> new IllegalArgumentException("해당 서브 퀘스트가 존재하지 않습니다."));
        }
        return null;
    }
}
