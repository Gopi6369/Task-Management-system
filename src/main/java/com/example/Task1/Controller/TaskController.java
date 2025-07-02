package com.example.Task1.Controller;

import com.example.Task1.Entity.Task;
import com.example.Task1.Entity.User;
import com.example.Task1.Repository.TaskRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // ✅ GET all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ✅ POST create task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setDate(LocalDate.now().toString());
        return taskRepository.save(task);
    }

    // ✅ GET tasks by employee name
    @GetMapping("/by-employee/{name}")
    public List<Task> getTasksByEmployee(@PathVariable String name) {
        return taskRepository.findByEmployee(name);
    }

    // ✅ PUT update status
    @PutMapping("/update-status/{id}")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setStatus(body.get("status"));
            taskRepository.save(task);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ DELETE task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    // ✅ GET tasks assigned to session employee
    @GetMapping("/mytasks")
    public List<Task> getMyTasks(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "Employee".equals(user.getRole())) {
            return taskRepository.findByEmployee(user.getName());
        }
        return new ArrayList<>();
    }

    // ✅ PUT reassign task
    @PutMapping("/reassign/{id}")
    public ResponseEntity<Void> reassignTask(@PathVariable Long id,
                                             @RequestBody Map<String, String> data,
                                             HttpSession session) {
        Task task = taskRepository.findById(id).orElse(null);
        User currentUser = (User) session.getAttribute("user");

        if (task != null && currentUser != null) {
            task.setReassignedFrom(currentUser.getName());
            task.setReassignedTo(data.get("reassignedTo"));
            task.setEmployee(data.get("reassignedTo"));
            task.setStatus(data.get("status"));
            task.setFeedback(data.get("message"));
            taskRepository.save(task);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ GET last reassigned task
    @GetMapping("/last-reassigned")
    public ResponseEntity<Task> getLastReassignedTask() {
        List<Task> reassignedTasks = taskRepository.findByReassignedFromIsNotNullAndReassignedToIsNotNullOrderByIdDesc();
        if (!reassignedTasks.isEmpty()) {
            return ResponseEntity.ok(reassignedTasks.get(0));
        }
        return ResponseEntity.noContent().build();
    }

    // ✅ Developer report
    @GetMapping("/developer-report")
    public List<Map<String, Object>> getDeveloperReport() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream().map(task -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", task.getEmployee());
            map.put("taskTitle", task.getTitle());
            map.put("status", task.getStatus());
            map.put("deadline", task.getDeadline());
            map.put("remarks", task.getFeedback());
            map.put("rating", task.getRating());
            return map;
        }).collect(Collectors.toList());
    }
}
