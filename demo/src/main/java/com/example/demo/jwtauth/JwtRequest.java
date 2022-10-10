package com.example.demo.jwtauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

	private String username;
	private String password;
	private String role;
	private Long cid;
	public String getRole() {
		return role;
	}

	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
