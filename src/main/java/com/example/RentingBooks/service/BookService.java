package com.example.RentingBooks.service;

import com.example.RentingBooks.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto create(BookDto bookDto);

    List<BookDto> findAll();

    BookDto findById(Integer id);

    void deleteById(Integer id);
}
