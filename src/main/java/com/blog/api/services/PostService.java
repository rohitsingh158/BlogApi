package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;

public interface PostService {

	//Create Post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//Update Post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//Get All Post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//Get all post by Category
	PostResponse getAllPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize);
	
	//Get All post by User
	PostResponse getAllPostByUser(Integer userId,Integer pageNumber,Integer pageSize);
	
	//Search post by Category
	List<PostDto> searchPost(String keyword);
	
	//Get post by Id
	PostDto getPostByID(Integer postId) ;
	
	//Delete Post
	void deletePost(Integer postId);
	
}
