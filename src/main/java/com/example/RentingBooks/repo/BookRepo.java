package com.example.RentingBooks.repo;

import com.example.RentingBooks.entity.Book;
import com.example.RentingBooks.projection.BookCategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {
    @Query(
            nativeQuery = true,
            value = "select b.id,\n" +
                    "       b.name as              bookname,\n" +
                    "       b.noofpages noofpages,\n" +
                    "       b.isbn isbn,\n" +
                    "       b.rating rating,\n" +
                    "       b.stockcount stockcount,\n" +
                    "       b.publisheddate publisheddate,\n" +
                    "       string_agg(c.name, ', ') categoryname\n" +
                    "from tbl_book b\n" +
                    "         left join tbl_category c on c.id = b.category_id\n" +
                    "group by b.id, b.name, b.noofpages, b.isbn,b.id, b.name, b.noofpages, b.isbn, b.rating, b.stockcount, b.publisheddate;"

    )
    List<BookCategoryProjection> findAllData();
}
