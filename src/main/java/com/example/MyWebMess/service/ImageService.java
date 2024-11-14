package com.example.MyWebMess.service;

import com.example.MyWebMess.model.Image;
import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.model.UsersPrincipal;
import com.example.MyWebMess.repository.IImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final IImageRepository iImageRepository;
    private final String imagesFolder = "G:\\Projects\\Java\\MyWebMess\\.images\\"; // TODO: перенести в .properties

    public List<Image> getUserImagesInfo() {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Image> images = iImageRepository.findByUser(user);
        for (Image image: images) {
            image.setPath(null);
            image.setUser(null);
        }
        return images;
    }

    public List<Image> getPublicImagesInfo() {
        List<Image> images = iImageRepository.findByHidden(false);
        for (Image image: images) {
            image.setPath(null);
            image.getUser().setPassword(null);
        }
        return images;
    }

    public Image getImageInfoById(int id) {
        Image image = iImageRepository.findById(id).orElse(null);
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (null == image || (image.isHidden() && !image.getUser().equals(user))) {

            return null;
        }

        image.setPath(null);
        image.getUser().setPassword(null);

        return image;
    }

    public byte[] getImageById(int id, StringBuilder type) {
        Image image = iImageRepository.findById(id).orElse(null);
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (null == image || (image.isHidden() && !image.getUser().equals(user))) {
            return null;
        }

        try {
            type.append(image.getType());
            return Files.readAllBytes(Paths.get(image.getPath()));
        } catch (IOException exception) {
            return null;
        }
    }

    public Image addImage(Image image, MultipartFile imageFile) {
        Users user = ((UsersPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        String imagePath = imagesFolder + user.getUsername() + "\\" + imageFile.getOriginalFilename();

        image.setUser(user);
        image.setPath(imagePath);
        image.setType(imageFile.getContentType());
        image.setName(imageFile.getOriginalFilename());

        try {
            File file = new File(imagePath);
            file.mkdirs();
            imageFile.transferTo(file);

            return iImageRepository.save(image);
        } catch (IOException exception) {
            return null;
        }
    }

    public Image updateImage(int id, Image image) {
        Users user = ((UsersPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Image imageSaved = iImageRepository.findById(id).orElse(null);

        if (null == imageSaved || !imageSaved.getUser().equals(user)) {
            return null;
        }

        image.setPath(imageSaved.getPath());
        image.setType(imageSaved.getType());

        return iImageRepository.save(image);
    }

    public boolean deleteImage(int id) {
        Users user = ((UsersPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Image image = iImageRepository.findById(id).orElse(null);
        if (null == image || !image.getUser().equals(user)) {
            return false;
        }

        File file = new File(image.getPath());
        boolean deleted = file.delete();
        if (!deleted) {
            return false;
        }

        iImageRepository.deleteById(id);
        return true;
    }
}
