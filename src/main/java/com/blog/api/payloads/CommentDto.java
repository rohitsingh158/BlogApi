package com.blog.api.payloads;

import lombok.Getter;
import lombok.Setter;

//getter setter will automatically generate by lombok tool
@Getter
@Setter
public class CommentDto {
	private Integer commentId;
	private String content;
	
	private UserDto user;
	
}
