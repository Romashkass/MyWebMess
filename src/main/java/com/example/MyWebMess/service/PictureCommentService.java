package com.example.MyWebMess.service;

import com.example.MyWebMess.exception.ForbiddenException;
import com.example.MyWebMess.model.PictureComment;
import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.model.UsersPrincipal;
import com.example.MyWebMess.repository.IPictureCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureCommentService {

    private final IPictureCommentRepository iPictureCommentRepository;

    public List<PictureComment> getPictureComments(int id) {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<PictureComment> pictureComments = iPictureCommentRepository.findByPictureId(id);
        if (!pictureComments.isEmpty() && (pictureComments.getFirst().getPicture().isHidden() && !pictureComments.getFirst().getPicture().getUser().equals(user))) {
            throw new ForbiddenException("No");
        }
        return pictureComments;
    }

    public PictureComment addPictureComment(int id, PictureComment pictureComment) {
        Users user = ((UsersPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        pictureComment.setUser(user);
        if (null == pictureComment.getPicture() || (pictureComment.getPicture().isHidden() && !pictureComment.getPicture().getUser().equals(user))) {
            throw new ForbiddenException("No");
        }
        return iPictureCommentRepository.save(pictureComment);
    }
}
