package com.apmst.controller;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.enums.SprintStatus;
import com.apmst.service.ProjectService;
import com.apmst.service.SprintService;
import com.apmst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sprints")
public class SprintController {

	  @Autowired
	    private SprintService sprintService;

	    @Autowired
	    private ProjectService projectService;

	    @Autowired
	    private UserService userService;

	    // Show all sprints for a project
	    @GetMapping("/project/{projectId}")
	    public String getSprintsByProject(@PathVariable Long projectId,
	                                       Model model) {
	        projectService.findById(projectId).ifPresent(project -> {
	            model.addAttribute("project", project);
	            model.addAttribute("sprints",
	                    sprintService.getSprintsByProject(project));
	        });
	        return "sprints/sprints";
	    }

	    // Show create sprint form
	    @GetMapping("/create/{projectId}")
	    public String showCreateForm(@PathVariable Long projectId,
	                                  Model model) {
	        projectService.findById(projectId).ifPresent(project -> {
	            model.addAttribute("project", project);
	            model.addAttribute("sprint", new Sprint());
	            model.addAttribute("statuses", SprintStatus.values());
	        });
	        return "sprints/create-sprint";
	    }

	    // Handle create sprint form
	    @PostMapping("/create/{projectId}")
	    public String createSprint(@PathVariable Long projectId,
	                                @ModelAttribute Sprint sprint) {
	        projectService.findById(projectId).ifPresent(project -> {
	            sprint.setProject(project);
	            sprint.setStatus(SprintStatus.PLANNED);
	            sprintService.saveSprint(sprint);
	        });
	        return "redirect:/sprints/project/" + projectId + "?success=true";
	    }

	    // Show sprint details
	    @GetMapping("/{id}")
	    public String getSprintDetails(@PathVariable Long id,
	                                    Model model) {
	        sprintService.findById(id).ifPresent(sprint -> {
	            model.addAttribute("sprint", sprint);
	        });
	        return "sprints/sprint-details";
	    }

	    // Update sprint status
	    @PostMapping("/status/{id}")
	    public String updateStatus(@PathVariable Long id,
	                                @RequestParam SprintStatus status) {
	        sprintService.findById(id).ifPresent(sprint -> {
	            sprintService.updateStatus(id, status);
	        });
	        return "redirect:/sprints/" + id;
	    }

	    // Delete sprint
	    @GetMapping("/delete/{id}")
	    public String deleteSprint(@PathVariable Long id) {
	        sprintService.findById(id).ifPresent(sprint -> {
	            Long projectId = sprint.getProject().getId();
	            sprintService.deleteSprint(id);
	        });
	        return "redirect:/projects";
	    }
}
