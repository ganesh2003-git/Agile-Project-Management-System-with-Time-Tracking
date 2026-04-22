package com.apmst.service;

import java.util.List;
import java.util.Optional;

import com.apmst.entity.Project;
import com.apmst.entity.User;
import com.apmst.enums.ProjectStatus;

public interface ProjectService {

	// Create new project
    Project saveProject(Project project);

    // Find project by id
    Optional<Project> findById(Long id);

    // Get all projects
    List<Project> getAllProjects();

    // Get projects by manager
    List<Project> getProjectsByManager(User manager);

    // Get projects by member
    List<Project> getProjectsByMember(User member);

    // Get projects for a user (manager or member)
    List<Project> getProjectsForUser(User user);

    // Update project
    Project updateProject(Project project);

    // Delete project
    void deleteProject(Long id);

    // Update project status
    void updateStatus(Long id, ProjectStatus status);

    // Add member to project
    void addMember(Long projectId, User user);

    // Remove member from project
    void removeMember(Long projectId, User user);
}
