package com.example.MyWebMess.repository;

import com.example.MyWebMess.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
