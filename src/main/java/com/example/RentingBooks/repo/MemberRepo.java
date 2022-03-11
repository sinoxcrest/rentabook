package com.example.RentingBooks.repo;

import com.example.RentingBooks.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository <Member,Integer>{
}
