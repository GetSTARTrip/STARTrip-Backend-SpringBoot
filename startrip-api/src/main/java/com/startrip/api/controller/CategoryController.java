package com.startrip.api.controller;

import com.startrip.api.service.CategoryService;
import com.startrip.core.dto.category.RequestCategoryDto;
import com.startrip.core.dto.category.UpdateCategoryDto;
import com.startrip.core.entity.category.Category;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/category")
    public @ResponseBody
    ResponseEntity<String> createCategory(@RequestBody RequestCategoryDto dto) {
        try {
            categoryService.createCategory(dto);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("카테고리 생성", HttpStatus.CREATED);
    }

    @GetMapping("/category")
    public @ResponseBody
    ResponseEntity<List<Category>> getCategory() {
        List<Category> categories;
        try {
            categories = categoryService.getCategoryList();
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public @ResponseBody
    ResponseEntity getCategory(@PathVariable("id") UUID id) {
        Category category;
        try {
            category = categoryService.getCategory(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(category, HttpStatus.OK);
    }

    // GET All child
    @GetMapping("/category/child-full/{id}")
    public @ResponseBody
    ResponseEntity getCategoryChildren(@PathVariable("id") UUID id) {
        List<Category> children;
        try {
            children = categoryService.getChildrenCategory(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(children, HttpStatus.OK);
    }

    // GET depth+1 child
    @GetMapping("category/child/{id}")
    public @ResponseBody
    ResponseEntity getCategoryChildren1Depth(@PathVariable("id") UUID id) {
        List<Category> children;
        try {
            children = categoryService.getDepthPlus1ChildrenCategory(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(children, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PutMapping("/category/{id}")
    public @ResponseBody
    ResponseEntity updateCategory(@PathVariable("id") UUID id, UpdateCategoryDto dto) {
        try {
            categoryService.updateCategory(id, dto);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("수정", HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @DeleteMapping("/category/{id}")
    public @ResponseBody
    ResponseEntity deleteCategory(@PathVariable("id") UUID id) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제", HttpStatus.OK);
    }
}