package com.example.mobile_app.users.repository;

import com.example.mobile_app.users.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
