package com.apmst.repository;


import com.apmst.entity.Notification;
import com.apmst.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	 // Find all notifications for user
    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    // Find unread notifications for user
    List<Notification> findByUserAndIsReadFalse(User user);

    // Count unread notifications
    Long countByUserAndIsReadFalse(User user);
}
