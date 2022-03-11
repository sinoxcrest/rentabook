package com.example.RentingBooks.dto;

import com.example.RentingBooks.entity.Book;
import com.example.RentingBooks.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTransactionDto {
    private Integer id;
    @NotEmpty(message = "Your code must not be Empty.")
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date todate;
    private String rentstatus;
    @NotNull(message = "Book must be selected.")
    private Book book;
    @NotNull(message = "Member must be selected.")
    private Member member;
    @NotNull(message = "Number of days can't be empty.")
    private Integer noofdays;
    private String membername;
    private String bookname;
    private Integer stockcount;
    private List<String> codelist;


}
