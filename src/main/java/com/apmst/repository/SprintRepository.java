package com.apmst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.enums.SprintStatus;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
	 // Find sprints by project
    List<Sprint> findByProject(Project project);

    // Find sprints by status
    List<Sprint> findByStatus(SprintStatus status);

    // Find sprints by project and status
    List<Sprint> findByProjectAndStatus(Project project, 
                                        SprintStatus status);
}
