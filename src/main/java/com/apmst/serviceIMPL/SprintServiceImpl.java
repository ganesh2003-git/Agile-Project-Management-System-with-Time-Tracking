package com.apmst.serviceIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apmst.entity.Project;
import com.apmst.entity.Sprint;
import com.apmst.enums.SprintStatus;
import com.apmst.repository.SprintRepository;
import com.apmst.service.SprintService;

@Service

public class SprintServiceImpl implements SprintService {

	 @Autowired
	    private SprintRepository sprintRepository;

	    @Override
	    public Sprint saveSprint(Sprint sprint) {
	        return sprintRepository.save(sprint);
	    }

	    @Override
	    public Optional<Sprint> findById(Long id) {
	        return sprintRepository.findById(id);
	    }

	    @Override
	    public List<Sprint> getAllSprints() {
	        return sprintRepository.findAll();
	    }

	    @Override
	    public List<Sprint> getSprintsByProject(Project project) {
	        return sprintRepository.findByProject(project);
	    }

	    @Override
	    public List<Sprint> getSprintsByStatus(SprintStatus status) {
	        return sprintRepository.findByStatus(status);
	    }

	    @Override
	    public List<Sprint> getSprintsByProjectAndStatus(Project project,
	                                                      SprintStatus status) {
	        return sprintRepository.findByProjectAndStatus(project, status);
	    }

	    @Override
	    public Sprint updateSprint(Sprint sprint) {
	        return sprintRepository.save(sprint);
	    }

	    @Override
	    public void deleteSprint(Long id) {
	        sprintRepository.deleteById(id);
	    }

	    @Override
	    public void updateStatus(Long id, SprintStatus status) {
	        sprintRepository.findById(id).ifPresent(sprint -> {
	            sprint.setStatus(status);
	            sprintRepository.save(sprint);
	        });
	    }

}
