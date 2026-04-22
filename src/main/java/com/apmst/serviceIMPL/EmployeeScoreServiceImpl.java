package com.apmst.serviceIMPL;

import com.apmst.entity.EmployeeScore;
import com.apmst.entity.User;
import com.apmst.repository.EmployeeScoreRepository;
import com.apmst.service.EmployeeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class EmployeeScoreServiceImpl implements EmployeeScoreService {

	 @Autowired
	    private EmployeeScoreRepository employeeScoreRepository;

	    // Points system
	    private static final int POINTS_ON_TIME = 10;
	    private static final int POINTS_LATE = -5;

	    @Override
	    public EmployeeScore getOrCreateScore(User user,
	                                           Integer month,
	                                           Integer year) {
	        return employeeScoreRepository
	                .findByUserAndMonthAndYear(user, month, year)
	                .orElseGet(() -> {
	                    EmployeeScore score = new EmployeeScore();
	                    score.setUser(user);
	                    score.setMonth(month);
	                    score.setYear(year);
	                    score.setTotalPoints(0);
	                    score.setTasksCompletedOnTime(0);
	                    score.setTasksCompletedLate(0);
	                    score.setTasksIncomplete(0);
	                    return employeeScoreRepository.save(score);
	                });
	    }

	    @Override
	    public void awardPointsOnTime(User user) {
	        int month = LocalDate.now().getMonthValue();
	        int year = LocalDate.now().getYear();

	        EmployeeScore score = getOrCreateScore(user, month, year);
	        score.setTasksCompletedOnTime(
	                score.getTasksCompletedOnTime() + 1);
	        score.setTotalPoints(
	                score.getTotalPoints() + POINTS_ON_TIME);

	        // Update user total points
	        user.setPoints(user.getPoints() + POINTS_ON_TIME);
	        employeeScoreRepository.save(score);
	    }

	    @Override
	    public void deductPointsLate(User user) {
	        int month = LocalDate.now().getMonthValue();
	        int year = LocalDate.now().getYear();

	        EmployeeScore score = getOrCreateScore(user, month, year);
	        score.setTasksCompletedLate(
	                score.getTasksCompletedLate() + 1);
	        score.setTotalPoints(
	                score.getTotalPoints() + POINTS_LATE);

	        // Update user total points
	        user.setPoints(user.getPoints() + POINTS_LATE);
	        employeeScoreRepository.save(score);
	    }

	    @Override
	    public Optional<EmployeeScore> getScore(User user,
	                                             Integer month,
	                                             Integer year) {
	        return employeeScoreRepository
	                .findByUserAndMonthAndYear(user, month, year);
	    }

	    @Override
	    public List<EmployeeScore> getScoresByUser(User user) {
	        return employeeScoreRepository.findByUser(user);
	    }

	    @Override
	    public List<EmployeeScore> getLeaderboard(Integer month,
	                                               Integer year) {
	        return employeeScoreRepository
	                .getLeaderboardByMonthAndYear(month, year);
	    }

	    @Override
	    public List<EmployeeScore> getCurrentMonthLeaderboard() {
	        int month = LocalDate.now().getMonthValue();
	        int year = LocalDate.now().getYear();
	        return employeeScoreRepository
	                .getLeaderboardByMonthAndYear(month, year);
	    }
}
