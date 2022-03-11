package com.example.RentingBooks.service;

import com.example.RentingBooks.dto.MemberDto;

import java.util.List;

public interface MemberService {
    MemberDto create(MemberDto memberDto);

    List<MemberDto> findAll();

    MemberDto findById(Integer id);
    void deleteById(Integer id);
}
