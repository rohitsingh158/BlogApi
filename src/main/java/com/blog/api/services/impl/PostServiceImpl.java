package com.blog.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort ;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User","id",userId));
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() ->  new ResourceNotFoundException("Category","id",categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		
		Post newPost=this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}
	
	

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
			Post post=this.postRepo.findById(postId)
				.orElseThrow(() ->  new ResourceNotFoundException("Post","id",postId));
			post.setContent(postDto.getContent());
			post.setTitle(post.getTitle());
			post.setImageName(postDto.getImageName());
			
			
			Post updatedPost=this.postRepo.save(post);
			return this.modelMapper.map(updatedPost, PostDto.class);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> allPost=pagePost.getContent();
		List<PostDto> allPostDto=allPost.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostByID(Integer postId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId)
				.orElseThrow(() ->  new ResourceNotFoundException("Post","id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(() ->  new ResourceNotFoundException("Post","id",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
		// TODO Auto-generated method stub
		
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() ->  new ResourceNotFoundException("Category","id",categoryId));
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findByCategory(category,p);
		List<Post> allPostByCategory= pagePost.getContent();
		List<PostDto> allPostByCategoryDto=allPostByCategory.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostByCategoryDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
		

	}

	@Override
	public PostResponse getAllPostByUser(Integer userId,Integer pageNumber,Integer pageSize) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User","id",userId));	
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findByUser(user, p);
		List<Post> allPostByUser= pagePost.getContent();
		List<PostDto> allPostByUserDto=allPostByUser.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostByUserDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		// TODO Auto-generated method stub
		List<Post> allPost=this.postRepo.findByTitleContaining(keyword);
		//List<Post> allPost=this.postRepo.searchByTitle("%"+keyword+"%");
		
		List<PostDto> allPostDto=allPost.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostDto;
	}

}
