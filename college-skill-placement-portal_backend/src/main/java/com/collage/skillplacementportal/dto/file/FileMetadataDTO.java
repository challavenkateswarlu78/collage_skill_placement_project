package com.collage.skillplacementportal.dto.file;

import java.time.LocalDateTime;

public class FileMetadataDTO {

    private Long id;
    private Long userId;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String filePath;
    private LocalDateTime uploadedAt;

    public FileMetadataDTO() {
    }

    public FileMetadataDTO(
            Long id,
            Long userId,
            String fileName,
            String fileType,
            Long fileSize,
            String filePath,
            LocalDateTime uploadedAt) {

        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
