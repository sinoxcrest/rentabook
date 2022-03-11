package com.example.RentingBooks.repo;

import com.example.RentingBooks.entity.BookTransaction;
import com.example.RentingBooks.enums.RentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction,Integer> {
    BookTransaction findBookTransactionById(Integer id);
    List<BookTransaction> findBookTransactionByRentType(RentType rentType);
}
