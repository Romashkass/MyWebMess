package com.example.MyWebMess.dto;

import lombok.Data;

@Data
public class PictureDto {
    private int id;
    private UsersDto user;
    private String name;
    private String description;
    private boolean hidden;
}
