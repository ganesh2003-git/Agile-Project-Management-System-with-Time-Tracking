package com.apmst.service;

import java.util.List;
import java.util.Optional;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.entity.Task;
import com.apmst.entity.User;
import com.apmst.enums.TaskStatus;

import jakarta.transaction.Transactional;

public interface TaskService {

	 // Create task
    Task saveTask(Task task);

    // Find task by id
    Optional<Task> findById(Long id);

    // Get all tasks
    List<Task> getAllTasks();

    // Get tasks by assignee
    List<Task> getTasksByAssignee(User assignee);

    // Get tasks by project
    List<Task> getTasksByProject(Project project);

    // Get tasks by sprint
    List<Task> getTasksBySprint(Sprint sprint);

    // Get tasks by status
    List<Task> getTasksByStatus(TaskStatus status);

    // Get tasks by assignee and status
    List<Task> getTasksByAssigneeAndStatus(User assignee,
                                            TaskStatus status);

    // Update task
    Task updateTask(Task task);

    // Delete task
    void deleteTask(Long id);

    // Update task status
    void updateStatus(Long id, TaskStatus status);

    // Start timer
    void startTimer(Long id);

    // Stop timer
    void stopTimer(Long id);

    // Count tasks by project and status
    Long countByProjectAndStatus(Project project, TaskStatus status);
    
 // Get tasks by project and status
    List<Task> getTasksByProjectAndStatus(Project project, TaskStatus status);
    
    
}
