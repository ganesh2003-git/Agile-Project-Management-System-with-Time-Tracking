package com.apmst.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.apmst.enums.TaskPriority;
import com.apmst.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String title;

	    @Column(length = 2000)
	    private String description;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private TaskStatus status = TaskStatus.BACKLOG;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private TaskPriority priority = TaskPriority.MEDIUM;

	    // Story points for agile planning
	    private Integer storyPoints;

	    // Estimated hours
	    private Double estimatedHours;

	    // Actual hours spent
	    private Double actualHours = 0.0;

	    // Due date
	    private LocalDate dueDate;

	    // Document attachment path
	    private String attachmentPath;

	    // Task belongs to a sprint
	    @ManyToOne
	    @JoinColumn(name = "sprint_id")
	    private Sprint sprint;

	    // Task belongs to a project
	    @ManyToOne
	    @JoinColumn(name = "project_id", nullable = false)
	    private Project project;

	    // Task assigned to a user
	    @ManyToOne
	    @JoinColumn(name = "assignee_id")
	    private User assignee;

	    // Task created by a user
	    @ManyToOne
	    @JoinColumn(name = "created_by_id")
	    private User createdBy;

	    // Timer fields
	    private LocalDateTime timerStartTime;
	    private Boolean timerRunning = false;

	    @Column(updatable = false)
	    private LocalDateTime createdAt;
	    
	 // GitHub commit URL
	    private String githubUrl;
	    
	    //this is for task feedback
	    @Column(length = 1000) // Give it enough space for long messages
	    private String lastFeedback;
	    
	    public void setLastFeedback(String lastFeedback) {
	        this.lastFeedback = lastFeedback;
	    }

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }
}
