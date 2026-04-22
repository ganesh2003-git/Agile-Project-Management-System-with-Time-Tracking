package com.apmst.controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController{
  
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "error/403"; // Your Access Denied page
            } else if (statusCode == 404) {
                return "error/404"; // Page for "URL not found"
            } else if (statusCode == 500) {
                return "error/500"; // Page for "Server crashed"
            }
        }
        return "error/generic"; // A general error page
    }
}
	

