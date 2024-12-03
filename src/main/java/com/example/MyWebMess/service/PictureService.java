package com.example.MyWebMess.service;

import com.example.MyWebMess.exception.FileException;
import com.example.MyWebMess.exception.ForbiddenException;
import com.example.MyWebMess.exception.NotFoundException;
import com.example.MyWebMess.model.Picture;
import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.model.UsersPrincipal;
import com.example.MyWebMess.repository.IPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final IPictureRepository iPictureRepository;
    private final String imagesFolder = "G:\\Projects\\Java\\MyWebMess\\.images\\"; // TODO: перенести в .properties

    public List<Picture> getUserPicturesInfo() {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Picture> pictures = iPictureRepository.findByUser(user);
        return pictures;
    }

    public List<Picture> getProfilePicturesInfo(String username) {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (user.getUsername().equals(username)) {
            return iPictureRepository.findByUser(user);
        } else {
            return iPictureRepository.findByUserUsernameAndHidden(username, false);
        }
    }

    public List<Picture> getPublicPicturesInfo() {
        List<Picture> pictures = iPictureRepository.findByHidden(false);
        return pictures;
    }

    public Picture getPictureInfoById(int id) {
        Picture picture = iPictureRepository.findById(id).orElseThrow(() -> new NotFoundException("Picture not found"));
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (picture.isHidden() && !picture.getUser().equals(user)) {
            throw new ForbiddenException("Hidden picture");
        }

        return picture;

    }

    public byte[] getPictureImageById(int id, StringBuilder type) {
        Picture picture = iPictureRepository.findById(id).orElseThrow(() -> new NotFoundException("Picture not found"));
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (picture.isHidden() && !picture.getUser().equals(user)) {
            throw new ForbiddenException("Hidden picture");
        }

        try {
            type.append(picture.getType());
            return Files.readAllBytes(Paths.get(picture.getPath()));
        } catch (IOException exception) {
            throw new FileException("Error while reading file");
        }
    }

    public Picture addPicture(Picture picture, MultipartFile imageFile) {
        Users user = ((UsersPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        String imagePath = imagesFolder + user.getUsername() + "\\" + imageFile.getOriginalFilename();

        picture.setUser(user);
        picture.setPath(imagePath);
        picture.setType(imageFile.getContentType());
        picture.setName(imageFile.getOriginalFilename());

        try {
            File file = new File(imagePath);
            file.mkdirs();
            imageFile.transferTo(file);

            return iPictureRepository.save(picture);
        } catch (IOException exception) {
            throw new FileException("Error while writing file");
        }
    }

    public Picture updatePictureInfo(int id, Picture picture) {
        Users user = ((UsersPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Picture pictureSaved = iPictureRepository.findById(id).orElseThrow(() -> new NotFoundException("Picture not found"));

        if (!pictureSaved.getUser().equals(user)) {
            throw new ForbiddenException("You not the owner");
        }

        picture.setId(id);
        picture.setUser(user);
        picture.setPath(pictureSaved.getPath());
        picture.setType(pictureSaved.getType());

        return iPictureRepository.save(picture);
    }

    public boolean deletePicture(int id) {
        Users user = ((UsersPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Picture picture = iPictureRepository.findById(id).orElseThrow(() -> new NotFoundException("Picture not found"));
        if (!picture.getUser().equals(user)) {
            throw new ForbiddenException("You not the owner");
        }

        File file = new File(picture.getPath());
        boolean deleted = file.delete();
        if (!deleted) {
            return false;
        }

        iPictureRepository.deleteById(id);
        return true;
    }
}
