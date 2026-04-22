package com.apmst.controller;

import com.apmst.entity.Project;
import com.apmst.entity.User;
import com.apmst.enums.TaskStatus;
import com.apmst.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

	 @Autowired
	    private ProjectService projectService;

	    @Autowired
	    private TaskService taskService;

	    @Autowired
	    private UserService userService;

	    @Autowired
	    private TimeLogService timeLogService;

	    @Autowired
	    private SprintService sprintService;

	    // Main analytics dashboard
	    @GetMapping
	    public String getAnalytics(Model model,
	                                Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        // Get all projects
	        List<Project> projects;
	        if (currentUser.getRole().name().equals("ADMIN")) {
	            projects = projectService.getAllProjects();
	        } else {
	            projects = projectService.getProjectsForUser(currentUser);
	        }

	        // Task counts by status
	        long backlogCount = taskService.getAllTasks().stream()
	                .filter(t -> t.getStatus() == TaskStatus.BACKLOG)
	                .count();
	        long todoCount = taskService.getAllTasks().stream()
	                .filter(t -> t.getStatus() == TaskStatus.TODO)
	                .count();
	        long inProgressCount = taskService.getAllTasks().stream()
	                .filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS)
	                .count();
	        long inReviewCount = taskService.getAllTasks().stream()
	                .filter(t -> t.getStatus() == TaskStatus.IN_REVIEW)
	                .count();
	        long doneCount = taskService.getAllTasks().stream()
	                .filter(t -> t.getStatus() == TaskStatus.DONE)
	                .count();

	        // Total tasks
	        long totalTasks = taskService.getAllTasks().size();

	        // Completion percentage
	        double completionPercentage = totalTasks > 0 ?
	                (double) doneCount / totalTasks * 100 : 0;

	        // All users
	        List<User> users = userService.getAllUsers();

	        // Hours per user
	        List<Double> hoursPerUser = users.stream()
	                .map(u -> timeLogService.getTotalHoursByUser(u))
	                .collect(Collectors.toList());

	        // User names
	        List<String> userNames = users.stream()
	                .map(User::getFullName)
	                .collect(Collectors.toList());

	        // Hours per project
	        List<Double> hoursPerProject = projects.stream()
	                .map(p -> timeLogService.getTotalHoursByProject(p))
	                .collect(Collectors.toList());

	        // Project names
	        List<String> projectNames = projects.stream()
	                .map(Project::getName)
	                .collect(Collectors.toList());

	        model.addAttribute("projects", projects);
	        model.addAttribute("users", users);
	        model.addAttribute("currentUser", currentUser);
	        model.addAttribute("totalProjects", projects.size());
	        model.addAttribute("totalTasks", totalTasks);
	        model.addAttribute("backlogCount", backlogCount);
	        model.addAttribute("todoCount", todoCount);
	        model.addAttribute("inProgressCount", inProgressCount);
	        model.addAttribute("inReviewCount", inReviewCount);
	        model.addAttribute("doneCount", doneCount);
	        model.addAttribute("completionPercentage",
	                String.format("%.1f", completionPercentage));
	        model.addAttribute("userNames", userNames);
	        model.addAttribute("hoursPerUser", hoursPerUser);
	        model.addAttribute("projectNames", projectNames);
	        model.addAttribute("hoursPerProject", hoursPerProject);

	        return "analytics/analytics";
	    }

	    // Project specific analytics
	    @GetMapping("/project/{id}")
	    public String getProjectAnalytics(@PathVariable Long id,
	                                       Model model) {
	        projectService.findById(id).ifPresent(project -> {

	            // Task counts
	            long backlog = taskService.getTasksByProject(project)
	                    .stream()
	                    .filter(t -> t.getStatus() == TaskStatus.BACKLOG)
	                    .count();
	            long todo = taskService.getTasksByProject(project)
	                    .stream()
	                    .filter(t -> t.getStatus() == TaskStatus.TODO)
	                    .count();
	            long inProgress = taskService.getTasksByProject(project)
	                    .stream()
	                    .filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS)
	                    .count();
	            long inReview = taskService.getTasksByProject(project)
	                    .stream()
	                    .filter(t -> t.getStatus() == TaskStatus.IN_REVIEW)
	                    .count();
	            long done = taskService.getTasksByProject(project)
	                    .stream()
	                    .filter(t -> t.getStatus() == TaskStatus.DONE)
	                    .count();

	            long total = taskService.getTasksByProject(project).size();
	            double completion = total > 0 ?
	                    (double) done / total * 100 : 0;

	            // Total hours
	            Double totalHours = timeLogService
	                    .getTotalHoursByProject(project);

	            // Sprints
	            List sprints = sprintService
	                    .getSprintsByProject(project);

	            model.addAttribute("project", project);
	            model.addAttribute("backlog", backlog);
	            model.addAttribute("todo", todo);
	            model.addAttribute("inProgress", inProgress);
	            model.addAttribute("inReview", inReview);
	            model.addAttribute("done", done);
	            model.addAttribute("total", total);
	            model.addAttribute("completion",
	                    String.format("%.1f", completion));
	            model.addAttribute("totalHours",
	                    totalHours != null ? totalHours : 0.0);
	            model.addAttribute("sprints", sprints);
	        });

	        return "analytics/project-analytics";
	    }
}
