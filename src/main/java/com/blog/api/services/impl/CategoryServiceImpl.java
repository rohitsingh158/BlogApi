package com.blog.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
	Category category= this.modelMapper.map(categoryDto, Category.class);
		
	Category savedCategory= this.categoryRepo.save(category);
	return this.modelMapper.map(savedCategory,CategoryDto.class);
	
	}
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub

		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() ->  new ResourceNotFoundException("Category","id",categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory=this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory,CategoryDto.class);
	}
	@Override
	public CategoryDto getCategoryByID(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() ->  new ResourceNotFoundException("Category","id",categoryId));
		return this.modelMapper.map(category,CategoryDto.class);
	
	}
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategories=this.categoryRepo.findAll();
		List<CategoryDto> allCategoryDto=allCategories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return allCategoryDto;
	}
	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() ->  new ResourceNotFoundException("Category","id",categoryId));
		this.categoryRepo.delete(category);
		
	}
	
	
	
	

}
