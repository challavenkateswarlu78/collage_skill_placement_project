package com.collage.skillplacementportal.controller.notification;

import com.collage.skillplacementportal.dto.notification.NotificationDTO;
import com.collage.skillplacementportal.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUserNotifications(userId)
        );
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadNotifications(userId)
        );
    }

    @PutMapping("/{notificationId}/read/{userId}")
    public ResponseEntity<NotificationDTO> markAsRead(
            @PathVariable Long notificationId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.markAsRead(
                        notificationId,
                        userId
                )
        );
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @PathVariable Long userId) {

        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<NotificationDTO> createNotification(
            @RequestBody NotificationDTO request) {

        NotificationDTO notification =
                notificationService.createNotification(
                        request.getUserId(),
                        request.getTitle(),
                        request.getMessage(),
                        request.getType()
                );

        return ResponseEntity.ok(notification);
    }
}