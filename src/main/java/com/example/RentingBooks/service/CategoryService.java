package com.example.RentingBooks.service;

import com.example.RentingBooks.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    List<CategoryDto> findAll();

    CategoryDto findById(Integer id);

    void deleteById(Integer id);

}
