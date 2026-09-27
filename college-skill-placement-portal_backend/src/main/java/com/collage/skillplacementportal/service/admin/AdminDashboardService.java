package com.collage.skillplacementportal.service.admin;

import com.collage.skillplacementportal.dto.admin.*;
import com.collage.skillplacementportal.entity.job.ApplicationStatus;
import com.collage.skillplacementportal.entity.job.Job;
import com.collage.skillplacementportal.entity.job.JobApplication;
import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.repository.job.JobApplicationRepository;
import com.collage.skillplacementportal.repository.job.JobRepository;
import com.collage.skillplacementportal.repository.skill.StudentSkillRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminDashboardService {

    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final StudentSkillRepository studentSkillRepository;
    public AdminDashboardService(
            StudentRepository studentRepository,
            JobRepository jobRepository,
            JobApplicationRepository jobApplicationRepository, StudentSkillRepository studentSkillRepository) {

        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    public AdminDashboardDTO getDashboard() {

        long totalStudents =
                studentRepository.count();

        long totalJobs =
                jobRepository.count();
        long activeJobs = jobRepository.findByActiveTrue().size();

        long totalApplications =
                jobApplicationRepository.count();

        Set<Long> selectedStudentIds =
                new HashSet<>();

        List<Job> jobs =
                jobRepository.findAll();

        for (Job job : jobs) {

            List<JobApplication> selectedApplications =
                    jobApplicationRepository
                            .findByJobIdAndStatus(
                                    job.getId(),
                                    ApplicationStatus.SELECTED
                            );

            for (JobApplication application :
                    selectedApplications) {

                if (application.getStudent() != null) {

                    selectedStudentIds.add(
                            application.getStudent().getId()
                    );
                }
            }
        }

        long selectedStudents =
                selectedStudentIds.size();

        double placementPercentage = 0.0;

        if (totalStudents > 0) {

            placementPercentage =
                    ((double) selectedStudents
                            / totalStudents) * 100;
        }

        List<DepartmentPlacementDTO> departmentStatistics =
                getDepartmentStatistics();

        List<TopStudentDTO> topStudents =
                getTopStudents();

        ApplicationStatusStatisticsDTO applicationStatusStatistics =
                getApplicationStatusStatistics();

        return new AdminDashboardDTO(
                totalStudents,
                totalJobs,
                activeJobs,
                totalApplications,
                selectedStudents,
                placementPercentage,
                departmentStatistics,
                topStudents,
                applicationStatusStatistics);
    }
    public List<DepartmentPlacementDTO> getDepartmentStatistics() {

        List<Student> students =
                studentRepository.findAll();

        List<JobApplication> applications =
                jobApplicationRepository.findAll();

        Map<String, Long> totalStudentsByDepartment =
                new HashMap<>();

        Map<String, Set<Long>> selectedStudentsByDepartment =
                new HashMap<>();

        for (Student student
                : students) {

            String department = student.getDepartment();

            if (department == null || department.isBlank()) {
                department = "UNKNOWN";
            }

            totalStudentsByDepartment.put(
                    department,
                    totalStudentsByDepartment.getOrDefault(
                            department, 0L
                    ) + 1
            );
        }

        for (JobApplication application : applications) {

            if (application.getStatus()
                    == ApplicationStatus.SELECTED
                    && application.getStudent() != null) {

                String department =
                        application.getStudent().getDepartment();

                if (department == null || department.isBlank()) {
                    department = "UNKNOWN";
                }

                selectedStudentsByDepartment
                        .computeIfAbsent(
                                department,
                                key -> new HashSet<>()
                        )
                        .add(application.getStudent().getId());
            }
        }

        List<DepartmentPlacementDTO> result =
                new ArrayList<>();

        for (String department :
                totalStudentsByDepartment.keySet()) {

            long totalStudents =
                    totalStudentsByDepartment.get(department);

            long selectedStudents =
                    selectedStudentsByDepartment
                            .getOrDefault(
                                    department,
                                    new HashSet<>()
                            )
                            .size();

            double placementPercentage = 0.0;

            if (totalStudents > 0) {

                placementPercentage =
                        ((double) selectedStudents
                                / totalStudents) * 100;
            }

            result.add(
                    new DepartmentPlacementDTO(
                            department,
                            totalStudents,
                            selectedStudents,
                            placementPercentage
                    )
            );
        }

        return result;
    }
    public List<TopStudentDTO> getTopStudents() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .filter(student -> student.getCgpa() != null)
                .sorted(Comparator.comparing(
                        Student::getCgpa,
                        Comparator.reverseOrder()
                ))
                .limit(5)
                .map(student -> new TopStudentDTO(
                        student.getId(),
                        student.getName(),
                        student.getDepartment(),
                        student.getCgpa()
                ))
                .toList();
    }
    public ApplicationStatusStatisticsDTO getApplicationStatusStatistics() {

        List<JobApplication> applications =
                jobApplicationRepository.findAll();

        long applied = 0;
        long shortlisted = 0;
        long interview = 0;
        long selected = 0;
        long rejected = 0;

        for (JobApplication application : applications) {

            ApplicationStatus status = application.getStatus();

            if (status == null) {
                continue;
            }

            switch (status) {
                case APPLIED:
                    applied++;
                    break;

                case SHORTLISTED:
                    shortlisted++;
                    break;

                case INTERVIEW:
                    interview++;
                    break;

                case SELECTED:
                    selected++;
                    break;

                case REJECTED:
                    rejected++;
                    break;
            }
        }

        return new ApplicationStatusStatisticsDTO(
                applied,
                shortlisted,
                interview,
                selected,
                rejected
        );
    }
    public PlacementReportDTO getPlacementReport() {

        long totalStudents = studentRepository.count();

        Set<Long> placedStudentIds = new HashSet<>();

        List<JobApplication> applications =
                jobApplicationRepository.findAll();

        for (JobApplication application : applications) {

            if (application.getStatus() == ApplicationStatus.SELECTED) {

                if (application.getStudent() != null
                        && application.getStudent().getId() != null) {

                    placedStudentIds.add(
                            application.getStudent().getId()
                    );
                }
            }
        }

        long placedStudents = placedStudentIds.size();

        long unplacedStudents =
                Math.max(0, totalStudents - placedStudents);

        double placementPercentage = 0.0;

        if (totalStudents > 0) {
            placementPercentage =
                    ((double) placedStudents / totalStudents) * 100;
        }

        return new PlacementReportDTO(
                totalStudents,
                placedStudents,
                unplacedStudents,
                placementPercentage
        );
    }
    public List<DepartmentPlacementReportDTO> getDepartmentPlacementReport() {

        List<Student> students = studentRepository.findAll();
        List<JobApplication> applications =
                jobApplicationRepository.findAll();

        Map<String, Long> totalStudentsByDepartment =
                new HashMap<>();

        Map<String, Set<Long>> placedStudentsByDepartment =
                new HashMap<>();

        for (Student student : students) {

            String department = student.getDepartment();

            if (department == null || department.isBlank()) {
                department = "UNKNOWN";
            }

            totalStudentsByDepartment.put(
                    department,
                    totalStudentsByDepartment.getOrDefault(department, 0L) + 1
            );
        }

        for (JobApplication application : applications) {

            if (application.getStatus() != ApplicationStatus.SELECTED) {
                continue;
            }

            if (application.getStudent() == null) {
                continue;
            }

            Student student = application.getStudent();

            String department = student.getDepartment();

            if (department == null || department.isBlank()) {
                department = "UNKNOWN";
            }

            placedStudentsByDepartment
                    .computeIfAbsent(
                            department,
                            key -> new HashSet<>()
                    )
                    .add(student.getId());
        }

        List<DepartmentPlacementReportDTO> report =
                new ArrayList<>();

        for (Map.Entry<String, Long> entry :
                totalStudentsByDepartment.entrySet()) {

            String department = entry.getKey();
            long totalStudents = entry.getValue();

            long placedStudents =
                    placedStudentsByDepartment
                            .getOrDefault(
                                    department,
                                    new HashSet<>()
                            )
                            .size();

            long unplacedStudents =
                    Math.max(0, totalStudents - placedStudents);

            double placementPercentage = 0.0;

            if (totalStudents > 0) {
                placementPercentage =
                        ((double) placedStudents / totalStudents) * 100;
            }

            report.add(
                    new DepartmentPlacementReportDTO(
                            department,
                            totalStudents,
                            placedStudents,
                            unplacedStudents,
                            placementPercentage
                    )
            );
        }

        return report;
    }
    public List<JobApplicationReportDTO> getJobApplicationReport() {

        List<Job> jobs = jobRepository.findAll();
        List<JobApplicationReportDTO> report = new ArrayList<>();

        for (Job job : jobs) {

            List<JobApplication> applications =
                    jobApplicationRepository.findByJobId(job.getId());

            long totalApplications = applications.size();

            long selectedApplications = 0;

            for (JobApplication application : applications) {

                if (application.getStatus() == ApplicationStatus.SELECTED) {
                    selectedApplications++;
                }
            }

            report.add(
                    new JobApplicationReportDTO(
                            job.getId(),
                            job.getTitle(),
                            job.getCompany(),
                            totalApplications,
                            selectedApplications
                    )
            );
        }
        report.sort(
                Comparator.comparing(
                        JobApplicationReportDTO::getTotalApplications
                ).reversed()
        );
        return report;
    }
    public List<JobApplicationStatusReportDTO> getJobApplicationStatusReport() {

        List<Job> jobs = jobRepository.findAll();

        List<JobApplicationStatusReportDTO> report =
                new ArrayList<>();

        for (Job job : jobs) {

            List<JobApplication> applications =
                    jobApplicationRepository.findByJobId(job.getId());

            long applied = 0;
            long shortlisted = 0;
            long interview = 0;
            long selected = 0;
            long rejected = 0;

            for (JobApplication application : applications) {

                ApplicationStatus status = application.getStatus();

                if (status == null) {
                    continue;
                }

                switch (status) {

                    case APPLIED:
                        applied++;
                        break;

                    case SHORTLISTED:
                        shortlisted++;
                        break;

                    case INTERVIEW:
                        interview++;
                        break;

                    case SELECTED:
                        selected++;
                        break;

                    case REJECTED:
                        rejected++;
                        break;
                }
            }

            report.add(
                    new JobApplicationStatusReportDTO(
                            job.getId(),
                            job.getTitle(),
                            job.getCompany(),
                            applied,
                            shortlisted,
                            interview,
                            selected,
                            rejected
                    )
            );
        }

        return report;
    }
    public List<StudentPlacementReportDTO> getStudentPlacementReport() {

        List<Student> students = studentRepository.findAll();

        List<JobApplication> applications =
                jobApplicationRepository.findAll();

        Set<Long> placedStudentIds = new HashSet<>();

        for (JobApplication application : applications) {

            if (application.getStatus() == ApplicationStatus.SELECTED
                    && application.getStudent() != null
                    && application.getStudent().getId() != null) {

                placedStudentIds.add(
                        application.getStudent().getId()
                );
            }
        }

        List<StudentPlacementReportDTO> report =
                new ArrayList<>();

        for (Student student : students) {

            boolean placed =
                    placedStudentIds.contains(student.getId());

            report.add(
                    new StudentPlacementReportDTO(
                            student.getId(),
                            student.getName(),
                            student.getRollNumber(),
                            student.getDepartment(),
                            student.getCgpa(),
                            placed
                    )
            );
        }

        report.sort(
                Comparator.comparing(
                        StudentPlacementReportDTO::isPlaced
                ).reversed()
        );

        return report;
    }
    public CGPAStatisticsDTO getCGPAStatistics() {

        List<Student> students = studentRepository.findAll();

        List<Double> cgpas = new ArrayList<>();

        for (Student student : students) {

            if (student.getCgpa() != null) {
                cgpas.add(student.getCgpa());
            }
        }

        if (cgpas.isEmpty()) {
            return new CGPAStatisticsDTO(
                    0.0,
                    0.0,
                    0.0
            );
        }

        double totalCgpa = 0.0;
        double highestCgpa = cgpas.get(0);
        double lowestCgpa = cgpas.get(0);

        for (Double cgpa : cgpas) {

            totalCgpa += cgpa;

            if (cgpa > highestCgpa) {
                highestCgpa = cgpa;
            }

            if (cgpa < lowestCgpa) {
                lowestCgpa = cgpa;
            }
        }

        double averageCgpa =
                totalCgpa / cgpas.size();

        return new CGPAStatisticsDTO(
                averageCgpa,
                highestCgpa,
                lowestCgpa
        );
    }
    public List<SkillAnalyticsDTO> getSkillAnalytics() {

        List<StudentSkill> studentSkills =
                studentSkillRepository.findAll();

        Map<Long, String> skillNames = new HashMap<>();
        Map<Long, Set<Long>> studentsBySkill = new HashMap<>();
        Map<Long, Double> totalSkillLevels = new HashMap<>();

        for (StudentSkill studentSkill : studentSkills) {

            if (studentSkill.getSkill() == null
                    || studentSkill.getSkill().getId() == null) {
                continue;
            }

            Long skillId = studentSkill.getSkill().getId();
            String skillName = studentSkill.getSkill().getName();

            skillNames.put(skillId, skillName);

            if (studentSkill.getStudent() != null
                    && studentSkill.getStudent().getId() != null) {

                studentsBySkill
                        .computeIfAbsent(
                                skillId,
                                key -> new HashSet<>()
                        )
                        .add(studentSkill.getStudent().getId());
            }

            double skillLevel = studentSkill.getSkillLevel();

            totalSkillLevels.put(
                    skillId,
                    totalSkillLevels.getOrDefault(skillId, 0.0)
                            + skillLevel
            );
        }

        List<SkillAnalyticsDTO> report =
                new ArrayList<>();

        for (Long skillId : skillNames.keySet()) {

            long studentCount =
                    studentsBySkill
                            .getOrDefault(
                                    skillId,
                                    new HashSet<>()
                            )
                            .size();

            double totalLevel =
                    totalSkillLevels.getOrDefault(
                            skillId,
                            0.0
                    );

            double averageSkillLevel = 0.0;

            if (studentCount > 0) {
                averageSkillLevel =
                        totalLevel / studentCount;
            }

            report.add(
                    new SkillAnalyticsDTO(
                            skillId,
                            skillNames.get(skillId),
                            studentCount,
                            averageSkillLevel
                    )
            );
        }

        report.sort(
                Comparator.comparing(
                        SkillAnalyticsDTO::getStudentCount
                ).reversed()
        );

        return report;
    }

}