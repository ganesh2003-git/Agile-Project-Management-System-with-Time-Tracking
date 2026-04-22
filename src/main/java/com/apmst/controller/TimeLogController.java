package com.apmst.controller;

import com.apmst.entity.TimeLog;
import com.apmst.entity.User;
import com.apmst.service.ProjectService;
import com.apmst.service.TaskService;
import com.apmst.service.TimeLogService;
import com.apmst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
@Controller
@RequestMapping("/timelog")
public class TimeLogController {

	 @Autowired
	    private TimeLogService timeLogService;

	    @Autowired
	    private TaskService taskService;

	    @Autowired
	    private ProjectService projectService;

	    @Autowired
	    private UserService userService;

	    // Show my timelogs
	    @GetMapping
	    public String getMyTimeLogs(Model model,
	                                 Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        model.addAttribute("timelogs",
	                timeLogService.getTimeLogsByUser(currentUser));
	        model.addAttribute("totalHours",
	                timeLogService.getTotalHoursByUser(currentUser));
	        model.addAttribute("currentUser", currentUser);
	        return "timelog/timelog";
	    }

	    // Show log time form for a task
	    @GetMapping("/log/{taskId}")
	    public String showLogForm(@PathVariable Long taskId,
	                               Model model,
	                               Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        taskService.findById(taskId).ifPresent(task -> {
	            model.addAttribute("task", task);
	            model.addAttribute("timeLog", new TimeLog());
	            model.addAttribute("currentUser", currentUser);
	        });
	        return "timelog/log-time";
	    }

	    // Handle log time form
	    @PostMapping("/log/{taskId}")
	    public String logTime(@PathVariable Long taskId,
	                           @ModelAttribute TimeLog timeLog,
	                           Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        taskService.findById(taskId).ifPresent(task -> {
	            timeLog.setTask(task);
	            timeLog.setUser(currentUser);
	            timeLog.setProject(task.getProject());
	            timeLog.setWorkDate(LocalDate.now());

	            // Update actual hours on task
	            double currentHours = task.getActualHours() != null
	                    ? task.getActualHours() : 0.0;
	            task.setActualHours(currentHours + timeLog.getHoursLogged());
	            taskService.updateTask(task);

	            timeLogService.saveTimeLog(timeLog);
	        });

	        return "redirect:/timelog?success=true";
	    }

	    // Show project timelog report
	    @GetMapping("/project/{projectId}")
	    public String getProjectTimeLogs(@PathVariable Long projectId,
	                                      Model model) {
	        projectService.findById(projectId).ifPresent(project -> {
	            model.addAttribute("project", project);
	            model.addAttribute("timelogs",
	                    timeLogService.getTimeLogsByProject(project));
	            model.addAttribute("totalHours",
	                    timeLogService.getTotalHoursByProject(project));
	        });
	        return "timelog/project-timelog";
	    }

	    // Delete timelog
	    @GetMapping("/delete/{id}")
	    public String deleteTimeLog(@PathVariable Long id) {
	        timeLogService.deleteTimeLog(id);
	        return "redirect:/timelog";
	    }
}
