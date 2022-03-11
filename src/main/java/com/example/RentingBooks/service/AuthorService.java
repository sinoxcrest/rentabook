package com.example.RentingBooks.service;

import com.example.RentingBooks.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto create(AuthorDto authorDto);

    List<AuthorDto> findAll();

    AuthorDto findById(Integer id);
    void sendEmail(AuthorDto authorDto);
    void deleteById(Integer id);
    AuthorDto getAuthorByEmail(String email);
}
