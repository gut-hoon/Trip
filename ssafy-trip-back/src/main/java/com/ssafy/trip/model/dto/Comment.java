package com.ssafy.trip.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Comment {
    private int no;
    private String content;
    private int usersNo;
    private int articleNo;
    private Integer parentNo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate; 
    private String username;
}
