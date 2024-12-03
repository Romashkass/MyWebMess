package com.example.MyWebMess.repository;

import com.example.MyWebMess.model.Picture;
import com.example.MyWebMess.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPictureRepository extends JpaRepository<Picture, Integer> {
    List<Picture> findByUser(Users user);
    List<Picture> findByHidden(boolean hidden);
    List<Picture> findByUserUsernameAndHidden(String username, boolean hidden);
}
