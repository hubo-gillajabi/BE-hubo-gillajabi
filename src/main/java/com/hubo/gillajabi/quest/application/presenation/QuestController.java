package com.hubo.gillajabi.quest.application.presenation;

import com.hubo.gillajabi.login.application.annotation.UserOnly;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/quest")
public class QuestController {

    @Operation(summary = "퀘스트 검증 api")
    @UserOnly
    @PostMapping("/validate")
    public void validate(){
        /// how to detail json parsing and validation

    }

}
