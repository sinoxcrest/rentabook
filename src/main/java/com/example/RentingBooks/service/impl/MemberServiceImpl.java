package com.example.RentingBooks.service.impl;

import com.example.RentingBooks.dto.MemberDto;
import com.example.RentingBooks.entity.Member;
import com.example.RentingBooks.repo.MemberRepo;
import com.example.RentingBooks.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepo memberRepo;
    @Override
    public MemberDto create(MemberDto memberDto) {
        Member entity= Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .address(memberDto.getAddress())
                .email(memberDto.getEmail())
                .mobileno(memberDto.getMobileno())
                .build();
        entity=memberRepo.save(entity);
        return memberDto.builder().
                id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .mobileno(entity.getMobileno())
                .build();
    }

    @Override
    public List<MemberDto> findAll() {
        List<Member> memberList = memberRepo.findAll();
        return memberList.stream().map(entity -> MemberDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .mobileno(entity.getMobileno())
                .build()).collect(Collectors.toList());
    }

    @Override
    public MemberDto findById(Integer id) {
     Optional<Member> optionalmember=memberRepo.findById(id);
     if(optionalmember.isPresent()){
         Member member=optionalmember.get();
         return MemberDto.builder().
                 id(member.getId())
                 .name(member.getName())
                 .email(member.getEmail())
                 .address(member.getAddress())
                 .mobileno(member.getMobileno())
                 .build();
     }else {
         return null;
     }
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Member> optionalMember=memberRepo.findById(id);
        if(optionalMember.isPresent()) {
            memberRepo.deleteById(id);
        }
    }
}
