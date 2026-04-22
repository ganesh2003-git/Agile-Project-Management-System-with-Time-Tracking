package com.apmst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.entity.Task;
import com.apmst.entity.User;
import com.apmst.enums.TaskPriority;
import com.apmst.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long>{

	 // Find tasks by assignee
    List<Task> findByAssignee(User assignee);

    // Find tasks by project
    List<Task> findByProject(Project project);

    // Find tasks by sprint
    List<Task> findBySprint(Sprint sprint);

    // Find tasks by status
    List<Task> findByStatus(TaskStatus status);

    // Find tasks by priority
    List<Task> findByPriority(TaskPriority priority);

    // Find tasks by assignee and status
    List<Task> findByAssigneeAndStatus(User assignee, TaskStatus status);

    // Find tasks by project and status
    List<Task> findByProjectAndStatus(Project project, TaskStatus status);

    // Find tasks by sprint and status
    List<Task> findBySprintAndStatus(Sprint sprint, TaskStatus status);

    // Count tasks by project and status
    Long countByProjectAndStatus(Project project, TaskStatus status);
}
