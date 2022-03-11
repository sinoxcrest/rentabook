package com.example.RentingBooks.controller;

import com.example.RentingBooks.dto.AuthorDto;
import com.example.RentingBooks.entity.Author;
import com.example.RentingBooks.repo.AuthorRepo;
import com.example.RentingBooks.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public String openauthor(Model model){
        model.addAttribute("authorDto",new AuthorDto());
        model.addAttribute("authorDtoList",authorService.findAll());
        return "/author/author";
    }
    @PostMapping("create")
    public String createAuthor(@Valid @ModelAttribute AuthorDto authorDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "/author/author";
        }
        if(authorDto.getId()!=null) {
            authorDto.setId(authorDto.getId());

        }

        authorDto=authorService.create(authorDto);

        if(authorDto!=null){
            redirectAttributes.addFlashAttribute("message","Data Saved Successfully");
        }else{
            redirectAttributes.addFlashAttribute("message","Data not Saved");
        }

        return "redirect:/author";
    }
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Integer id, Model model){
        authorService.deleteById(id);
        return "redirect:/author";

    }
    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable("id") Integer id,@ModelAttribute AuthorDto authorDto,Model model){
        model.addAttribute("authorDto",authorService.findById(id));
        model.addAttribute("authorDtoList",authorService.findAll());
        return "/author/author";

    }

}
