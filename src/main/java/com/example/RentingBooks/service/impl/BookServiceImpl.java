package com.example.RentingBooks.service.impl;

import com.example.RentingBooks.components.FileStorageComponent;
import com.example.RentingBooks.dto.AuthorDto;
import com.example.RentingBooks.dto.BookDto;
import com.example.RentingBooks.dto.ResponseDto;
import com.example.RentingBooks.entity.Author;
import com.example.RentingBooks.entity.Book;
import com.example.RentingBooks.entity.Category;
import com.example.RentingBooks.repo.BookRepo;
import com.example.RentingBooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private FileStorageComponent fileStorageComponent;
    @Autowired
    private BookRepo bookRepo;

    @Override
    public BookDto create(BookDto bookDto) {
        ResponseDto responseDto = null;
        try {
            responseDto = fileStorageComponent.storeFile(bookDto.getMultipartFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responseDto.isStatus()) {
            List<Author> authorList = new ArrayList<>();
            try {
                bookDto.getAuthorList().forEach(item -> {
                    Author author = new Author();
                    author.setId(item);
                    authorList.add(author);
                });
            } catch (Exception e) {
            }
            Book entity = Book.builder().id(bookDto.getId())
                    .name(bookDto.getName())
                    .noofpages(bookDto.getNoofpages())
                    .isbn(bookDto.getIsbn())
                    .rating(bookDto.getRating())
                    .stockcount(bookDto.getStockcount())
                    .publisheddate(bookDto.getPublisheddate())
                    .filepath(responseDto.getMessage())
                    .category(bookDto.getCategory())
                    .authorList(authorList)
                    .build();
            entity = bookRepo.save(entity);
            return BookDto.builder().id(bookDto.getId())
                    .name(bookDto.getName())
                    .noofpages(bookDto.getNoofpages())
                    .isbn(bookDto.getIsbn())
                    .rating(bookDto.getRating())
                    .stockcount(bookDto.getStockcount())
                    .publisheddate(bookDto.getPublisheddate())
                    .filepath(responseDto.getMessage())
                    .category(bookDto.getCategory())
                    .authorDtoList(authorList)
                    .build();
        } else if (!responseDto.isStatus()) {
         return BookDto.builder().message(responseDto.getMessage()).build();

        }else {
            return null;
        }
    }


    @Override
    public List<BookDto> findAll() {
        List<Book> bookList = bookRepo.findAll();
        return bookList.stream().map(entity -> BookDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .noofpages(entity.getNoofpages())
                .isbn(entity.getIsbn())
                .rating(entity.getRating())
                .stockcount(entity.getStockcount())
                .publisheddate(entity.getPublisheddate())
                .filepath(entity.getFilepath())
                .categoryname(entity.getCategory().getName())
                .authorname(entity.getAuthorList().stream().map(Author::getFirstname).collect(Collectors.toList()).toString()+"")
                .build()).collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Integer id) {

        Optional<Book> optionalBook=bookRepo.findById(id);
        if(optionalBook.isPresent()){
            Book book=optionalBook.get();
            try {
                return BookDto.builder().id(book.getId())
                        .name(book.getName())
                        .noofpages(book.getNoofpages())
                        .isbn(book.getIsbn())
                        .rating(book.getRating())
                        .stockcount(book.getStockcount())
                        .publisheddate(book.getPublisheddate())
                        .filepath(fileStorageComponent.returnFileAsBase64(book.getFilepath()))
                        .categoryname(book.getCategory().getName())
                        .authorname(book.getAuthorList().stream().map(Author::getFirstname).collect(Collectors.toList()).toString()+"")
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }return null;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Book> optionalBook=bookRepo.findById(id);
        if(optionalBook.isPresent()) {
            bookRepo.deleteById(id);
        }
    }
}
