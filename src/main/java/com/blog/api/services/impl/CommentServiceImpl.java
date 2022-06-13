package com.blog.api.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Comment;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CommentDto;
import com.blog.api.repositories.CommentRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User","id",userId));
		Post post=this.postRepo.findById(postId)
				.orElseThrow(() ->  new ResourceNotFoundException("Post","id",postId));
		
		
		Comment comment =this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment=this.commentRepo.findById(commentId)
				.orElseThrow(() ->  new ResourceNotFoundException("Comment","id",commentId));
		this.commentRepo.delete(comment);

	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment=this.commentRepo.findById(commentId)
				.orElseThrow(() ->  new ResourceNotFoundException("Comment","id",commentId));
		comment.setContent(commentDto.getContent());
		
		Comment updatedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}

}
