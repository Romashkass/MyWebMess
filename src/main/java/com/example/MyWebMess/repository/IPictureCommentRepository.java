package com.example.MyWebMess.repository;

import com.example.MyWebMess.model.PictureComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPictureCommentRepository extends JpaRepository<PictureComment, Integer> {
    List<PictureComment> findByPictureId(int id);
}
