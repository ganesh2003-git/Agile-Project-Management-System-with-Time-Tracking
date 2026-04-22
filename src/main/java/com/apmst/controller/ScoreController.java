package com.apmst.controller;
import com.apmst.entity.User;
import com.apmst.service.EmployeeScoreService;
import com.apmst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
@RequestMapping("/scores")
public class ScoreController {

	  @Autowired
	    private EmployeeScoreService employeeScoreService;

	    @Autowired
	    private UserService userService;

	    // Show leaderboard
	    @GetMapping("/leaderboard")
	    public String getLeaderboard(
	            @RequestParam(required = false) Integer month,
	            @RequestParam(required = false) Integer year,
	            Model model) {

	        // Default to current month and year
	        int currentMonth = month != null ? month :
	                LocalDate.now().getMonthValue();
	        int currentYear = year != null ? year :
	                LocalDate.now().getYear();

	        model.addAttribute("leaderboard",
	                employeeScoreService.getLeaderboard(
	                        currentMonth, currentYear));
	        model.addAttribute("currentMonth", currentMonth);
	        model.addAttribute("currentYear", currentYear);
	        return "score/leaderboard";
	    }

	    // Show my score
	    @GetMapping("/my-score")
	    public String getMyScore(Model model,
	                              Authentication authentication) {
	        String email = authentication.getName();
	        User currentUser = userService.findByEmail(email).orElseThrow();

	        model.addAttribute("scores",
	                employeeScoreService.getScoresByUser(currentUser));
	        model.addAttribute("currentUser", currentUser);
	        model.addAttribute("totalPoints", currentUser.getPoints());
	        return "score/my-score";
	    }
}
