package com.apmst.serviceIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apmst.entity.Project;
import com.apmst.entity.User;
import com.apmst.enums.ProjectStatus;
import com.apmst.repository.ProjectRepository;
import com.apmst.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	 @Autowired
	    private ProjectRepository projectRepository;

	    public Project saveProject(Project project) {
	        return projectRepository.save(project);
	    }

	    @Override
	    public Optional<Project> findById(Long id) {
	        return projectRepository.findById(id);
	    }

	    @Override
	    public List<Project> getAllProjects() {
	        return projectRepository.findAll();
	    }

	    @Override
	    public List<Project> getProjectsByManager(User manager) {
	        return projectRepository.findByManager(manager);
	    }

	    @Override
	    public List<Project> getProjectsByMember(User member) {
	        return projectRepository.findByMembersContaining(member);
	    }

	    @Override
	    public List<Project> getProjectsForUser(User user) {
	        return projectRepository
	                .findByManagerOrMembersContaining(user, user);
	    }

	    @Override
	    public Project updateProject(Project project) {
	        return projectRepository.save(project);
	    }

	    @Override
	    public void deleteProject(Long id) {
	        projectRepository.deleteById(id);
	    }

	    @Override
	    public void updateStatus(Long id, ProjectStatus status) {
	        projectRepository.findById(id).ifPresent(project -> {
	            project.setStatus(status);
	            projectRepository.save(project);
	        });
	    }

	    @Override
	    public void addMember(Long projectId, User user) {
	        projectRepository.findById(projectId).ifPresent(project -> {
	            project.getMembers().add(user);
	            projectRepository.save(project);
	        });
	    }

	    @Override
	    public void removeMember(Long projectId, User user) {
	        projectRepository.findById(projectId).ifPresent(project -> {
	            project.getMembers().remove(user);
	            projectRepository.save(project);
	        });
	    }
}
