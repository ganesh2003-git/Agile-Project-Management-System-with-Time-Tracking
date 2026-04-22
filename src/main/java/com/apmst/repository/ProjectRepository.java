package com.apmst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apmst.entity.Project;
import com.apmst.entity.User;
import com.apmst.enums.ProjectStatus;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Find projects by manager
    List<Project> findByManager(User manager);

    // Find projects by status
    List<Project> findByStatus(ProjectStatus status);

    // Find projects where user is a member
    List<Project> findByMembersContaining(User user);

    // Find projects by manager or member
    List<Project> findByManagerOrMembersContaining(User manager, User member);
}
