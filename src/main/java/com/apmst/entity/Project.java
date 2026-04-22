package com.apmst.entity;
import com.apmst.enums.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="projects")
@Data
public class Project {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(length = 1000)
	    private String description;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private ProjectStatus status = ProjectStatus.ACTIVE;

	    @Column(nullable = false)
	    private LocalDate startDate;

	    @Column(nullable = false)
	    private LocalDate endDate;

	    // Manager who created the project
	    @ManyToOne
	    @JoinColumn(name = "manager_id")
	    private User manager;

	    // Team members on this project
	    @ManyToMany
	    @JoinTable(
	        name = "project_members",
	        joinColumns = @JoinColumn(name = "project_id"),
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    
	    private List<User> members;
	    
	    @jakarta.persistence.OneToMany(mappedBy = "project")
	    private List<Task> tasks;

	    @Column(updatable = false)
	    private LocalDateTime createdAt;
	    
	 // Local project path on employee's machine
	    private String localProjectPath;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }

}
