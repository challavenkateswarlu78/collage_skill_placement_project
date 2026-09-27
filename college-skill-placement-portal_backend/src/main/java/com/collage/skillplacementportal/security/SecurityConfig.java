package com.collage.skillplacementportal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                // Disable CSRF
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                // JWT is stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // =====================================================
                        // 1. AUTHENTICATION
                        // =====================================================

                        // Login / registration - public
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // =====================================================
                        // 2. STUDENT PROFILE / DASHBOARD
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/student/me"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/student/dashboard"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 3. STUDENT SKILLS
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/students/*/skills"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/students/*/skills"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/students/student/*/skill/*/assessment"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/students/skills/**"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 4. SKILL GAP
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/skill-gap/student/**"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 5. RECOMMENDATIONS
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/recommendations/student/**"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 6. JOB APPLICATIONS - STUDENT
                        // =====================================================

                        // Student applies for job
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/job-applications/**"
                        ).hasRole("STUDENT")

                        // Student withdraws application
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/job-applications/**"
                        ).hasRole("STUDENT")

                        // Student views own applications
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/job-applications/student/**"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 7. JOB APPLICATIONS - ADMIN
                        // =====================================================

                        // Admin updates application status
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/job-applications/**"
                        ).hasRole("ADMIN")

                        // Admin views applications for a job
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/job-applications/job/**"
                        ).hasRole("ADMIN")


                        // =====================================================
                        // 8. JOB APPLICATIONS - STUDENT + ADMIN
                        // =====================================================

                        // Both can view application history
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/job-applications/*/history"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )

                        // Both can view individual application
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/job-applications/*"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 9. DSA PROBLEMS
                        // =====================================================

                        // Admin creates DSA problem
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/dsa/problems/**"
                        ).hasRole("ADMIN")

                        // Admin updates DSA problem
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/dsa/problems/**"
                        ).hasRole("ADMIN")

                        // Admin deletes DSA problem
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/dsa/problems/**"
                        ).hasRole("ADMIN")

                        // Student + Admin can view DSA problems
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/dsa/problems/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 10. SKILL TOPIC MAPPINGS
                        // =====================================================

                        // Admin creates mapping
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/skill-topic-mappings/**"
                        ).hasRole("ADMIN")

                        // Admin deletes mapping
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/skill-topic-mappings/**"
                        ).hasRole("ADMIN")

                        // Student + Admin can view mappings
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/skill-topic-mappings/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 11. DSA PROGRESS
                        // =====================================================

                        // Student marks problem as solved
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/dsa/progress/solve"
                        ).hasRole("STUDENT")

                        // Student + Admin leaderboard
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/dsa/progress/leaderboard"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )

                        // Admin views problem progress
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/dsa/progress/problem/**"
                        ).hasRole("ADMIN")

                        // Student views own progress
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/dsa/progress/student/**"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 12. ASSESSMENTS
                        // =====================================================

                        // Admin creates assessment
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/assessments/**"
                        ).hasRole("ADMIN")

                        // Admin updates assessment
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/assessments/**"
                        ).hasRole("ADMIN")

                        // Admin deletes assessment
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/assessments/**"
                        ).hasRole("ADMIN")

                        // Student + Admin view assessments
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assessments/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 13. QUESTIONS
                        // =====================================================

                        // Admin creates question
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/questions/**"
                        ).hasRole("ADMIN")

                        // Admin deletes question
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/questions/**"
                        ).hasRole("ADMIN")

                        // Student + Admin view questions
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/questions/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 14. MCQ QUESTIONS
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/mcq-questions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/mcq-questions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/mcq-questions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/mcq-questions/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 15. CODING QUESTIONS
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/coding-questions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/coding-questions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/coding-questions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/coding-questions/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 16. STUDENT QUESTIONS
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/student/questions/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )


                        // =====================================================
                        // 17. ASSESSMENT ATTEMPTS
                        // =====================================================

                        // Student starts assessment
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/assessment-attempts/start"
                        ).hasRole("STUDENT")

                        // Student submits assessment
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/assessment-attempts/*/submit"
                        ).hasRole("STUDENT")

                        // Student views own attempts
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assessment-attempts/student/**"
                        ).hasRole("STUDENT")

                        // Admin views attempts for an assessment
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assessment-attempts/assessment/**"
                        ).hasRole("ADMIN")

                        // Student + Admin view one attempt
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assessment-attempts/*"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )

                        // Student views result
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assessment-attempts/*/result"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 18. STUDENT ANSWERS
                        // =====================================================

                        // Student submits MCQ answer
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/student-answers"
                        ).hasRole("STUDENT")

                        // Student completes coding question
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/student-answers/coding/complete"
                        ).hasRole("STUDENT")

                        // Student views answers for attempt
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/student-answers/attempt/**"
                        ).hasRole("STUDENT")

                        // Student views individual answer
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/student-answers/*"
                        ).hasRole("STUDENT")


                        // =====================================================
                        // 19. ADMIN DASHBOARD
                        // =====================================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/admin/dashboard",
                                "/api/admin/reports/placement",
                                "/api/admin/reports/department-placement",
                                "/api/admin/reports/job-applications",
                                "/api/admin/reports/job-application-status",
                                "/api/admin/reports/student-placement",
                                "/api/admin/reports/cgpa-statistics",
                                "/api/admin/reports/skill-analytics"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/notifications/user/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/notifications/**"
                        ).hasAnyRole(
                                "STUDENT",
                                "ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/notifications/admin"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/files/upload"
                        ).hasAnyRole("STUDENT", "ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/files/user/**"
                        ).hasAnyRole("STUDENT", "ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/files/download/**"
                        ).hasAnyRole("STUDENT", "ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/admin/search/**"
                        ).hasRole("ADMIN")

                        // =====================================================
                        // 20. EVERYTHING ELSE
                        // =====================================================

                        .anyRequest().authenticated()
                )

                // JWT filter runs before Spring's username/password filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // BCrypt password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}