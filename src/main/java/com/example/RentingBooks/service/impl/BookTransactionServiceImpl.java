package com.example.RentingBooks.service.impl;

import com.example.RentingBooks.dto.BookTransactionDto;
import com.example.RentingBooks.entity.Book;
import com.example.RentingBooks.entity.BookTransaction;
import com.example.RentingBooks.enums.RentType;
import com.example.RentingBooks.repo.BookRepo;
import com.example.RentingBooks.repo.BookTransactionRepo;
import com.example.RentingBooks.service.BookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookTransactionServiceImpl implements BookTransactionService {
    @Autowired
    private BookTransactionRepo bookTransactionRepo;
    @Autowired
    private BookRepo bookRepo;




    @Override
    public BookTransactionDto create(BookTransactionDto bookTransactionDto) {

        Integer codecount=0;
        List<BookTransaction> bookTransactionList=bookTransactionRepo.findAll();
        List<BookTransactionDto> codelist=bookTransactionList.stream().map(entity->BookTransactionDto.builder()
                .code(entity.getCode())
                .build()).collect(Collectors.toList());
        Long count=(codelist.stream().count());
        int c=count.intValue();
        for(int j=0;j<c;j++){
            if(Objects.equals(bookTransactionDto.getCode(), codelist.get(j).getCode())){
                System.out.println(codelist.get(j).getCode());
                codecount=codecount+1;
            }
        }
        System.out.println(codecount);

        if (bookTransactionDto.getId() != null && bookTransactionDto.getNoofdays() == null && bookTransactionDto.getRentstatus()=="RENT" && codecount==1 ) {
            System.out.println(1);
            LocalDate to = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String todate = to.format(formatter);
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bookTransactionDto.setTodate(date1);
            bookTransactionDto.setNoofdays(0);
            Book book = bookTransactionDto.getBook();
            book.setStockcount(book.getStockcount() + 1);
            bookRepo.save(book);

                bookTransactionDto.setRentstatus(String.valueOf(RentType.RETURN));
                BookTransaction entity = BookTransaction.builder()
                        .code(bookTransactionDto.getCode())
                        .fromdate(bookTransactionDto.getFromdate())
                        .todate(bookTransactionDto.getTodate())
                        .rentType(RentType.valueOf(bookTransactionDto.getRentstatus()))
                        .member(bookTransactionDto.getMember())
                        .book(bookTransactionDto.getBook())
                        .build();
                bookTransactionRepo.save(entity);
                return bookTransactionDto.builder()
                        .id(entity.getId())
                        .code(entity.getCode())
                        .fromdate(entity.getFromdate())
                        .todate(entity.getTodate())
                        .rentstatus(String.valueOf(entity.getRentType()))
                        .member(entity.getMember())
                        .book(entity.getBook())
                        .stockcount(entity.getBook().getStockcount())
                        .build();


        } else if(bookTransactionDto.getId() != null && bookTransactionDto.getRentstatus()=="RETURN"){
            System.out.println(2);

        }else if(bookTransactionDto.getId()!=null && bookTransactionDto.getNoofdays() == null && bookTransactionDto.getRentstatus()=="RENT" && codecount==1){
            System.out.println(3);

        }
        else if(bookTransactionDto.getId()==null && bookTransactionDto.getNoofdays()!=null && bookTransactionDto.getRentstatus()==null && codecount==2 ){
            System.out.println("CODE REUSED");

        }
        else if(bookTransactionDto.getId()==null && bookTransactionDto.getNoofdays()!=null && bookTransactionDto.getRentstatus()==null && codecount==0){
            System.out.println(5);
            LocalDate now = LocalDate.now();
            LocalDate to = now.plusDays(bookTransactionDto.getNoofdays());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fromdate = now.format(formatter);
            String todate = to.format(formatter);
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bookTransactionDto.setFromdate(date1);
            bookTransactionDto.setTodate(date2);
            bookTransactionDto.setRentstatus(String.valueOf(RentType.RENT));
            if (bookTransactionDto.getBook().getStockcount() > 0) {
                Book book = bookTransactionDto.getBook();
                book.setStockcount(book.getStockcount() - 1);
                bookRepo.save(book);

                BookTransaction entity = BookTransaction.builder()
                        .id(bookTransactionDto.getId())
                        .code(bookTransactionDto.getCode())
                        .fromdate(bookTransactionDto.getFromdate())
                        .todate(bookTransactionDto.getTodate())
                        .rentType(RentType.valueOf(bookTransactionDto.getRentstatus()))
                        .member(bookTransactionDto.getMember())
                        .book(bookTransactionDto.getBook())
                        .build();
                bookTransactionRepo.save(entity);


                return bookTransactionDto.builder()
                        .id(entity.getId())
                        .code(entity.getCode())
                        .fromdate(entity.getFromdate())
                        .todate(entity.getTodate())
                        .rentstatus(String.valueOf(entity.getRentType()))
                        .member(entity.getMember())
                        .book(entity.getBook())
                        .stockcount(entity.getBook().getStockcount())
                        .build();
            }
            } else {
                return null;
            }
return null;

    }





   @Override
    public List<BookTransactionDto> findAll() {
        List<BookTransaction> bookTransactionList = bookTransactionRepo.findAll();
        return bookTransactionList.stream().map(entity -> BookTransactionDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .fromdate(entity.getFromdate())
                .todate(entity.getTodate())
                .membername(entity.getMember().getName())
                .bookname(entity.getBook().getName())
                .rentstatus(String.valueOf(entity.getRentType()))
                .stockcount(entity.getBook().getStockcount())
                .build()).collect(Collectors.toList());
    }

    @Override
    public BookTransactionDto findById(Integer id) {
        Optional<BookTransaction> optionalBookTransaction=bookTransactionRepo.findById(id);
        if(optionalBookTransaction.isPresent()){
            BookTransaction bookTransaction=optionalBookTransaction.get();
            return BookTransactionDto.builder().id(bookTransaction.getId())
                    .bookname(bookTransaction.getBook().getName())
                    .fromdate(bookTransaction.getFromdate())
                    .todate(bookTransaction.getTodate())
                    .membername(bookTransaction.getMember().getName())
                    .book(bookTransaction.getBook())
                    .member(bookTransaction.getMember())
                    .rentstatus(String.valueOf(bookTransaction.getRentType()))
                    .code(bookTransaction.getCode())
                    .build();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<BookTransaction> optionalBookTransaction=bookTransactionRepo.findById(id);
        if(optionalBookTransaction.isPresent()) {
            bookTransactionRepo.deleteById(id);
        }

    }



    @Override
    public BookTransactionDto findBytransactionId(Integer id) {
        BookTransaction bookTransaction=bookTransactionRepo.findBookTransactionById(id);
        BookTransactionDto bookTransactionDto=BookTransactionDto.builder()
                .id(bookTransaction.getId())
                .code(bookTransaction.getCode())
                .fromdate(bookTransaction.getFromdate())
                .todate(bookTransaction.getTodate())
                .membername(bookTransaction.getMember().getName())
                .bookname(bookTransaction.getBook().getName())
                .book(bookTransaction.getBook())
                .member(bookTransaction.getMember())
                .rentstatus(String.valueOf(bookTransaction.getRentType()))
                .stockcount(bookTransaction.getBook().getStockcount())
                .build();
        return bookTransactionDto;
    }

    @Override
    public List<BookTransactionDto> findByRenttype(RentType rentType) {
        List<BookTransaction> bookTransactionList = bookTransactionRepo.findBookTransactionByRentType(rentType);
        return bookTransactionList.stream().map(entity -> BookTransactionDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .fromdate(entity.getFromdate())
                .todate(entity.getTodate())
                .membername(entity.getMember().getName())
                .bookname(entity.getBook().getName())
                .rentstatus(String.valueOf(entity.getRentType()))
                .stockcount(entity.getBook().getStockcount())
                .build()).collect(Collectors.toList());
    }


}
