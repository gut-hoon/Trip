package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetail {
	private int placeNo;
	private int seq;
	private int planId;
	private String placeUrl;
	private	String placeName;
	private String placeAddress;
}
