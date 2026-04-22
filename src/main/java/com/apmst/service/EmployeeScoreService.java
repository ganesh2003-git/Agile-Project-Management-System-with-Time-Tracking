package com.apmst.service;

import java.util.List;
import java.util.Optional;

import com.apmst.entity.EmployeeScore;
import com.apmst.entity.User;

public interface EmployeeScoreService {

	  // Get or create score for user this month
    EmployeeScore getOrCreateScore(User user,
                                    Integer month,
                                    Integer year);

    // Award points for completing task on time
    void awardPointsOnTime(User user);

    // Deduct points for missing deadline
    void deductPointsLate(User user);

    // Get score by user month and year
    Optional<EmployeeScore> getScore(User user,
                                      Integer month,
                                      Integer year);

    // Get all scores by user
    List<EmployeeScore> getScoresByUser(User user);

    // Get leaderboard for month and year
    List<EmployeeScore> getLeaderboard(Integer month, Integer year);

    // Get current month leaderboard
    List<EmployeeScore> getCurrentMonthLeaderboard();
}
