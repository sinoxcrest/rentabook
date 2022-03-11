package com.example.RentingBooks.dto;

import com.example.RentingBooks.entity.Author;
import com.example.RentingBooks.entity.Category;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Integer id;
    @NotEmpty(message = "Book's name cannot be empty.")
    private String name;
    @NotNull(message = "Book's page size cannot be empty.")
    @Min(value =1,message = "Book's page size should not be less than 1.")
    private Integer noofpages;
    @NotEmpty(message = "Book's ISBN cannot be empty.")
    private String isbn;
    @NotNull(message = "Book's rating cannot be empty.")
    @Max(value = 5,message = "Rating should not exceed 5.")
    @Min(value =1,message = "Rating should not be less than 1.")
    private Integer rating;
    @NotNull(message = "Book's stockcount cannot be empty.")
    @Min(value =1,message = "Book's stockcount should not be less than 1.")
    private Integer stockcount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Book's published date cannnot be set empty")
    private Date publisheddate;
    private String filepath;
    @NotNull(message = "Book's cover cannot be set empty")
    private MultipartFile multipartFile;
    @NotNull(message = "Category must be selected.")
    private Category category;
    private Integer categoryid;
    private String categoryname;
    @Size(min=1,message = "Author must be selected.")
    private List<Integer> authorList;
    private List<Author> authorDtoList;
    private String authorname;
    private String message;


}
