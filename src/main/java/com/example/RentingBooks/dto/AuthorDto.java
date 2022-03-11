package com.example.RentingBooks.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Integer id;
    @NotEmpty(message = "User's first name cannot be empty.")
    private String firstname;
    @NotEmpty(message = "User's last name cannot be empty.")
    private String lastname;
    @NotEmpty(message = "User's email cannot be empty.")
    @Email(message = "Email id must be valid.")
    private String email;
    @NotEmpty(message = "The mobile number shouldn't be empty.")
    @Size(max = 10,min = 10,message = "The mobile number should be of 10 digit and shouldnt exceed.")
    private String mobileno;
    private String message;
}
