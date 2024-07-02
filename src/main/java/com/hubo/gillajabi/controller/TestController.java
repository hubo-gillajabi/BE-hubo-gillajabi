package com.hubo.gillajabi.controller;

import com.hubo.gillajabi.login.application.annotation.MemberOnly;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @MemberOnly
    @GetMapping("/hello")
    public String test() {
        return "world";
    }

}
