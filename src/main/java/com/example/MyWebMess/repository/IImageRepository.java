package com.example.MyWebMess.repository;

import com.example.MyWebMess.model.Image;
import com.example.MyWebMess.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByUser(Users user);
    List<Image> findByHidden(boolean hidden);
}
