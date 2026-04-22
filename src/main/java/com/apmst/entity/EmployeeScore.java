package com.apmst.entity;

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
@Table(name = "employee_scores")
@Data
public class EmployeeScore {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Employee
	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    // Month (1-12)
	    @Column(nullable = false)
	    private Integer month;

	    // Year
	    @Column(nullable = false)
	    private Integer year;

	    // Tasks completed on time
	    @Column(nullable = false)
	    private Integer tasksCompletedOnTime = 0;

	    // Tasks completed late
	    @Column(nullable = false)
	    private Integer tasksCompletedLate = 0;

	    // Tasks incomplete
	    @Column(nullable = false)
	    private Integer tasksIncomplete = 0;

	    // Total points earned
	    @Column(nullable = false)
	    private Integer totalPoints = 0;

	    @Column(updatable = false)
	    private LocalDateTime createdAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }
}
