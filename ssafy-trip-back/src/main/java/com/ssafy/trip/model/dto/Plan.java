package com.ssafy.trip.model.dto;
import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Plan {
	private int no;
	private @NonNull String title;
	private @NonNull int usersNo;
	private String desc;
	private String expense;
	private @NonNull Date startDate;
	private @NonNull Date endDate;
	private boolean share;
	private String userName;
}
