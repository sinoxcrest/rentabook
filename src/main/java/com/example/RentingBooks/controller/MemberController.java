package com.example.RentingBooks.controller;

import com.example.RentingBooks.dto.AuthorDto;
import com.example.RentingBooks.dto.MemberDto;
import com.example.RentingBooks.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping
    public String openmember(Model model){
        model.addAttribute("memberDto",new MemberDto());
        model.addAttribute("memberDtoList",memberService.findAll());
        return "/member/member";
    }
    @PostMapping("create")
    public String createMember(@Valid @ModelAttribute MemberDto memberDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "/member/member";
        }
        if(memberDto.getId()!=null){
            memberDto.setId(memberDto.getId());
        }
        memberDto=memberService.create(memberDto);
        if(memberDto!=null){
            redirectAttributes.addFlashAttribute("message","Data Saved Successfully");
        }else{
            redirectAttributes.addFlashAttribute("message","Data not Saved");
        }

        return "redirect:/member";
    }
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") Integer id, Model model){
        memberService.deleteById(id);
        return "redirect:/member";

    }
    @GetMapping("/edit/{id}")
    public String editMember(@PathVariable("id")Integer id,@ModelAttribute MemberDto memberDto, Model model){

        model.addAttribute("memberDto",memberService.findById(id));
        model.addAttribute("memberDtoList",memberService.findAll());
        return "/member/member";
    }
}
