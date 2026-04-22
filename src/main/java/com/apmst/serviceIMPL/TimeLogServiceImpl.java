package com.apmst.serviceIMPL;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apmst.entity.Project;
import com.apmst.entity.Task;
import com.apmst.entity.TimeLog;
import com.apmst.entity.User;
import com.apmst.repository.TimeLogRepository;
import com.apmst.service.TimeLogService;

@Service
public class TimeLogServiceImpl implements TimeLogService {

	 @Autowired
	    private TimeLogRepository timeLogRepository;

	    @Override
	    public TimeLog saveTimeLog(TimeLog timeLog) {
	        return timeLogRepository.save(timeLog);
	    }

	    @Override
	    public Optional<TimeLog> findById(Long id) {
	        return timeLogRepository.findById(id);
	    }

	    @Override
	    public List<TimeLog> getAllTimeLogs() {
	        return timeLogRepository.findAll();
	    }

	    @Override
	    public List<TimeLog> getTimeLogsByUser(User user) {
	        return timeLogRepository.findByUser(user);
	    }

	    @Override
	    public List<TimeLog> getTimeLogsByTask(Task task) {
	        return timeLogRepository.findByTask(task);
	    }

	    @Override
	    public List<TimeLog> getTimeLogsByProject(Project project) {
	        return timeLogRepository.findByProject(project);
	    }

	    @Override
	    public List<TimeLog> getTimeLogsByUserAndDateRange(User user,
	                                                        LocalDate start,
	                                                        LocalDate end) {
	        return timeLogRepository
	                .findByUserAndWorkDateBetween(user, start, end);
	    }

	    @Override
	    public void deleteTimeLog(Long id) {
	        timeLogRepository.deleteById(id);
	    }

	    @Override
	    public Double getTotalHoursByUser(User user) {
	        Double total = timeLogRepository.getTotalHoursByUser(user);
	        return total != null ? total : 0.0;
	    }

	    @Override
	    public Double getTotalHoursByProject(Project project) {
	        Double total = timeLogRepository.getTotalHoursByProject(project);
	        return total != null ? total : 0.0;
	    }

	    @Override
	    public Double getTotalHoursByTask(Task task) {
	        Double total = timeLogRepository.getTotalHoursByTask(task);
	        return total != null ? total : 0.0;
	    }
}
