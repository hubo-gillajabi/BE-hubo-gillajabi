package com.hubo.gillajabi.review.application.presenation;
// TODO 패키지 명 변경
import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.UserOnly;
import com.hubo.gillajabi.login.infrastructure.util.SecurityUtil;
import com.hubo.gillajabi.review.application.dto.request.PostWriteRequest;
import com.hubo.gillajabi.review.application.dto.response.PostResponse;
import com.hubo.gillajabi.review.domain.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "방명록 api", description = "방명록 api")
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Operation(summary = "방명록 작성 api", description = "방명록을 작성합니다.")
    @PostMapping
    @UserOnly
    @ImageUploader
    public ResponseEntity writePost(@Valid @RequestBody final PostWriteRequest request) {
        final String userName = SecurityUtil.getCurrentUsername();
        postService.writePost(userName, request);

        return ResponseEntity.created(null).build();
    }

    @Operation(summary = "방명록 삭제 api", description = "방명록을 삭제합니다.")
    @DeleteMapping("/{postId}")
    @UserOnly
    public ResponseEntity deletePost(
            @PathVariable final Long postId
    ) {
        final String userName = SecurityUtil.getCurrentUsername();
        postService.deletePost(userName, postId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "방명록 보기", description = "방명록을 보여줍니다.")
    @GetMapping("/{postId}")
    public ResponseEntity getPost(
            @PathVariable final Long postId
    ) {
        String username = SecurityUtil.getCurrentUsername();
        PostResponse response = postService.getPost(username, postId);

        return ResponseEntity.ok(response);
    }


}


