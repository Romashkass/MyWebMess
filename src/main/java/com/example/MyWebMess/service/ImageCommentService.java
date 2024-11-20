package com.example.MyWebMess.service;

import com.example.MyWebMess.model.ImageComment;
import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.model.UsersPrincipal;
import com.example.MyWebMess.repository.IImageCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageCommentService {

    private final IImageCommentRepository iImageCommentRepository;

    public List<ImageComment> getImageComments(int id) {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<ImageComment> imageComments = iImageCommentRepository.findByImageId(id);
        if (null == imageComments || (imageComments.getFirst().getImage().isHidden() && !imageComments.getFirst().getImage().getUser().equals(user))) {
            return null;
        }
        return imageComments;
    }

    public ImageComment addImageComment(int id, ImageComment imageComment) {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        imageComment.setUser(user);
        if (null == imageComment.getImage() || (imageComment.getImage().isHidden() && !imageComment.getImage().getUser().equals(user))) {
            return null;
        }
        return iImageCommentRepository.save(imageComment);
    }
}
