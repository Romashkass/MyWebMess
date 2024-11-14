package com.example.MyWebMess.controller;

import com.example.MyWebMess.model.Image;
import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.model.UsersPrincipal;
import com.example.MyWebMess.service.ImageService;
import com.example.MyWebMess.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<List<Image>> getUserImagesInfo() {
        List<Image> info = imageService.getUserImagesInfo();
        if (!info.isEmpty()) {
            return new ResponseEntity<>(info, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/image/public")
    public ResponseEntity<List<Image>> getPublicImagesInfo() {
        List<Image> info = imageService.getPublicImagesInfo();
        if (!info.isEmpty()) {
            return new ResponseEntity<>(info, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Image> getImageInfoById(@PathVariable int id) {
        Image image = imageService.getImageInfoById(id);

        if (null != image) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/image/{id}/bytes")
    public ResponseEntity<byte[]> getImageById(@PathVariable int id) {
        StringBuilder type = new StringBuilder();
        byte[] file = imageService.getImageById(id, type);

        if (null != file) {
            return ResponseEntity.ok().contentType(MediaType.valueOf(type.toString())).body(file);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/image")
    public ResponseEntity<?> addImage(@RequestPart Image image, @RequestPart MultipartFile imageFile) {
        Image result = imageService.addImage(image, imageFile);
        if (null != result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/image/{id}/update")
    public ResponseEntity<?> updateImage(@PathVariable int id, @RequestPart Image image) {
        Image result = imageService.updateImage(id, image);
        if (null != result) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable int id) {
        boolean result = imageService.deleteImage(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
