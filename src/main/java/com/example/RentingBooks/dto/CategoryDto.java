package com.example.RentingBooks.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotEmpty(message="Name must be Filled.")
    private String name;
    @NotEmpty(message="Description must be Filled.")
    private String description;



}
