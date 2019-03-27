package com.form.builder.configservice.module.usermanagement.restcontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.form.builder.configservice.core.security.jwt.JwtTokenProvider;
import com.form.builder.configservice.core.security.model.UserPrincipal;
import com.form.builder.configservice.module.base.model.PagedResponse;
import com.form.builder.configservice.module.usermanagement.model.AuthenticationRequest;
import com.form.builder.configservice.module.usermanagement.model.AuthenticationResponse;
import com.form.builder.configservice.module.usermanagement.model.UserVO;
import com.form.builder.configservice.module.usermanagement.service.UserService;
import com.form.builder.configservice.util.AppConstants;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	@PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(populateAuthResponse(authentication));
    }
	
	@PostMapping()
	public ResponseEntity<?> createUser(@Valid @RequestBody UserVO userVORequest) {
        return ResponseEntity.ok(userService.createUser(userVORequest));
    }
	
	@GetMapping()
    public PagedResponse<UserVO> getAllUsers(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return userService.getAllUsers(page, size);
    }
	
	private AuthenticationResponse populateAuthResponse(final Authentication authentication) {
		AuthenticationResponse authResponse = null;
		if(authentication != null) {
			String jwt = tokenProvider.generateToken(authentication);
			UserPrincipal converisUser = (UserPrincipal)authentication.getPrincipal();
			
			authResponse = new AuthenticationResponse(jwt);
			authResponse.setId(converisUser.getId());
			authResponse.setFirstName(converisUser.getFirstName());
			authResponse.setLastName(converisUser.getLastName());
		}
		return authResponse;
	}
}
