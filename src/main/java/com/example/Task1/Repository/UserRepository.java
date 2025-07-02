package com.example.Task1.Repository;

import com.example.Task1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    User findByNameAndPassword(String name, String password);
    List<User> findByRole(String role);
}
