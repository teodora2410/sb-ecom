package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;
import com.ecommerce.sb_ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
//ovo je na klasnom nivou, znaci da svi iz ove klase krecu sa api i ne moramo posle da navodimo
public class CategoryController {


    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }


    //@GetMapping("api/public/categories")
    @GetMapping( "/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        //promijenis sa list categories na categoryResponse nakon pravljenja dto
        CategoryResponse categoryResponse= categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

    }

    //@PostMapping("api/public/categories")
    @RequestMapping(value = "/public/categories" , method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> CreateCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategortdTO = categoryService.createcategory(categoryDTO);
        return new ResponseEntity<>(savedCategortdTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
         //ResponseEntity tipa jer vraca response
        CategoryDTO deletedcategoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedcategoryDTO, HttpStatus.OK);

    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);


    }


}
