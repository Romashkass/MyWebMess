package com.example.MyWebMess.utils;

import com.example.MyWebMess.dto.PictureCommentDto;
import com.example.MyWebMess.dto.PictureDto;
import com.example.MyWebMess.dto.UsersDto;
import com.example.MyWebMess.model.Picture;
import com.example.MyWebMess.model.PictureComment;
import com.example.MyWebMess.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingUtils {

    public List<UsersDto> mapToUsersDto(List<Users> users) {
        return users.stream().map(this::mapToUsersDto).collect(Collectors.toList());
    }
    public UsersDto mapToUsersDto(Users user) {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(user.getId());
        usersDto.setUsername(user.getUsername());
        usersDto.setPassword("");
        return usersDto;
    }
    public List<Users> mapToUsers(List<UsersDto> usersDtos) {
        return usersDtos.stream().map(this::mapToUsers).collect(Collectors.toList());
    }
    public Users mapToUsers(UsersDto usersDto) {
        Users user = new Users();
        user.setId(usersDto.getId());
        user.setUsername(usersDto.getUsername());
        user.setPassword(usersDto.getPassword());
        return user;
    }

    public List<PictureDto> mapToPictureDto(List<Picture> pictures) {
        return pictures.stream().map(this::mapToPictureDto).collect(Collectors.toList());
    }
    public PictureDto mapToPictureDto(Picture picture) {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setId(picture.getId());
        pictureDto.setName(picture.getName());
        pictureDto.setDescription(picture.getDescription());
        pictureDto.setHidden(picture.isHidden());

        pictureDto.setUser(mapToUsersDto(picture.getUser()));
        return pictureDto;
    }
    public List<Picture> mapToPicture(List<PictureDto> pictureDtos) {
        return pictureDtos.stream().map(this::mapToPicture).collect(Collectors.toList());
    }
    public Picture mapToPicture(PictureDto pictureDtos) {
        Picture picture = new Picture();
        picture.setId(pictureDtos.getId());
        picture.setName(pictureDtos.getName());
        picture.setHidden(pictureDtos.isHidden());
        picture.setDescription(pictureDtos.getDescription());
        picture.setId(pictureDtos.getId());
        picture.setId(pictureDtos.getId());

        if (null != pictureDtos.getUser()){
            picture.setUser(mapToUsers(pictureDtos.getUser()));
        }
        return picture;
    }

    public List<PictureCommentDto> mapToPictureCommentDto(List<PictureComment> pictureComments) {
        return pictureComments.stream().map(this::mapToPictureCommentDto).collect(Collectors.toList());
    }
    public PictureCommentDto mapToPictureCommentDto(PictureComment pictureComment) {
        PictureCommentDto pictureCommentDto = new PictureCommentDto();
        pictureCommentDto.setId(pictureComment.getId());
        pictureCommentDto.setText(pictureComment.getText());
        pictureCommentDto.setPostDate(pictureComment.getPostDate());

        pictureCommentDto.setUser(mapToUsersDto(pictureComment.getUser()));
        pictureCommentDto.setPicture(mapToPictureDto(pictureComment.getPicture()));
        return pictureCommentDto;
    }
    public List<PictureComment> mapToPictureComment(List<PictureCommentDto> pictureCommentDtos) {
        return pictureCommentDtos.stream().map(this::mapToPictureComment).collect(Collectors.toList());
    }
    public PictureComment mapToPictureComment(PictureCommentDto pictureCommentDto) {
        PictureComment pictureComment = new PictureComment();
        pictureComment.setId(pictureCommentDto.getId());
        pictureComment.setText(pictureCommentDto.getText());
        pictureComment.setPostDate(pictureComment.getPostDate());

        pictureComment.setUser(mapToUsers(pictureCommentDto.getUser()));
        pictureComment.setPicture(mapToPicture(pictureCommentDto.getPicture()));
        return pictureComment;
    }


}
