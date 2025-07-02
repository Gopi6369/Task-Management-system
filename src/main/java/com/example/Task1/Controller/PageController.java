package com.example.Task1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String frontpagePage() {
        return "frontpage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/create")
    public String createAccountPage() {
        return "createaccount";
    }

    @GetMapping("/employee_tasks.html")
    public String employeePage() {
        return "employee_tasks";
    }

    @GetMapping("/developer")
    public String developerPage() {
        return "developer";
    }

    @GetMapping("/report")
    public String reportpage() {
        return "reportpage";
    }

    @GetMapping("/overallreport")
    public String overallreportPage() {
        return "overallreport";
    }

    @GetMapping("/developerreport")
    public String developerreportPage() {
        return "developerreport";
    }
}

