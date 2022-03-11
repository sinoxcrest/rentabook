package com.example.RentingBooks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String baseUrl(){
        return "redirect:/index";
    }
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String openIndexPage(){
        return "index";
    }

}
