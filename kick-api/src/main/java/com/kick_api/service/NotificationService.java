package com.kick_api.service;

import com.kick_api.entity.Notification;
import com.kick_api.constant.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Notification createNotification(Long userId, String title, String content, NotificationType type);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    Page<Notification> getUserNotifications(Long userId, Pageable pageable);
    long countUnreadNotifications(Long userId);
}
