package com.apmst.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apmst.entity.Project;
import com.apmst.entity.Task;
import com.apmst.entity.TimeLog;
import com.apmst.entity.User;
@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {

	 // Find timelogs by user
    List<TimeLog> findByUser(User user);

    // Find timelogs by task
    List<TimeLog> findByTask(Task task);

    // Find timelogs by project
    List<TimeLog> findByProject(Project project);

    // Find timelogs by user and date range
    List<TimeLog> findByUserAndWorkDateBetween(User user,
                                               LocalDate start,
                                               LocalDate end);

    // Find timelogs by project and user
    List<TimeLog> findByProjectAndUser(Project project, User user);

    // Total hours by user
    @Query("SELECT SUM(t.hoursLogged) FROM TimeLog t WHERE t.user = ?1")
    Double getTotalHoursByUser(User user);

    // Total hours by project
    @Query("SELECT SUM(t.hoursLogged) FROM TimeLog t WHERE t.project = ?1")
    Double getTotalHoursByProject(Project project);

    // Total hours by task
    @Query("SELECT SUM(t.hoursLogged) FROM TimeLog t WHERE t.task = ?1")
    Double getTotalHoursByTask(Task task);
}
