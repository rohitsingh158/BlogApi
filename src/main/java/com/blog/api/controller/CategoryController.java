package com.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategoryDto,HttpStatus.CREATED);
	}
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updatedCategoryDto=this.categoryService.updateCategory(categoryDto, categoryId);
		return  ResponseEntity.ok(updatedCategoryDto);
	}
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryId") Integer id){
		this.categoryService.deleteCategory(id);
		//return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfuly", true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllUser(){
		List<CategoryDto> allCategories=this.categoryService.getAllCategory();
		return ResponseEntity.ok(allCategories);
	}
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getUserById(@PathVariable Integer categoryId){
		CategoryDto categoryDetails=this.categoryService.getCategoryByID(categoryId);
		return  ResponseEntity.ok(categoryDetails);
	}
	
}
