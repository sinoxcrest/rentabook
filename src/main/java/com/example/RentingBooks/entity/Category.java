package com.example.RentingBooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_category")
public class Category implements Serializable {
    @Id
    @SequenceGenerator(name="category_sequence",initialValue = 1,sequenceName = "category_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_sequence")
    private Integer id;
    @Column(name="name",length = 100)
    private String name;
    @Column(name="description",length = 255)
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
}
