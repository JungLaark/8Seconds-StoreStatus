package com.ateciot.checkstorelist.config;

public class UserLoginToken {
	
	public UserLoginToken() {
		
	}
	
	public UserLoginToken(String xAuthToken, String token) {
		this.xAuthToken = xAuthToken;
		this.token = token;
	}
	private String xAuthToken;
	private String token;
	
	public String getxAuthToken() {
		return xAuthToken;
	}
	public void setxAuthToken(String xAuthToken) {
		this.xAuthToken = xAuthToken;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
