package com.form.builder.configservice.module.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.form.builder.configservice.module.usermanagement.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Optional<Role> findByNameIgnoreCase(final String roleName);
}
