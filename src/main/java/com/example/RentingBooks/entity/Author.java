package com.example.RentingBooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_author")

public class Author implements Serializable {
    @Id
    @SequenceGenerator(name="author_sequence",initialValue = 1,sequenceName = "author_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_sequence")
    private Integer id;
    @Column(name="firstname",length = 100)
    private String firstname;
    @Column(name="lastname",length = 100)
    private String lastname;
    @Column(name="email",length = 100,unique = true)
    private String email;
    @Column(name="mobileno",length = 10,unique = true)
    private String mobileno;
    @ManyToMany(mappedBy = "authorList", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
}
