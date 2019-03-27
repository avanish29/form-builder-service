package com.form.builder.configservice.module.usermanagement.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.form.builder.configservice.module.base.entity.BaseEntity;

@Entity
@Table(name = "app_role")
//@Audited 
public class Role extends BaseEntity {
	private static final long serialVersionUID = 6035363317959892411L;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;
	
	@ManyToMany(mappedBy = "roles")
    private Collection<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
}
