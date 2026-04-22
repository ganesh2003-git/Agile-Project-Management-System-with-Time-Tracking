package com.apmst.serviceIMPL;

import com.apmst.entity.Notification;
import com.apmst.entity.User;
import com.apmst.repository.NotificationRepository;
import com.apmst.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

	 @Autowired
	    private NotificationRepository notificationRepository;

	    @Override
	    public Notification createNotification(User user,
	                                            String message,
	                                            String type,
	                                            String link) {
	        Notification notification = new Notification();
	        notification.setUser(user);
	        notification.setMessage(message);
	        notification.setType(type);
	        notification.setLink(link);
	        notification.setRead(false);
	        return notificationRepository.save(notification);
	    }

	    @Override
	    public List<Notification> getNotificationsByUser(User user) {
	        return notificationRepository
	                .findByUserOrderByCreatedAtDesc(user);
	    }

	    @Override
	    public List<Notification> getUnreadNotifications(User user) {
	        return notificationRepository
	                .findByUserAndIsReadFalse(user);
	    }

	    @Override
	    public Long countUnreadNotifications(User user) {
	        return notificationRepository
	                .countByUserAndIsReadFalse(user);
	    }

	    @Override
	    public void markAsRead(Long id) {
	        notificationRepository.findById(id).ifPresent(n -> {
	            n.setRead(true);
	            notificationRepository.save(n);
	        });
	    }

	    @Override
	    public void markAllAsRead(User user) {
	        List<Notification> unread = getUnreadNotifications(user);
	        unread.forEach(n -> {
	            n.setRead(true);
	            notificationRepository.save(n);
	        });
	    }

	    @Override
	    public void deleteNotification(Long id) {
	        notificationRepository.deleteById(id);
	    }
}
