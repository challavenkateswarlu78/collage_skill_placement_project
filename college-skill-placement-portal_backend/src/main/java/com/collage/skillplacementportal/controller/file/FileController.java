package com.collage.skillplacementportal.controller.file;

import com.collage.skillplacementportal.dto.file.FileMetadataDTO;
import com.collage.skillplacementportal.repository.file.FileMetadataRepository;
import com.collage.skillplacementportal.service.file.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.collage.skillplacementportal.entity.file.FileMetadata;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;
    private final FileMetadataRepository fileMetadataRepository;

    public FileController(FileStorageService fileStorageService, FileMetadataRepository fileMetadataRepository) {
        this.fileStorageService = fileStorageService;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetadataDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long userId) {

        FileMetadataDTO uploadedFile =
                fileStorageService.storeFile(file, userId);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FileMetadataDTO>> getUserFiles(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                fileStorageService.getUserFiles(userId)
        );
    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long fileId) {

        FileMetadata metadata =
                fileMetadataRepository.findById(fileId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "File not found with id: " + fileId));

        try {
            Path filePath = Paths.get(metadata.getFilePath());

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException(
                        "File does not exist or cannot be read");
            }

            String contentType = metadata.getFileType();

            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    metadata.getFileName() + "\""
                    )
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not download file", e);
        }
    }
}
