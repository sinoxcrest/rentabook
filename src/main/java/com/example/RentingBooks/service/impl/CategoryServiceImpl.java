package com.example.RentingBooks.service.impl;

import com.example.RentingBooks.dto.BookDto;
import com.example.RentingBooks.dto.CategoryDto;
import com.example.RentingBooks.entity.Book;
import com.example.RentingBooks.entity.Category;
import com.example.RentingBooks.repo.BookRepo;
import com.example.RentingBooks.repo.CategoryRepo;
import com.example.RentingBooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private BookRepo bookRepo;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        System.out.println(categoryDto.getId());
            Category entity = Category.builder()
                    .id(categoryDto.getId())
                    .name(categoryDto.getName())
                    .description(categoryDto.getDescription())
                    .build();
            entity = categoryRepo.save(entity);
            return categoryDto.builder().
                    id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .build();


    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList.stream().map(entity ->CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build()).collect(Collectors.toList());
    }



    @Override
    public CategoryDto findById(Integer id) {
        Optional<Category> optionalCategory=categoryRepo.findById(id);
        if(optionalCategory.isPresent()){
            Category category=optionalCategory.get();
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Category> optionalCategory=categoryRepo.findById(id);
        if(optionalCategory.isPresent()) {
            categoryRepo.deleteById(id);


        }
    }


}
