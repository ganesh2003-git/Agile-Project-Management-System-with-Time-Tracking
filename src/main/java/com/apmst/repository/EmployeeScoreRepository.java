package com.apmst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apmst.entity.EmployeeScore;
import com.apmst.entity.User;

@Repository
public interface EmployeeScoreRepository extends JpaRepository<EmployeeScore, Long>{

    // Find score by user, month and year
    Optional<EmployeeScore> findByUserAndMonthAndYear(
            User user, Integer month, Integer year);

    // Find all scores by user
    List<EmployeeScore> findByUser(User user);

    // Find all scores by month and year
    List<EmployeeScore> findByMonthAndYear(
            Integer month, Integer year);

    // Get top performers by month and year
    @Query("SELECT e FROM EmployeeScore e " +
           "WHERE e.month = ?1 AND e.year = ?2 " +
           "ORDER BY e.totalPoints DESC")
    List<EmployeeScore> getLeaderboardByMonthAndYear(
            Integer month, Integer year);
}
