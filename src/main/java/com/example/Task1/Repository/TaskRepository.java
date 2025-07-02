package com.example.Task1.Repository;


import java.util.List;
import com.example.Task1.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // No extra methods needed for basic CRUD
	List<Task> findByEmployee(String employee);
	List<Task> findByReassignedTo(String reassignedTo);
	List<Task> findByReassignedFromIsNotNullAndReassignedToIsNotNullOrderByIdDesc();


}



