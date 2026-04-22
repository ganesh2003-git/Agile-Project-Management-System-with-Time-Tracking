package com.apmst.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "timelogs")
@Data
public class TimeLog {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Hours logged
	    @Column(nullable = false)
	    private Double hoursLogged;

	    // Date of work
	    @Column(nullable = false)
	    private LocalDate workDate;

	    // Description of work done
	    @Column(length = 1000)
	    private String workDescription;

	    // TimeLog belongs to a task
	    @ManyToOne
	    @JoinColumn(name = "task_id", nullable = false)
	    private Task task;

	    // TimeLog belongs to a user
	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    // TimeLog belongs to a project
	    @ManyToOne
	    @JoinColumn(name = "project_id", nullable = false)
	    private Project project;

	    @Column(updatable = false)
	    private LocalDateTime createdAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
}
}
