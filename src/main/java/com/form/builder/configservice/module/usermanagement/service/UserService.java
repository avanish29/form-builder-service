package com.form.builder.configservice.module.usermanagement.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.form.builder.configservice.core.security.model.UserPrincipal;
import com.form.builder.configservice.module.base.exception.BadRequestException;
import com.form.builder.configservice.module.base.exception.ResourceNotFoundException;
import com.form.builder.configservice.module.base.model.PagedResponse;
import com.form.builder.configservice.module.usermanagement.entity.Role;
import com.form.builder.configservice.module.usermanagement.entity.User;
import com.form.builder.configservice.module.usermanagement.model.RoleVO;
import com.form.builder.configservice.module.usermanagement.model.UserVO;
import com.form.builder.configservice.module.usermanagement.repository.RoleRepository;
import com.form.builder.configservice.module.usermanagement.repository.UserRepository;
import com.form.builder.configservice.util.AppConstants;

@Service
public class UserService implements UserDetailsService {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserNameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));
		return UserPrincipal.create(user);
	}
	
	@Transactional
    public UserDetails loadUserById(final Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserPrincipal.create(user);
    }
	
	@RolesAllowed({ "CONFIG_ADMIN", "SUPER_ADMIN", "SYSTEM_ADMIN" })
	public PagedResponse<UserVO> getAllUsers(final int page, final int size) {
		validatePageNumberAndSize(page, size);
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "firstName");
		Page<User> users = userRepository.findAll(pageable);
		if(users.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), users.getNumber(), users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
		}
		
		List<UserVO> usersResponse = users.map(user -> {
			UserVO userResponse = new UserVO();
			BeanUtils.copyProperties(user, userResponse);
			for(Role role : CollectionUtils.emptyIfNull(user.getRoles())) {
				RoleVO roleResponse = new RoleVO();
				BeanUtils.copyProperties(role, roleResponse);
				userResponse.addRoles(roleResponse);
			}		
			return userResponse;
		}).getContent();
		
		return new PagedResponse<>(usersResponse, users.getNumber(), users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
	}
	
	@Transactional
	public UserVO createUser(final UserVO userVo) {
		User newEntity = convertVoToEntity(userVo);
		for(RoleVO role : userVo.getRoles()) {
			newEntity.addRole(roleRepository.findByNameIgnoreCase(role.getName()).get());
		}
		newEntity.setPassword(passwordEncoder.encode(userVo.getPassword()));
		newEntity = userRepository.save(newEntity);
		return convertEntityToVo(newEntity);
	}
	
	private User convertVoToEntity(final UserVO userVo) {
		User userEntity = new User();
		BeanUtils.copyProperties(userVo, userEntity);
		userEntity.setRoles(null);
		return userEntity;
	}
	
	private UserVO convertEntityToVo(final User userEntity) {
		UserVO userVo = new UserVO();
		BeanUtils.copyProperties(userEntity, userVo);
		return userVo;
	}
	
	private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
