package com.collage.skillplacementportal.controller.admin;

import com.collage.skillplacementportal.dto.admin.AdminDashboardDTO;
import com.collage.skillplacementportal.service.admin.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService) {

        this.adminDashboardService =
                adminDashboardService;
    }

    @GetMapping("/dashboard")
    public AdminDashboardDTO getDashboard() {

        return adminDashboardService.getDashboard();
    }
}
