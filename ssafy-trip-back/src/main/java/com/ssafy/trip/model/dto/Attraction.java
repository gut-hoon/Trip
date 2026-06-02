package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attraction {
	private int no;
	private int contentId;
	private String title;
	private int contentTypeId;
	private int areaCode;
	private int sigunguCode;
	private String firstImage1;
	private String firstImage2;
	private int mapLevel;
	private Double latitude;
	private Double longitude;
	private String tel;
	private String addr1;
	private String addr2;
	private String homepage;
	private String overview;
}
