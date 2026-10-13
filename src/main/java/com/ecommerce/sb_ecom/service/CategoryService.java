package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    //ovo sam promijenila nakon sto smo napravili dto
    CategoryResponse getAllCategories();

    CategoryDTO createcategory(CategoryDTO categoryDTO);


    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
