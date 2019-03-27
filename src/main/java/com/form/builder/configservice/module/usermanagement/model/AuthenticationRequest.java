package com.form.builder.configservice.module.usermanagement.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class AuthenticationRequest implements Serializable {
	private static final long serialVersionUID = 4167560773940479798L;

	@NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
