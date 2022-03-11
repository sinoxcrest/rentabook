package com.example.RentingBooks.controller;

import com.example.RentingBooks.dto.CategoryDto;
import com.example.RentingBooks.entity.Category;
import com.example.RentingBooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

    @GetMapping
    public String opencategory(Model model){
        model.addAttribute("categoryDto",new CategoryDto());
        model.addAttribute("categoryDtoList",categoryService.findAll());
        return "/category/category";
    }
    @PostMapping("create")
    public String createCategory(@Valid @ModelAttribute CategoryDto categoryDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "/category/category";
        }
        if(categoryDto.getId()!=null){
          categoryDto.setId(categoryDto.getId());
        }
        categoryDto=categoryService.create(categoryDto);
        if(categoryDto!=null){
            redirectAttributes.addFlashAttribute("message","Data Saved Successfully");
        }else{
            redirectAttributes.addFlashAttribute("message","Data not Saved");
        }

        return "redirect:/category";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id,Model model){
        categoryService.deleteById(id);
       return "redirect:/category";
    }
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id,@ModelAttribute CategoryDto categoryDto,Model model){
        model.addAttribute("categoryDto",categoryService.findById(id));
        model.addAttribute("categoryDtoList",categoryService.findAll());
        return "/category/category";


    }
}
