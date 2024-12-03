package com.example.MyWebMess.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PictureCommentDto {
    private int id;
    private UsersDto user;
    private PictureDto picture;
    private String text;
    private Date postDate;
}
