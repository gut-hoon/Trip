package com.ssafy.trip.model.dto;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
	private int no;
	private @NonNull String title;
	private @NonNull int usersNo;
	private @NonNull String content;
    private Date regDate;
	private int viewCnt = 0;
	private int isArticle = 1;
	private String name;
}
