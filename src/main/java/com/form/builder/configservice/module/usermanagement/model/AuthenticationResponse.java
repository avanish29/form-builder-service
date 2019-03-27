package com.form.builder.configservice.module.usermanagement.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = 8500415693213672759L;
	private Long id;
	private String firstName;
	private String lastName;
	private String token;
    private String tokenType = "Bearer";

    public AuthenticationResponse(String accessToken) {
        this.token = accessToken;
    }
    
    public String getToken() {
		return token;
	}

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}
    
    public String getFirstName() {
		return firstName;
	}
    
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    
    public String getLastName() {
		return lastName;
	}
    
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
