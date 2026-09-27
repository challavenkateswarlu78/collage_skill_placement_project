package com.collage.skillplacementportal.service.notification;

import com.collage.skillplacementportal.dto.notification.NotificationDTO;
import com.collage.skillplacementportal.entity.notification.Notification;
import com.collage.skillplacementportal.repository.notification.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDTO createNotification(
            Long userId,
            String title,
            String message,
            String type) {

        Notification notification = new Notification();

        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification savedNotification =
                notificationRepository.save(notification);

        return convertToDTO(savedNotification);
    }

    public List<NotificationDTO> getUserNotifications(Long userId) {

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getUnreadNotifications(Long userId) {

        return notificationRepository
                .findByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO markAsRead(
            Long notificationId,
            Long userId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found with id: "
                                                + notificationId));

        if (!notification.getUserId().equals(userId)) {
            throw new RuntimeException(
                    "You are not authorized to update this notification");
        }

        notification.setRead(true);

        Notification updatedNotification =
                notificationRepository.save(notification);

        return convertToDTO(updatedNotification);
    }

    public void markAllAsRead(Long userId) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserIdOrderByCreatedAtDesc(userId);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);
    }

    private NotificationDTO convertToDTO(
            Notification notification) {

        return new NotificationDTO(
                notification.getId(),
                notification.getUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
