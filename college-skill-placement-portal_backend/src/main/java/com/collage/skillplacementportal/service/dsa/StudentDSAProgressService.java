package com.collage.skillplacementportal.service.dsa;

import com.collage.skillplacementportal.dto.dsa.*;
import com.collage.skillplacementportal.entity.dsa.DSAProblem;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.entity.dsa.StudentDSAProgress;
import com.collage.skillplacementportal.repository.dsa.DSAProblemRepository;
import com.collage.skillplacementportal.repository.dsa.StudentDSAProgressRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Service
public class StudentDSAProgressService {

    private final StudentDSAProgressRepository progressRepository;
    private final StudentRepository studentRepository;
    private final DSAProblemRepository dsaProblemRepository;

    public StudentDSAProgressService(
            StudentDSAProgressRepository progressRepository,
            StudentRepository studentRepository,
            DSAProblemRepository dsaProblemRepository) {

        this.progressRepository = progressRepository;
        this.studentRepository = studentRepository;
        this.dsaProblemRepository = dsaProblemRepository;
    }

    // Mark a DSA problem as solved
    public StudentDSAProgress markAsSolved(
            Long studentId,
            Long problemId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: "
                                        + studentId
                        )
                );

        DSAProblem problem = dsaProblemRepository.findById(problemId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "DSA problem not found with id: "
                                        + problemId
                        )
                );

        StudentDSAProgress progress =
                progressRepository
                        .findByStudentIdAndProblemId(
                                studentId,
                                problemId
                        )
                        .orElse(new StudentDSAProgress());

        progress.setStudent(student);
        progress.setProblem(problem);
        progress.setSolved(true);
        progress.setPointsEarned(problem.getPoints());
        progress.setSolvedAt(LocalDateTime.now());

        return progressRepository.save(progress);
    }

    // Get all progress of a student
    public List<StudentDSAProgress> getStudentProgress(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        return progressRepository.findByStudentId(studentId);
    }

    // Get solved problems of a student
    public List<StudentDSAProgress> getSolvedProblems(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        return progressRepository
                .findByStudentIdAndSolvedTrue(studentId);
    }

    // Get students who solved a problem
    public List<StudentDSAProgress> getProblemProgress(
            Long problemId) {

        if (!dsaProblemRepository.existsById(problemId)) {
            throw new RuntimeException(
                    "DSA problem not found with id: " + problemId
            );
        }

        return progressRepository.findByProblemId(problemId);
    }
    public int getTotalPoints(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<StudentDSAProgress> solvedProblems =
                progressRepository
                        .findByStudentIdAndSolvedTrue(studentId);

        int totalPoints = 0;

        for (StudentDSAProgress progress : solvedProblems) {

            if (progress.getPointsEarned() != null) {
                totalPoints += progress.getPointsEarned();
            }
        }

        return totalPoints;
    }
    public List<LeaderboardDTO> getLeaderboard() {

        List<Student> students =
                studentRepository.findAll();

        List<LeaderboardDTO> leaderboard =
                new ArrayList<>();

        for (Student student : students) {

            List<StudentDSAProgress> solvedProblems =
                    progressRepository
                            .findByStudentIdAndSolvedTrue(
                                    student.getId()
                            );

            int totalPoints = 0;

            for (StudentDSAProgress progress : solvedProblems) {

                if (progress.getPointsEarned() != null) {
                    totalPoints += progress.getPointsEarned();
                }
            }

            leaderboard.add(
                    new LeaderboardDTO(
                            0,
                            student.getId(),
                            student.getName(),
                            solvedProblems.size(),
                            totalPoints
                    )
            );
        }

        // Sort by total points, highest first
        leaderboard.sort(
                Comparator.comparingInt(
                        LeaderboardDTO::getTotalPoints
                ).reversed()
        );

        // Assign ranks
        for (int i = 0; i < leaderboard.size(); i++) {

            leaderboard.get(i).setRank(i + 1);
        }

        return leaderboard;
    }

    public int getCurrentStreak(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<StudentDSAProgress> solvedProblems =
                progressRepository
                        .findByStudentIdAndSolvedTrue(studentId);

        Set<LocalDate> solvedDates = new HashSet<>();

        for (StudentDSAProgress progress : solvedProblems) {

            if (progress.getSolvedAt() != null) {
                solvedDates.add(
                        progress.getSolvedAt().toLocalDate()
                );
            }
        }

        LocalDate today = LocalDate.now();

        int streak = 0;

        LocalDate checkDate = today;

        while (solvedDates.contains(checkDate)) {

            streak++;

            checkDate = checkDate.minusDays(1);
        }

        return streak;
    }
    public DSAProgressDTO getStudentSummary(Long studentId) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found with id: "
                                                + studentId
                                )
                        );

        List<StudentDSAProgress> solvedProblems =
                progressRepository
                        .findByStudentIdAndSolvedTrue(studentId);

        int totalPoints = 0;

        for (StudentDSAProgress progress : solvedProblems) {

            if (progress.getPointsEarned() != null) {
                totalPoints += progress.getPointsEarned();
            }
        }

        int currentStreak =
                getCurrentStreak(studentId);

        return new DSAProgressDTO(
                student.getId(),
                student.getName(),
                solvedProblems.size(),
                totalPoints,
                currentStreak
        );
    }

    public List<DSATopicProgressDTO> getTopicProgress(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<DSAProblem> allProblems =
                dsaProblemRepository.findAll();

        List<StudentDSAProgress> solvedProblems =
                progressRepository
                        .findByStudentIdAndSolvedTrue(
                                studentId
                        );

        Map<Long, StudentDSAProgress> solvedMap =
                new HashMap<>();

        for (StudentDSAProgress progress : solvedProblems) {
            solvedMap.put(
                    progress.getProblem().getId(),
                    progress
            );
        }

        Map<String, Integer> totalByTopic =
                new HashMap<>();

        Map<String, Integer> solvedByTopic =
                new HashMap<>();

        Map<String, Integer> pointsByTopic =
                new HashMap<>();

        for (DSAProblem problem : allProblems) {

            String topic = problem.getTopic();

            totalByTopic.put(
                    topic,
                    totalByTopic.getOrDefault(topic, 0) + 1
            );

            if (solvedMap.containsKey(problem.getId())) {

                solvedByTopic.put(
                        topic,
                        solvedByTopic.getOrDefault(topic, 0) + 1
                );

                StudentDSAProgress progress =
                        solvedMap.get(problem.getId());

                if (progress.getPointsEarned() != null) {

                    pointsByTopic.put(
                            topic,
                            pointsByTopic.getOrDefault(topic, 0)
                                    + progress.getPointsEarned()
                    );
                }
            }
        }

        List<DSATopicProgressDTO> result =
                new ArrayList<>();

        for (String topic : totalByTopic.keySet()) {

            int total =
                    totalByTopic.get(topic);

            int solved =
                    solvedByTopic.getOrDefault(topic, 0);

            int points =
                    pointsByTopic.getOrDefault(topic, 0);

            double percentage = 0;

            if (total > 0) {
                percentage =
                        ((double) solved / total) * 100;
            }

            result.add(
                    new DSATopicProgressDTO(
                            topic,
                            total,
                            solved,
                            points,
                            percentage
                    )
            );
        }

        return result;
    }
    public List<DSADifficultyProgressDTO> getDifficultyProgress(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<DSAProblem> allProblems =
                dsaProblemRepository.findAll();

        List<StudentDSAProgress> solvedProblems =
                progressRepository
                        .findByStudentIdAndSolvedTrue(studentId);

        Map<Long, StudentDSAProgress> solvedMap =
                new HashMap<>();

        for (StudentDSAProgress progress : solvedProblems) {

            solvedMap.put(
                    progress.getProblem().getId(),
                    progress
            );
        }

        Map<String, Integer> totalByDifficulty =
                new HashMap<>();

        Map<String, Integer> solvedByDifficulty =
                new HashMap<>();

        Map<String, Integer> pointsByDifficulty =
                new HashMap<>();

        for (DSAProblem problem : allProblems) {

            String difficulty =
                    problem.getDifficulty();

            totalByDifficulty.put(
                    difficulty,
                    totalByDifficulty.getOrDefault(
                            difficulty, 0
                    ) + 1
            );

            if (solvedMap.containsKey(problem.getId())) {

                solvedByDifficulty.put(
                        difficulty,
                        solvedByDifficulty.getOrDefault(
                                difficulty, 0
                        ) + 1
                );

                StudentDSAProgress progress =
                        solvedMap.get(problem.getId());

                if (progress.getPointsEarned() != null) {

                    pointsByDifficulty.put(
                            difficulty,
                            pointsByDifficulty.getOrDefault(
                                    difficulty, 0
                            ) + progress.getPointsEarned()
                    );
                }
            }
        }

        List<DSADifficultyProgressDTO> result =
                new ArrayList<>();

        for (String difficulty :
                totalByDifficulty.keySet()) {

            int total =
                    totalByDifficulty.get(difficulty);

            int solved =
                    solvedByDifficulty.getOrDefault(
                            difficulty, 0
                    );

            int points =
                    pointsByDifficulty.getOrDefault(
                            difficulty, 0
                    );

            double percentage = 0;

            if (total > 0) {
                percentage =
                        ((double) solved / total) * 100;
            }

            result.add(
                    new DSADifficultyProgressDTO(
                            difficulty,
                            total,
                            solved,
                            points,
                            percentage
                    )
            );
        }

        return result;
    }
    public List<DSAPlatformProgressDTO> getPlatformProgress(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<DSAProblem> allProblems =
                dsaProblemRepository.findAll();

        List<StudentDSAProgress> solvedProblems =
                progressRepository
                        .findByStudentIdAndSolvedTrue(studentId);

        Map<Long, StudentDSAProgress> solvedMap =
                new HashMap<>();

        for (StudentDSAProgress progress : solvedProblems) {

            solvedMap.put(
                    progress.getProblem().getId(),
                    progress
            );
        }

        Map<String, Integer> totalByPlatform =
                new HashMap<>();

        Map<String, Integer> solvedByPlatform =
                new HashMap<>();

        Map<String, Integer> pointsByPlatform =
                new HashMap<>();

        for (DSAProblem problem : allProblems) {

            String platform = problem.getPlatform();

            totalByPlatform.put(
                    platform,
                    totalByPlatform.getOrDefault(
                            platform, 0
                    ) + 1
            );

            if (solvedMap.containsKey(problem.getId())) {

                solvedByPlatform.put(
                        platform,
                        solvedByPlatform.getOrDefault(
                                platform, 0
                        ) + 1
                );

                StudentDSAProgress progress =
                        solvedMap.get(problem.getId());

                if (progress.getPointsEarned() != null) {

                    pointsByPlatform.put(
                            platform,
                            pointsByPlatform.getOrDefault(
                                    platform, 0
                            ) + progress.getPointsEarned()
                    );
                }
            }
        }

        List<DSAPlatformProgressDTO> result =
                new ArrayList<>();

        for (String platform :
                totalByPlatform.keySet()) {

            int total =
                    totalByPlatform.get(platform);

            int solved =
                    solvedByPlatform.getOrDefault(
                            platform, 0
                    );

            int points =
                    pointsByPlatform.getOrDefault(
                            platform, 0
                    );

            double percentage = 0;

            if (total > 0) {
                percentage =
                        ((double) solved / total) * 100;
            }

            result.add(
                    new DSAPlatformProgressDTO(
                            platform,
                            total,
                            solved,
                            points,
                            percentage
                    )
            );
        }

        return result;
    }
    public double getDSAProgressPercentage(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<DSAProblem> allProblems =
                dsaProblemRepository.findAll();

        List<StudentDSAProgress> solvedProblems =
                progressRepository.findByStudentIdAndSolvedTrue(
                        studentId
                );

        if (allProblems.isEmpty()) {
            return 0.0;
        }

        return ((double) solvedProblems.size()
                / allProblems.size()) * 100;
    }
}