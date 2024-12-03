package com.example.MyWebMess.controller;

import com.example.MyWebMess.dto.PictureDto;
import com.example.MyWebMess.model.Picture;
import com.example.MyWebMess.service.PictureService;
import com.example.MyWebMess.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/picture")
public class PictureController {

    private final PictureService pictureService;
    private final MappingUtils mappingUtils;

    @GetMapping("")
    public ResponseEntity<List<PictureDto>> getUserPicturesInfo() {
        List<Picture> info = pictureService.getUserPicturesInfo();
        return new ResponseEntity<>(mappingUtils.mapToPictureDto(info), HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<List<PictureDto>> getProfilePicturesInfo(@PathVariable String username) {
        List<Picture> info = pictureService.getProfilePicturesInfo(username);
        return new ResponseEntity<>(mappingUtils.mapToPictureDto(info), HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<PictureDto>> getPublicPicturesInfo() {
        List<Picture> info = pictureService.getPublicPicturesInfo();
        return new ResponseEntity<>(mappingUtils.mapToPictureDto(info), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureDto> getPictureInfoById(@PathVariable int id) {
        Picture picture = pictureService.getPictureInfoById(id);
        return new ResponseEntity<>(mappingUtils.mapToPictureDto(picture), HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getPictureImageById(@PathVariable int id) {
        StringBuilder type = new StringBuilder();
        byte[] file = pictureService.getPictureImageById(id, type);
        return ResponseEntity.ok().contentType(MediaType.valueOf(type.toString())).body(file);
    }

    @PostMapping("")
    public ResponseEntity<?> addPicture(@RequestPart PictureDto picture, @RequestPart MultipartFile imageFile) {
        Picture result = pictureService.addPicture(mappingUtils.mapToPicture(picture), imageFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PictureDto> updatePictureInfo(@PathVariable int id, @RequestBody PictureDto picture) {
        Picture result = pictureService.updatePictureInfo(id, mappingUtils.mapToPicture(picture));
        return new ResponseEntity<>(mappingUtils.mapToPictureDto(result), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePicture(@PathVariable int id) {
        boolean result = pictureService.deletePicture(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
