package com.form.builder.configservice.core.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.form.builder.configservice.module.usermanagement.entity.User;

public class UserPrincipal implements UserDetails {
	private static final long serialVersionUID = -5695058524304784629L;
	private Long id;
	private String firstName;
	private String lastName;
    private String userName;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    
    public UserPrincipal(Long id, String username, String email, String password) {
        this.id = id;
        this.userName = username;
        this.email = email;
        this.password = password;
    }

    public static UserPrincipal create(User user) {
    	UserPrincipal userPrincipal = new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword()
        );
    	userPrincipal.firstName = user.getFirstName();
    	userPrincipal.lastName = user.getLastName();
    	
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(user.getRoles())) 
			roles.addAll(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
		userPrincipal.authorities = roles;
		
		return userPrincipal;
    }
    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    
    public String getFirstName() {
		return firstName;
	}
    
    public String getLastName() {
		return lastName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
