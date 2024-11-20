package com.example.MyWebMess.repository;

import com.example.MyWebMess.model.ImageComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageCommentRepository extends JpaRepository<ImageComment, Integer> {
    List<ImageComment> findByImageId(int id);
}
