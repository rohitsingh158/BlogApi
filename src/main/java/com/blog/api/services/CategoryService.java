package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.CategoryDto;

public interface CategoryService {
	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	//Update
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	//get
	public CategoryDto getCategoryByID(Integer categoryId);
	//get All
	public List<CategoryDto> getAllCategory();
	//Delete
	public void deleteCategory(Integer categoryId);
	
	

}
