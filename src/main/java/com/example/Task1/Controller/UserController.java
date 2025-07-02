package com.example.Task1.Controller;

import java.util.List;

import com.example.Task1.Entity.User;
import com.example.Task1.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Create User (Signup)
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam String name,
                                                @RequestParam String password,
                                                @RequestParam(required = false) String email) {
        if (userRepository.findByName(name) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setRole("Employee"); // default role
        if (email != null) {
            newUser.setEmail(email);
        }
        userRepository.save(newUser);
        return ResponseEntity.ok("Account created successfully");
    }

    // ✅ Login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userRepository.findByNameAndPassword(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            if (username.toLowerCase().startsWith("admin")) {
                return "redirect:/admin";
            } else {
                return "redirect:/developer";
            }
        }
        return "redirect:/login?error=true";
    }

    // ✅ API: Get current session user
    @GetMapping("/api/current-user")
    @ResponseBody
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // ✅ API: Get all employees
    @GetMapping("/api/employees")
    @ResponseBody
    public List<User> getAllEmployees() {
        return userRepository.findByRole("Employee");
    }

    // ✅ API: Get employee data from session
    @GetMapping("/employee/info")
    @ResponseBody
    public ResponseEntity<User> getEmployeeFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "Employee".equals(user.getRole())) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // ✅ Logout and remove session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

