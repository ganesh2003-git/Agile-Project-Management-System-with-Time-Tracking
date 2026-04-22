package com.apmst.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.apmst.entity.Project;
import com.apmst.entity.Task;
import com.apmst.entity.TimeLog;
import com.apmst.entity.User;

public interface TimeLogService {

	// Log time
    TimeLog saveTimeLog(TimeLog timeLog);

    // Find timelog by id
    Optional<TimeLog> findById(Long id);

    // Get all timelogs
    List<TimeLog> getAllTimeLogs();

    // Get timelogs by user
    List<TimeLog> getTimeLogsByUser(User user);

    // Get timelogs by task
    List<TimeLog> getTimeLogsByTask(Task task);

    // Get timelogs by project
    List<TimeLog> getTimeLogsByProject(Project project);

    // Get timelogs by user and date range
    List<TimeLog> getTimeLogsByUserAndDateRange(User user,
                                                LocalDate start,
                                                LocalDate end);

    // Delete timelog
    void deleteTimeLog(Long id);

    // Get total hours by user
    Double getTotalHoursByUser(User user);

    // Get total hours by project
    Double getTotalHoursByProject(Project project);

    // Get total hours by task
    Double getTotalHoursByTask(Task task);
}
