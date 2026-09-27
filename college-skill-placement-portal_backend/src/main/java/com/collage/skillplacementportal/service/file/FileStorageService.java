package com.collage.skillplacementportal.service.file;

import com.collage.skillplacementportal.dto.file.FileMetadataDTO;
import com.collage.skillplacementportal.entity.file.FileMetadata;
import com.collage.skillplacementportal.repository.file.FileMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    private final FileMetadataRepository fileMetadataRepository;

    private final Path storageLocation =
            Paths.get("uploads").toAbsolutePath().normalize();

    public FileStorageService(
            FileMetadataRepository fileMetadataRepository) {

        this.fileMetadataRepository = fileMetadataRepository;

        try {
            Files.createDirectories(storageLocation);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not create file storage directory", e);
        }
    }

    public FileMetadataDTO storeFile(
            MultipartFile file,
            Long userId) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File cannot be empty");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new RuntimeException("Invalid file name");
        }

        String storedFileName =
                UUID.randomUUID() + "_" + originalFileName;

        Path targetLocation =
                storageLocation.resolve(storedFileName).normalize();

        if (!targetLocation.startsWith(storageLocation)) {
            throw new RuntimeException("Invalid file path");
        }

        try {
            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not store file", e);
        }

        FileMetadata metadata = new FileMetadata();

        metadata.setUserId(userId);
        metadata.setFileName(originalFileName);
        metadata.setFileType(file.getContentType());
        metadata.setFileSize(file.getSize());
        metadata.setFilePath(targetLocation.toString());
        metadata.setUploadedAt(LocalDateTime.now());

        FileMetadata savedMetadata =
                fileMetadataRepository.save(metadata);

        return convertToDTO(savedMetadata);
    }

    public List<FileMetadataDTO> getUserFiles(Long userId) {

        return fileMetadataRepository
                .findByUserIdOrderByUploadedAtDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FileMetadataDTO convertToDTO(
            FileMetadata metadata) {

        return new FileMetadataDTO(
                metadata.getId(),
                metadata.getUserId(),
                metadata.getFileName(),
                metadata.getFileType(),
                metadata.getFileSize(),
                metadata.getFilePath(),
                metadata.getUploadedAt()
        );
    }
}
