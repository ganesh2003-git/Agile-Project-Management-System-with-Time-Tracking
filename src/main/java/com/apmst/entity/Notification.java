package com.apmst.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Message
	    @Column(nullable = false)
	    private String message;

	    // Is read
	    @Column(nullable = false)
	    private boolean isRead = false;

	    // Type of notification
	    @Column(nullable = false)
	    private String type;

	    // Link to redirect on click
	    private String link;

	    // Notification belongs to a user
	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @Column(updatable = false)
	    private LocalDateTime createdAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }
}
