package com.hubo.gillajabi.controller;

import com.hubo.gillajabi.image.application.annotation.ImageUploader;
import com.hubo.gillajabi.login.application.annotation.MemberOnly;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("/hello")
    public String test() {
        return "world";
    }

    @PostMapping
    @ImageUploader
    public ResponseEntity<String> postCitReview(@RequestBody TestDto1 dto) {
        return ResponseEntity.ok("success");
    }

}
