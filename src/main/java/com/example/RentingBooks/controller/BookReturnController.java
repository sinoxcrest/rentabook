package com.example.RentingBooks.controller;

import com.example.RentingBooks.dto.BookTransactionDto;
import com.example.RentingBooks.entity.BookTransaction;
import com.example.RentingBooks.enums.RentType;
import com.example.RentingBooks.repo.BookTransactionRepo;
import com.example.RentingBooks.service.BookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books_return")

public class BookReturnController {
    @Autowired
    private BookTransactionService bookTransactionService;
    @Autowired
    private BookTransactionRepo bookTransactionRepo;
    @GetMapping
    public String openbooktransaction(Model model){
        model.addAttribute("bookReturnDto",new BookTransactionDto());
       model.addAttribute("bookReturnDtoList",bookTransactionService.findByRenttype(RentType.RENT));
        model.addAttribute("bookReturnDtoList1",bookTransactionService.findByRenttype(RentType.RETURN));
        return "/book_transaction/book_return";
    }
    @GetMapping("/delete/{id}")
    public String deleteBookTransaction(@PathVariable("id") Integer id, Model model){
        bookTransactionService.deleteById(id);
        return "redirect:/books_return";

    }
    @GetMapping("/book_return_view/{id}")
    public String viewBookReturn(@PathVariable("id") Integer id,Model model) {
        model.addAttribute("bookReturnDtos",bookTransactionService.findById(id));



        return "/book_transaction/book_return_view";
    }

    @GetMapping(value="/create/{id}")
    public String createReturn(@ModelAttribute BookTransactionDto bookTransactionDto,@PathVariable("id") Integer id,Model model){
        bookTransactionDto=bookTransactionService.findById(id);

        bookTransactionService.create(bookTransactionDto);

        return "redirect:/books_return";
    }
    @PostMapping("/data")
    @ResponseBody
    public BookTransactionDto datafromselect(@RequestBody Integer id) throws Exception {
        if (id.equals(null)) {
            throw new Exception("Code cannot be empty");
        }
        return bookTransactionService.findBytransactionId(id);
    }
    @PostMapping("create")
    public String createBook(@ModelAttribute("code")Integer id,   Model model,BookTransactionDto bookTransactionDto, RedirectAttributes redirectAttributes) throws IOException {
        bookTransactionDto=bookTransactionService.findById(id);
        bookTransactionService.create(bookTransactionDto);

        if(bookTransactionDto!=null) {

            redirectAttributes.addFlashAttribute("message", "Data Saved Successfully");
        }else{

            redirectAttributes.addFlashAttribute("message","Either your code is repeated or book count is zero.");
        }

        return "redirect:/books_return";

    }



    }
