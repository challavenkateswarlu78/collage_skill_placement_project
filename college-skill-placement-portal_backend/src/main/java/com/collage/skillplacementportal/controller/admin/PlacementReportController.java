package com.collage.skillplacementportal.controller.admin;

import com.collage.skillplacementportal.dto.admin.*;
import com.collage.skillplacementportal.service.admin.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class PlacementReportController {

    private final AdminDashboardService adminDashboardService;

    public PlacementReportController(
            AdminDashboardService adminDashboardService) {

        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/placement")
    public PlacementReportDTO getPlacementReport() {

        return adminDashboardService.getPlacementReport();
    }
    @GetMapping("/department-placement")
    public List<DepartmentPlacementReportDTO> getDepartmentPlacementReport() {

        return adminDashboardService.getDepartmentPlacementReport();
    }
    @GetMapping("/job-applications")
    public List<JobApplicationReportDTO> getJobApplicationReport() {

        return adminDashboardService.getJobApplicationReport();
    }
    @GetMapping("/job-application-status")
    public List<JobApplicationStatusReportDTO> getJobApplicationStatusReport() {

        return adminDashboardService.getJobApplicationStatusReport();
    }
    @GetMapping("/student-placement")
    public List<StudentPlacementReportDTO> getStudentPlacementReport() {

        return adminDashboardService.getStudentPlacementReport();
    }
    @GetMapping("/cgpa-statistics")
    public CGPAStatisticsDTO getCGPAStatistics() {

        return adminDashboardService.getCGPAStatistics();
    }
    @GetMapping("/skill-analytics")
    public List<SkillAnalyticsDTO> getSkillAnalytics() {

        return adminDashboardService.getSkillAnalytics();
    }

}