package com.apmst.serviceIMPL;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.entity.Task;
import com.apmst.entity.User;
import com.apmst.enums.TaskStatus;
import com.apmst.repository.TaskRepository;
import com.apmst.service.TaskService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	 @Autowired
	    private TaskRepository taskRepository;

	    @Override
	    public Task saveTask(Task task) {
	        return taskRepository.save(task);
	    }

	    @Override
	    public Optional<Task> findById(Long id) {
	        return taskRepository.findById(id);
	    }

	    @Override
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	    }

	    @Override
	    public List<Task> getTasksByAssignee(User assignee) {
	        return taskRepository.findByAssignee(assignee);
	    }

	    @Override
	    public List<Task> getTasksByProject(Project project) {
	        return taskRepository.findByProject(project);
	    }

	    @Override
	    public List<Task> getTasksBySprint(Sprint sprint) {
	        return taskRepository.findBySprint(sprint);
	    }

	    @Override
	    public List<Task> getTasksByStatus(TaskStatus status) {
	        return taskRepository.findByStatus(status);
	    }

	    @Override
	    public List<Task> getTasksByAssigneeAndStatus(User assignee,
	                                                   TaskStatus status) {
	        return taskRepository.findByAssigneeAndStatus(assignee, status);
	    }

	    @Override
	    public Task updateTask(Task task) {
	        return taskRepository.save(task);
	    }

	    @Override
	    public void deleteTask(Long id) {
	        taskRepository.deleteById(id);
	    }

	    @Override
	    public void updateStatus(Long id, TaskStatus status) {
	        taskRepository.findById(id).ifPresent(task -> {
	            task.setStatus(status);
	            taskRepository.save(task);
	        });
	    }

	    @Override
	    public void startTimer(Long id) {
	        taskRepository.findById(id).ifPresent(task -> {
	            task.setTimerStartTime(LocalDateTime.now());
	            task.setTimerRunning(true);
	            task.setStatus(TaskStatus.IN_PROGRESS);
	            taskRepository.save(task);
	        });
	    }

	    @Override
	    public void stopTimer(Long id) {
	        taskRepository.findById(id).ifPresent(task -> {
	            if (task.getTimerRunning() && 
	                task.getTimerStartTime() != null) {

	                // Calculate hours spent
	                Duration duration = Duration.between(
	                        task.getTimerStartTime(),
	                        LocalDateTime.now());
	                double hoursSpent = duration.toMinutes() / 60.0;

	                // Add to actual hours
	                double currentHours = task.getActualHours() != null 
	                        ? task.getActualHours() : 0.0;
	                task.setActualHours(currentHours + hoursSpent);
	                task.setTimerRunning(false);
	                task.setTimerStartTime(null);
	                taskRepository.save(task);
	            }
	        });
	    }

	    @Override
	    public Long countByProjectAndStatus(Project project, 
	                                         TaskStatus status) {
	        return taskRepository.countByProjectAndStatus(project, status);
	    }
	    
	    @Override
	    public List<Task> getTasksByProjectAndStatus(Project project,
	                                                  TaskStatus status) {
	        return taskRepository.findByProjectAndStatus(project, status);
	    }
}
