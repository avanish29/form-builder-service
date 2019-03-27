package com.form.builder.configservice.module.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.form.builder.configservice.module.usermanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByIdIn(final List<Long> userIds);

    public Optional<User> findByUserNameIgnoreCase(final String userName);

    public Boolean existsByUserName(final String userName);
    
    public Page<User> findAllOrderByFirstName(final Pageable pageable);
}
