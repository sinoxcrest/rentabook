package com.example.RentingBooks.service.impl;

import com.example.RentingBooks.dto.AuthorDto;
import com.example.RentingBooks.entity.Author;
import com.example.RentingBooks.repo.AuthorRepo;
import com.example.RentingBooks.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromName;

    @Value("${spring.mail.password}")
    private String password;
    @Override
    public AuthorDto create(AuthorDto authorDto) {
        sendEmail(authorDto);
        Author entity= Author.builder()
                .id(authorDto.getId())
                .firstname(authorDto.getFirstname())
                .lastname(authorDto.getLastname())
                .email(authorDto.getEmail())
                .mobileno(authorDto.getMobileno())
                .build();
        entity=authorRepo.save(entity);
        return authorDto.builder().
                id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .mobileno(entity.getMobileno())
                .build();
    }

    @Override
    public List<AuthorDto> findAll() {
        List<Author> authorList = authorRepo.findAll();
        return authorList.stream().map(entity -> AuthorDto.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .mobileno(entity.getMobileno())
                .build()).collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(Integer id) {
        Optional<Author> optionalauthor=authorRepo.findById(id);
        if(optionalauthor.isPresent()) {
            Author author = optionalauthor.get();
            return AuthorDto.builder().
                    id(author.getId())
                    .firstname(author.getFirstname())
                    .lastname(author.getLastname())
                    .email(author.getEmail())
                    .mobileno(author.getMobileno())
                    .build();
        }else {
            return null;
        }

    }

    @Override
    public void sendEmail(AuthorDto authorDto) {



        if(authorDto.getId()!=null && !Objects.equals(authorDto.getEmail(), authorRepo.getById(authorDto.getId()).getEmail())){
            SimpleMailMessage message = new SimpleMailMessage();
            authorDto.setMessage("Your email has been changed.");
            message.setFrom(fromName);
            message.setTo(authorRepo.getById(authorDto.getId()).getEmail());
            message.setText(authorDto.getMessage());
            mailSender.send(message);
        }else if(authorDto.getId()!=null && Objects.equals(authorDto.getEmail(), authorRepo.getById(authorDto.getId()).getEmail())){
            SimpleMailMessage message1 = new SimpleMailMessage();
            authorDto.setMessage("Your credential has been changed.");
            message1.setFrom(fromName);
            message1.setTo(authorDto.getEmail());
            message1.setText(authorDto.getMessage());
            mailSender.send(message1);
        } else if(authorDto.getId()==null){
            SimpleMailMessage message2 = new SimpleMailMessage();
            message2.setFrom(fromName);
        message2.setTo(authorDto.getEmail());
        authorDto.setMessage("Your account has been created succesfully.");
        message2.setText(authorDto.getMessage());
        mailSender.send(message2);
        }
        System.out.println("Successfully sent!!");
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Author> optionalAuthor=authorRepo.findById(id);
        if(optionalAuthor.isPresent()) {
            authorRepo.deleteById(id);
        }
    }

    @Override
    public AuthorDto getAuthorByEmail(String email) {
        Author author=authorRepo.getAuthorByEmail(email);
        return AuthorDto.builder().
                id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .email(author.getEmail())
                .mobileno(author.getMobileno())
                .build();


    }
}
