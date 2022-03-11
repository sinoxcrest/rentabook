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
public class MemberDto {
    private Integer id;
    @NotEmpty(message = "Member's name must not be empty.")
    private String name;
    @NotEmpty(message = "Member's address must not be empty.")
    private String address;
    @NotEmpty(message = "Member's email must not be empty.")
    @Email(message = "Member's email must be valid.")
    private String email;
    @NotEmpty(message = "Member's mobile number must not be empty.")
    @Size(max = 10,min = 10,message = "The mobile number should be of 10 digit and shouldnt exceed.")
    private String mobileno;
}
