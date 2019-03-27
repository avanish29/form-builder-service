package com.form.builder.configservice.module.usermanagement.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserVOTest {
	public static void main(String[] args) throws JsonProcessingException {
		UserVO userVO = new UserVO();
		userVO.setEmail("avanish.k.pandey@gmail.com");
		userVO.setEmailVerified(true);
		userVO.setFailCount(0);
		userVO.setFirstName("Avanish");
		userVO.setLastName("Pandey");
		userVO.setPassword("demo2017");
		userVO.setTcActive(true);
		userVO.setUserName("avanish.pandey");
		userVO.setLanguage("en_US");
		
		RoleVO role = new RoleVO();
		role.setName("ADMIN");
		userVO.addRoles(role);
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(userVO));
	}
}
