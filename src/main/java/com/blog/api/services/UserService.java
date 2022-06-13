package com.blog.api.services;

import com.blog.api.payloads.UserDto;


public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto,Integer userId);
	UserDto getUserById(Integer userId);
	java.util.List<UserDto> getAllUser ();
	
	void deleteUser(Integer userId);
	
	

}
