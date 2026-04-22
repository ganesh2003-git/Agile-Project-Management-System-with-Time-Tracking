package com.apmst.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apmst.entity.Task;
import com.apmst.entity.User;
import com.apmst.enums.TaskPriority;
import com.apmst.enums.TaskStatus;
import com.apmst.service.EmployeeScoreService;
import com.apmst.service.GitHubService;
import com.apmst.service.NotificationService;
import com.apmst.service.ProjectService;
import com.apmst.service.SprintService;
import com.apmst.service.TaskService;
import com.apmst.service.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	 @Autowired
	    private TaskService taskService;

	    @Autowired
	    private ProjectService projectService;

	    @Autowired
	    private SprintService sprintService;

	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private EmployeeScoreService employeeScoreService;
	    
	    @Autowired
	    private NotificationService notificationService;
	    
	    @Autowired
	    private GitHubService gitHubService;

	    // Upload directory
	    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

	    // Show all tasks for logged in user
	    @GetMapping
	    public String getAllTasks(Model model,
	                              Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        if (currentUser.getRole().name().equals("ADMIN")) {
	            model.addAttribute("tasks", taskService.getAllTasks());
	        } else {
	            model.addAttribute("tasks",
	                    taskService.getTasksByAssignee(currentUser));
	        }

	        model.addAttribute("currentUser", currentUser);
	        return "tasks/tasks";
	    }

	    // Show create task form
	    @GetMapping("/create/{projectId}")
	    public String showCreateForm(@PathVariable Long projectId,
	                                  Model model) {
	        projectService.findById(projectId).ifPresent(project -> {
	            model.addAttribute("project", project);
	            model.addAttribute("task", new Task());
	            model.addAttribute("sprints",
	                    sprintService.getSprintsByProject(project));
	            model.addAttribute("users", userService.getAllUsers());
	            model.addAttribute("priorities", TaskPriority.values());
	            model.addAttribute("statuses", TaskStatus.values());
	        });
	        return "tasks/create-task";
	    }

	    // Handle create task form
	    @PostMapping("/create/{projectId}")
	    public String createTask(@PathVariable Long projectId,
	                              @ModelAttribute Task task,
	                              @RequestParam(required = false) Long sprintId,
	                              @RequestParam(required = false) Long assigneeId,
	                              @RequestParam(required = false) MultipartFile attachment,
	                              Authentication authentication) throws IOException {

	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        projectService.findById(projectId).ifPresent(project -> {
	            task.setProject(project);
	            task.setCreatedBy(currentUser);

	            // Set sprint if selected
	            if (sprintId != null) {
	                sprintService.findById(sprintId)
	                        .ifPresent(task::setSprint);
	            }

	            // Set assignee if selected
	            if (assigneeId != null) {
	                userService.findById(assigneeId)
	                        .ifPresent(task::setAssignee);
	            }
	        });

	        // Handle file upload
	        if (attachment != null && !attachment.isEmpty()) {
	            String fileName = System.currentTimeMillis()
	                    + "_" + attachment.getOriginalFilename();
	            Path uploadPath = Paths.get(UPLOAD_DIR);
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }
	            Files.copy(attachment.getInputStream(),
	                    uploadPath.resolve(fileName));
	            task.setAttachmentPath("/uploads/" + fileName);
	        }

	        taskService.saveTask(task);
	        
	     // Send notification to assignee
	        if (task.getAssignee() != null) {
	            notificationService.createNotification(
	                task.getAssignee(),
	                "New task assigned to you: " + task.getTitle(),
	                "TASK_ASSIGNED",
	                "/tasks/" + task.getId()
	            );
	        }
	        return "redirect:/tasks?success=true";
	    }
	    
	 
	
	    // Show task details
	    @GetMapping("/{id}")
	    public String getTaskDetails(@PathVariable Long id,
	                                  Model model,
	                                  Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        taskService.findById(id).ifPresent(task -> {
	            model.addAttribute("task", task);
	            model.addAttribute("statuses", TaskStatus.values());
	            model.addAttribute("currentUser", currentUser);
	        });
	        return "tasks/task-details";
	    }
	    

	    // Update task status
	    @PostMapping("/status/{id}")
	    public String updateStatus(@PathVariable Long id,
	                                @RequestParam TaskStatus status,
	                                Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        taskService.findById(id).ifPresent(task -> {
	            // Employee marks as DONE -> moves to IN_REVIEW
	            if (status == TaskStatus.DONE && currentUser.getRole().name().equals("DEVELOPER")) {
	                task.setStatus(TaskStatus.IN_REVIEW);
	                taskService.updateTask(task);

	                // FIX: Identify the Recipient (The Project Manager assigned to the project)
	                User recipient = task.getProject().getManager();
	                
	                // Fallback: If no manager is assigned to the project, notify the Admin (User ID 1)
	                if (recipient == null) {
	                    recipient = userService.findById(1L).orElse(null);
	                }

	                if (recipient != null) {
	                    notificationService.createNotification(
	                        recipient,
	                        "🔍 Review Required: " + task.getTitle() + " (Submitted by " + currentUser.getFullName() + ")",
	                        "TASK_REVIEW",
	                        "/tasks/" + task.getId()
	                    );
	                }
	            } else {
	                // Normal status update
	                task.setStatus(status);
	                taskService.updateTask(task);
	            }
	        });
	        return "redirect:/tasks/" + id;
	    }
	 // Manager approves task → push to GitHub
	    @GetMapping("/approve/{id}")
	    public String approveTask(@PathVariable Long id, Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        taskService.findById(id).ifPresent(task -> {
	            // 1. Mark as DONE in DB
	            task.setStatus(TaskStatus.DONE);
	            taskService.updateTask(task);

	            // 2. Logic to read the ACTUAL CODE from VS Code path
	            String fileContent = "";
	            String fileName = "completed-task-" + id + ".txt"; // Default filename
	            
	            try {
	                String localPath = task.getProject().getLocalProjectPath();
	                if (localPath != null && !localPath.isEmpty()) {
	                    Path path = Paths.get(localPath);
	                    
	                    // If it's a directory, we'll look for a specific file or just list files
	                    // For this example, we assume the path points to the specific file worked on
	                    if (Files.exists(path)) {
	                        fileContent = Files.readString(path);
	                        fileName = path.getFileName().toString();
	                    }
	                }
	            } catch (IOException e) {
	                fileContent = "Error reading local file: " + e.getMessage();
	            }

	            // Fallback if file is empty
	            if (fileContent.isEmpty()) {
	                fileContent = "// No local code found. Task Summary: " + task.getTitle();
	            }

	            // 3. PUSH ACTUAL CODE TO GITHUB
	            String githubUrl = gitHubService.pushFileToGitHub(
	                    fileName,
	                    fileContent, // This is now your actual VS Code code!
	                    "🚀 Approved Code Push: " + task.getTitle() + " | by " + currentUser.getFullName()
	            );

	            // 4. Save GitHub URL and notify
	            if (githubUrl != null) {
	                task.setGithubUrl(githubUrl);
	                taskService.updateTask(task);
	            }
	            
	            // Award points (keep your existing points logic here...)
	            employeeScoreService.awardPointsOnTime(task.getAssignee());
	        });

	        return "redirect:/tasks/" + id + "?approved=true";
	    }
	    // Manager rejects task → back to IN_PROGRESS
	    @PostMapping("/reject/{id}")
	    public String rejectTask(@PathVariable Long id, 
	                             @RequestParam("rejectionReason") String reason) {
	        taskService.findById(id).ifPresent(task -> {
	            // Move back to IN_PROGRESS so developer can edit
	            task.setStatus(TaskStatus.IN_PROGRESS);
	            
	            // Save the reason so it shows in the blue alert box on the details page
	            task.setLastFeedback(reason); 
	            taskService.updateTask(task);

	            // Notify the developer that they have work to fix
	            if (task.getAssignee() != null) {
	                notificationService.createNotification(
	                    task.getAssignee(),
	                    "❌ Task Rejected: " + task.getTitle() + ". Feedback: " + reason,
	                    "TASK_REJECTED",
	                    "/tasks/" + task.getId()
	                );
	            }
	        });
	        return "redirect:/tasks/" + id + "?rejected=true";
	    }
	    // Start timer
	    @GetMapping("/start/{id}")
	    public String startTimer(@PathVariable Long id) {
	        taskService.startTimer(id);
	        return "redirect:/tasks/" + id;
	    }

	    // Stop timer
	    @GetMapping("/stop/{id}")
	    public String stopTimer(@PathVariable Long id) {
	        taskService.stopTimer(id);
	        return "redirect:/tasks/" + id;
	    }

	    // Delete task
	    @GetMapping("/delete/{id}")
	    public String deleteTask(@PathVariable Long id) {
	        taskService.deleteTask(id);
	        return "redirect:/tasks";
	    }
	    
	 // REST endpoint for drag and drop status update
	    @PostMapping("/api/status/{id}")
	    @ResponseBody
	    public String updateStatusApi(@PathVariable Long id,
	                                   @RequestParam TaskStatus status) {
	        taskService.updateStatus(id, status);
	        return "success";
	    }
}
