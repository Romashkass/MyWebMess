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

        if (null == image || (image.isHidden() && image.getUser().getId() != user.getId())) {
            return null;
        }

        image.setPath(null);
        image.getUser().setPassword(null);

        return image;
    }

    public byte[] getImageById(int id, StringBuilder type) {
        Image image = iImageRepository.findById(id).orElse(null);
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (null == image || (image.isHidden() && image.getUser().getId() != user.getId())) {
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
}
