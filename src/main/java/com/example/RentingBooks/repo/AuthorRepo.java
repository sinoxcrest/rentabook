package com.example.RentingBooks.repo;

import com.example.RentingBooks.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Integer> {
    Author getAuthorByEmail(String email);
}
