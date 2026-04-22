package com.apmst.service;

import com.apmst.entity.Notification;
import com.apmst.entity.User;
import java.util.List;

public interface NotificationService {

	  // Create notification
    Notification createNotification(User user,
                                     String message,
                                     String type,
                                     String link);

    // Get all notifications for user
    List<Notification> getNotificationsByUser(User user);

    // Get unread notifications for user
    List<Notification> getUnreadNotifications(User user);

    // Count unread notifications
    Long countUnreadNotifications(User user);

    // Mark notification as read
    void markAsRead(Long id);

    // Mark all notifications as read
    void markAllAsRead(User user);

    // Delete notification
    void deleteNotification(Long id);
}
