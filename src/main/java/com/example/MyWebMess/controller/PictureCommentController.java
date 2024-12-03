package com.example.MyWebMess.controller;

import com.example.MyWebMess.dto.PictureCommentDto;
import com.example.MyWebMess.model.PictureComment;
import com.example.MyWebMess.service.PictureCommentService;
import com.example.MyWebMess.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture/{id}")
public class PictureCommentController {

    private final PictureCommentService pictureCommentService;
    private final MappingUtils mappingUtils;

    @GetMapping("/comment")
    public ResponseEntity<List<PictureCommentDto>> getPictureComments(@PathVariable int id) {
        List<PictureComment> list = pictureCommentService.getPictureComments(id);
        return new ResponseEntity<>(mappingUtils.mapToPictureCommentDto(list), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<PictureCommentDto> addPictureComment(@PathVariable int id, @RequestBody PictureCommentDto pictureComment) {
        PictureComment comment = pictureCommentService.addPictureComment(id, mappingUtils.mapToPictureComment(pictureComment));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
