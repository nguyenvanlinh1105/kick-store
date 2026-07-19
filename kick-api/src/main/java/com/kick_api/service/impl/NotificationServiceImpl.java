package com.kick_api.service.impl;

import com.kick_api.entity.Notification;
import com.kick_api.entity.User;
import com.kick_api.constant.NotificationType;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.NotificationRepository;
import com.kick_api.repository.UserRepository;
import com.kick_api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Notification createNotification(Long userId, String title, String content, NotificationType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(type)
                .isRead(false)
                .build();

        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_INPUT, "Thông báo không tồn tại!"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        notificationRepository.markAllAsRead(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> getUserNotifications(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return notificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadNotifications(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
