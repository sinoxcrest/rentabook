package com.example.RentingBooks.controller;

import com.example.RentingBooks.components.FileStorageComponent;
import com.example.RentingBooks.dto.*;
import com.example.RentingBooks.entity.Author;
import com.example.RentingBooks.entity.Book;
import com.example.RentingBooks.entity.BookTransaction;
import com.example.RentingBooks.enums.RentType;
import com.example.RentingBooks.repo.BookRepo;
import com.example.RentingBooks.repo.BookTransactionRepo;
import com.example.RentingBooks.service.BookService;
import com.example.RentingBooks.service.BookTransactionService;
import com.example.RentingBooks.service.MemberService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books_transactions")
public class BookTransactionController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookTransactionService bookTransactionService;
    @Autowired
    private BookTransactionRepo bookTransactionRepo;

    @GetMapping
    public String openbooktransaction(Model model){
        List<BookDto> books=bookService.findAll();
        List<MemberDto> members=memberService.findAll();
        model.addAttribute("books",books);
        model.addAttribute("members",members);
        model.addAttribute("bookTransactionDto",new BookTransactionDto());
        model.addAttribute("bookTransactionDtoList",bookTransactionService.findByRenttype(RentType.RENT));
        return "/book_transaction/book_transaction";
    }

    @PostMapping("create")
    public String createBook(@Valid @ModelAttribute BookTransactionDto bookTransactionDto, BindingResult bindingResult,Model model, RedirectAttributes redirectAttributes) throws IOException {

        if(bindingResult.hasErrors()){
            List<BookDto> books=bookService.findAll();
            List<MemberDto> members=memberService.findAll();
            model.addAttribute("books",books);
            model.addAttribute("members",members);
            model.addAttribute("bookTransactionDto",bookTransactionDto);
            model.addAttribute("bookTransactionDtoList",bookTransactionService.findAll());
            return "/book_transaction/book_transaction";
        }


            bookTransactionDto=bookTransactionService.create(bookTransactionDto);


        if(bookTransactionDto!=null) {

            redirectAttributes.addFlashAttribute("message", "Data Saved Successfully");
        }else{

            redirectAttributes.addFlashAttribute("message","Either your code is repeated or book count is zero.");
        }

        return "redirect:/books_transactions";

    }
    @GetMapping("/delete/{id}")
    public String deleteBookTransaction(@PathVariable("id") Integer id, Model model){
        bookTransactionService.deleteById(id);
        return "redirect:/books_transactions";

    }
}
