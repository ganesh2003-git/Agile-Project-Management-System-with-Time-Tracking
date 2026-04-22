package com.apmst.dto;

import com.apmst.enums.Role;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private int points;
    private boolean isActive;

}
