package com.hubo.gillajabi.admin.application.presenation;

import com.hubo.gillajabi.admin.application.dto.request.MainQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.request.MainQuestUpdateRequest;
import com.hubo.gillajabi.admin.application.dto.request.SubQuestCreateRequest;
import com.hubo.gillajabi.admin.application.dto.response.MainQuestResponse;
import com.hubo.gillajabi.admin.application.dto.response.SubQuestResponse;
import com.hubo.gillajabi.admin.domain.service.AdminQuestService;
import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/quest")
@RequiredArgsConstructor
@Tag(name = "adminQuest", description = "관리자 퀘스트 API")
public class AdminQuestController {

    private final AdminQuestService adminQuestService;

    //TODO : 이미지 타입 추가 (리사이즈)
    @Operation(summary = "메인 퀘스트 생성", description = "메인 퀘스트를 생성합니다.")
    @PostMapping("/main-quest")
    @ImageUploader
    //@AdminOnly
    public ResponseEntity createMainQuest(@RequestBody @Valid MainQuestCreateRequest mainQuestCreateRequest) {
        MainQuestResponse mainQuestResponse = adminQuestService.createMainQuest(mainQuestCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mainQuestResponse);
    }

    @Operation(summary = "서브 퀘스트 생성", description = "서브 퀘스트를 생성합니다.")
    @PostMapping("/sub-quest")
    @ImageUploader
 //   @AdminOnly
    public ResponseEntity createSubQuest(@RequestBody @Valid SubQuestCreateRequest subQuestCreateRequest) {
        SubQuestResponse subQuestResponse = adminQuestService.createSubQuest(subQuestCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(subQuestResponse);
    }

    @Operation(summary = "메인 퀘스트 삭제", description = "퀘스트를 삭제합니다, 하위 서브 퀘스트도 삭제됩니다.")
    @DeleteMapping("/{questId}")
    @AdminOnly
    public ResponseEntity deleteMainQuest(@PathVariable Long questId) {
        adminQuestService.deleteMainQuest(questId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "서브 퀘스트 삭제", description = "서브 퀘스트를 삭제합니다.")
    @DeleteMapping("/{questId}/{subQuestId}")
    @AdminOnly
    public ResponseEntity deleteSubQuest(@PathVariable Long questId, @PathVariable Long subQuestId) {
        adminQuestService.deleteSubQuest(questId, subQuestId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "메인 퀘스트 수정", description = "메인 퀘스트를 수정합니다, 하이 서브 퀘스트도 변경될수 있습니다. 허용된 값 : ENABLE,DISABLE")
    @PatchMapping("/{questId}")
    public ResponseEntity updateMainQuest(@PathVariable Long questId, @RequestBody @Valid MainQuestUpdateRequest mainQuestUpdateRequest) {
        MainQuestResponse mainQuestResponse = adminQuestService.updateMainQuest(questId, mainQuestUpdateRequest);
        return ResponseEntity.ok().body(mainQuestResponse);
    }
}
