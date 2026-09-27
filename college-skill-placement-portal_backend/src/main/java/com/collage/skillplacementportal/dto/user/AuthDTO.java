package com.collage.skillplacementportal.dto.user;

public class AuthDTO {

    private String username;

    private String password;

    private String role;

    private Long studentId;

    public AuthDTO() {
    }

    public AuthDTO(
            String username,
            String password,
            String role) {

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}