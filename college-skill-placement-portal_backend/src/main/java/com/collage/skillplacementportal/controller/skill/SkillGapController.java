package com.collage.skillplacementportal.controller.skill;

import com.collage.skillplacementportal.dto.skill.SkillGapDTO;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import com.collage.skillplacementportal.service.skill.SkillGapService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/skill-gap")
public class SkillGapController {

    private final SkillGapService skillGapService;
    private final AuthorizationService authorizationService;
    public SkillGapController(
            SkillGapService skillGapService,
            AuthorizationService authorizationService) {

        this.skillGapService = skillGapService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/student/{studentId}")
    public List<SkillGapDTO> getSkillGaps(
            @PathVariable Long studentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's skill gap"
            );
        }

        return skillGapService.getSkillGaps(
                studentId
        );
    }
    @GetMapping("/student/{studentId}/weak")
    public List<SkillGapDTO> getWeakSkills(
            @PathVariable Long studentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's weak skills"
            );
        }

        return skillGapService.getWeakSkills(
                studentId
        );
    }
}