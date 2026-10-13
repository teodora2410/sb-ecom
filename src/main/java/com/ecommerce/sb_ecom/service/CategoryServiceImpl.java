package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;
import com.ecommerce.sb_ecom.project.exceptions.APIException;
import com.ecommerce.sb_ecom.project.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.repositories.CategoryRepository;
import io.micrometer.common.KeyValues;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    //dodali maper zbog dto
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No category created till now!");
        //takva je sintaksa za mapiranje
        List<CategoryDTO> categoryDTOS = categories.stream().
                map(category -> modelMapper.map(category, CategoryDTO.class)).
                toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createcategory(CategoryDTO categoryDTO) {

        Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(savedCategory != null)
            throw new APIException("Category with the name " +categoryDTO.getCategoryName() + " already exists!");
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        //umjesto for petlje ovako u javi prodjes kroz citav niz pomocu stream i filter

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "CategoryId", categoryId));

        categoryRepository.delete(category);
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);

        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);



    }
}
