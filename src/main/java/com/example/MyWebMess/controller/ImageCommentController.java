package com.example.MyWebMess.controller;

import com.example.MyWebMess.model.ImageComment;
import com.example.MyWebMess.service.ImageCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageCommentController {

    private final ImageCommentService imageCommentService;

    @GetMapping("/image/{id}/comment")
    public ResponseEntity<List<ImageComment>> getImageComments(@PathVariable int id) {
        List<ImageComment> list = imageCommentService.getImageComments(id);
        if (null != list) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/image/{id}/comment")
    public ResponseEntity<ImageComment> addImageComment(@PathVariable int id, @RequestPart ImageComment imageComment) {
        ImageComment comment = imageCommentService.addImageComment(id, imageComment);
        if (null != comment) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
