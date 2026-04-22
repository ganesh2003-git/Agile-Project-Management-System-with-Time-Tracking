package com.apmst.service;

import java.util.List;
import java.util.Optional;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.enums.SprintStatus;

public interface SprintService {

	 // Create new sprint
    Sprint saveSprint(Sprint sprint);

    // Find sprint by id
    Optional<Sprint> findById(Long id);

    // Get all sprints
    List<Sprint> getAllSprints();

    // Get sprints by project
    List<Sprint> getSprintsByProject(Project project);

    // Get sprints by status
    List<Sprint> getSprintsByStatus(SprintStatus status);

    // Get sprints by project and status
    List<Sprint> getSprintsByProjectAndStatus(Project project,
                                               SprintStatus status);

    // Update sprint
    Sprint updateSprint(Sprint sprint);

    // Delete sprint
    void deleteSprint(Long id);

    // Update sprint status
    void updateStatus(Long id, SprintStatus status);
}
