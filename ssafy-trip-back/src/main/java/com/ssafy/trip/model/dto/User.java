package com.ssafy.trip.model.dto;

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
public class User {
	private int no;
	private @NonNull String password;
	private @NonNull String name;
	private @NonNull String email;
	private String role = "USER";
	private String refresh;
}
