package com.hubo.gillajabi.admin.application.presenation;

import com.hubo.gillajabi.login.application.annotation.AdminOnly;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping("/login")
    public String loginForm() {
        return "admin/login";
    }

    @GetMapping("/swagger")
    @AdminOnly
    public String swagger(HttpServletRequest request) {
        // 세션에 플래그 설정
        request.getSession().setAttribute("swaggerAccess", true);

        return "redirect:/swagger-ui/index.html";
    }

}