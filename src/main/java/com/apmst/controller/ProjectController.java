package com.apmst.controller;

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

import com.apmst.entity.Project;
import com.apmst.entity.User;
import com.apmst.enums.ProjectStatus;
import com.apmst.enums.TaskStatus;
import com.apmst.service.ProjectService;
import com.apmst.service.TaskService;
import com.apmst.service.UserService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;

    // Show all projects
    @GetMapping
    public String getAllProjects(Model model, 
                                 Authentication authentication) {
        // Get logged in user
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email).orElseThrow();

        // Admin sees all projects
        // Manager/Employee sees only their projects
        if (currentUser.getRole().name().equals("ADMIN")) {
            model.addAttribute("projects", 
                    projectService.getAllProjects());
        } else {
            model.addAttribute("projects", 
                    projectService.getProjectsForUser(currentUser));
        }

        model.addAttribute("currentUser", currentUser);
        return "projects/projects";
    }

    // Show create project form
    @GetMapping("/create")
    public String showCreateForm(Model model, 
                                  Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email).orElseThrow();

        model.addAttribute("project", new Project());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", currentUser);
        return "projects/create-project";
    }

    // Handle create project form
    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project,
                                 @RequestParam(required = false) 
                                 java.util.List<Long> memberIds,
                                 Authentication authentication) {

        String email = authentication.getName();
        User currentUser = userService.findByEmail(email).orElseThrow();

        // Set manager as current user
        project.setManager(currentUser);
        project.setStatus(ProjectStatus.ACTIVE);

        // Add members if selected
        if (memberIds != null) {
            java.util.List<User> members = memberIds.stream()
                    .map(id -> userService.findById(id).orElseThrow())
                    .collect(java.util.stream.Collectors.toList());
            project.setMembers(members);
        }

        projectService.saveProject(project);
        return "redirect:/projects?success=true";
    }

    // Show project details
    @GetMapping("/{id}")
    public String getProjectDetails(@PathVariable Long id, 
                                     Model model) {
        projectService.findById(id).ifPresent(project -> {
            model.addAttribute("project", project);
        });
        return "projects/project-details";
    }

    // Delete project
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    // Update project status
    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id,
                                @RequestParam ProjectStatus status) {
        projectService.updateStatus(id, status);
        return "redirect:/projects/" + id;
    }
    
 // Save local project path
    @PostMapping("/path/{id}")
    public String saveLocalPath(@PathVariable Long id,
                                 @RequestParam String localPath) {
        projectService.findById(id).ifPresent(project -> {
            project.setLocalProjectPath(localPath);
            projectService.updateProject(project);
        });
        return "redirect:/projects/" + id;
    }
    
 // Show Kanban board for project
    @GetMapping("/{id}/kanban")
    public String getKanbanBoard(@PathVariable Long id, Model model) {
        projectService.findById(id).ifPresent(project -> {
            model.addAttribute("project", project);
            model.addAttribute("backlogTasks",
                    taskService.getTasksByProjectAndStatus(
                            project, TaskStatus.BACKLOG));
            model.addAttribute("todoTasks",
                    taskService.getTasksByProjectAndStatus(
                            project, TaskStatus.TODO));
            model.addAttribute("inProgressTasks",
                    taskService.getTasksByProjectAndStatus(
                            project, TaskStatus.IN_PROGRESS));
            model.addAttribute("inReviewTasks",
                    taskService.getTasksByProjectAndStatus(
                            project, TaskStatus.IN_REVIEW));
            model.addAttribute("doneTasks",
                    taskService.getTasksByProjectAndStatus(
                            project, TaskStatus.DONE));
        });
        return "kanban/kanban";
    }
}
