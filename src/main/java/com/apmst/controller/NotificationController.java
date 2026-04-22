package com.apmst.controller;

import com.apmst.entity.User;
import com.apmst.service.NotificationService;
import com.apmst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

	 @Autowired
	    private NotificationService notificationService;

	    @Autowired
	    private UserService userService;

	    // Show all notifications
	    @GetMapping
	    public String getNotifications(Model model,
	                                    Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        model.addAttribute("notifications",
	                notificationService.getNotificationsByUser(currentUser));
	        model.addAttribute("unreadCount",
	                notificationService.countUnreadNotifications(currentUser));

	        // Mark all as read when page opens
	        notificationService.markAllAsRead(currentUser);

	        return "notifications/notifications";
	    }

	    // Mark single notification as read
	    @GetMapping("/read/{id}")
	    public String markAsRead(@PathVariable Long id) {
	        notificationService.markAsRead(id);
	        return "redirect:/notifications";
	    }

	    // Delete notification
	    @GetMapping("/delete/{id}")
	    public String deleteNotification(@PathVariable Long id) {
	        notificationService.deleteNotification(id);
	        return "redirect:/notifications";
	    }
}
