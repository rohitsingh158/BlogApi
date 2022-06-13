package com.blog.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.config.AppConstants;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.services.FileService;
import com.blog.api.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		PostDto createdPost=this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
		
	}
	//Update Post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@RequestBody PostDto postDto,
			@PathVariable Integer postId
		){
		
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.CREATED);
		
	}
	
	//get By user
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<PostResponse> PostResponse(@PathVariable Integer userId,
			@RequestParam(value="pageNumber",defaultValue="1",required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="5",required=false)Integer pageSize){
		
		PostResponse allPostByUser=this.postService.getAllPostByUser(userId,pageNumber,pageSize);
		
		return new ResponseEntity<PostResponse>(allPostByUser,HttpStatus.OK);
	}
	//get By Category
		@GetMapping("category/{categoryId}/posts")
		public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId ,
				@RequestParam(value="pageNumber",defaultValue="1",required=false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue="5",required=false)Integer pageSize){
			
			PostResponse allPostByCategory=this.postService.getAllPostByCategory(categoryId,pageNumber,pageSize);
			
			return new ResponseEntity<PostResponse>(allPostByCategory,HttpStatus.OK);
		}
		//get All Post
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPost(
				@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
				@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
				@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir){
			
			PostResponse allPost=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
			
			return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
		}
		@GetMapping("/post/{postId}")
		public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
			
			PostDto post=this.postService.getPostByID(postId);
			
			return new ResponseEntity<PostDto>(post,HttpStatus.OK);
		}
		@DeleteMapping("/post/{postId}")
		public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer id){
			this.postService.deletePost(id);
			//return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfuly", true),HttpStatus.OK);
		}
		//Search
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
			List<PostDto> allPost=this.postService.searchPost(keywords);
			//return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
		}
		//upload image
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile file,
				@PathVariable Integer postId) throws IOException{
			
			PostDto postDto=this.postService.getPostByID(postId);
			
			String fileName=this.fileService.uploadImage(path, file);
			postDto.setImageName(fileName);
			PostDto updatedPost= this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
			
		}
		@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response)throws IOException{
			InputStream resource= this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
			
		}
		
		
	
}
