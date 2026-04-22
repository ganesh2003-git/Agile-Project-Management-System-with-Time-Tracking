package com.apmst.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication; // Corrected this line
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apmst.entity.User;
import com.apmst.enums.TaskStatus;
import com.apmst.service.NotificationService;
import com.apmst.service.ProjectService;
import com.apmst.service.TaskService;
import com.apmst.service.TimeLogService;
import com.apmst.service.UserService;

@Controller
public class AutoController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private ProjectService projectService; // This line fixes your error

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TimeLogService timeLogService;
    
   
	
	@GetMapping("/")
    public String landingPage() {
        return "index"; // This points to src/main/resources/templates/index.html
    }

	
	//Show login page
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	//show dashboard after login
	@GetMapping("/dashboard")
	public String dashboard(Model model, Authentication authentication) {
	    String email = authentication.getName();
	    User currentUser = userService.findByEmail(email).orElseThrow();

	    // Data containers
	    long activeProjectsCount;
	    long completedTasksCount;
	    long inProgressTasksCount;
	    double totalHoursLogged;

	    // Check user role for Data Scoping
	    if (currentUser.getRole().name().equals("ADMIN") || currentUser.getRole().name().equals("PROJECT_MANAGER")) {
	        // CLIENT VIEW (Manager/Admin): Show global company stats
	        activeProjectsCount = projectService.getAllProjects().size();
	        
	        completedTasksCount = taskService.getTasksByStatus(TaskStatus.DONE).size();
	        
	        inProgressTasksCount = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS).size();
	        
	        totalHoursLogged = taskService.getAllTasks().stream()
	                .mapToDouble(t -> t.getActualHours() != null ? t.getActualHours() : 0.0)
	                .sum();
	    } else {
	        // USER VIEW (Employee): Show only their assigned stats
	        // Check if projectService has getProjectsByUser, otherwise use getAllProjects
	        activeProjectsCount = projectService.getAllProjects().stream()
	                .filter(p -> p.getTasks().stream().anyMatch(t -> t.getAssignee() != null && t.getAssignee().equals(currentUser)))
	                .count();

	        completedTasksCount = taskService.getTasksByAssigneeAndStatus(currentUser, TaskStatus.DONE).size();

	        inProgressTasksCount = taskService.getTasksByAssigneeAndStatus(currentUser, TaskStatus.IN_PROGRESS).size();

	        totalHoursLogged = taskService.getTasksByAssignee(currentUser).stream()
	                .mapToDouble(t -> t.getActualHours() != null ? t.getActualHours() : 0.0)
	                .sum();
	    }

	    Long unreadCount = notificationService.countUnreadNotifications(currentUser);

	    // Populate Model for Thymeleaf
	    model.addAttribute("currentUser", currentUser);
	    model.addAttribute("unreadCount", unreadCount);
	    model.addAttribute("activeProjects", activeProjectsCount);
	    model.addAttribute("completedTasks", completedTasksCount);
	    model.addAttribute("inProgress", inProgressTasksCount);
	    model.addAttribute("totalHours", totalHoursLogged);

	    return "dashboard";
	}
	
	// Show register page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle register form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        // Check if email already exists
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        // Save user
        userService.saveUser(user);
        return "redirect:/login?registered=true";
    }
    
    //sending mail to regrading to project services
    @PostMapping("/send-request")
    public String handleServiceRequest(@RequestParam String userEmail, 
                                       @RequestParam String serviceType, 
                                       @RequestParam String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo("apmstservice2003@gmail.com"); // Official System Email
            mail.setSubject("🚀 Service Request: " + serviceType);
            
            String content = "New Inquiry from: " + userEmail + "\n" +
                             "Service: " + serviceType + "\n" +
                             "Message: " + message;
            
            mail.setText(content);
            mail.setReplyTo(userEmail); // Allows you to reply directly to the sender

            mailSender.send(mail);
        } catch (Exception e) {
            System.err.println("Email Error: " + e.getMessage());
            return "redirect:/?error=true";
        }
        
        // Redirect back to home with the success parameter
        return "redirect:/?requestSent=true";
    }
}
