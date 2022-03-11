package com.example.RentingBooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_book")
public class Book implements Serializable {
    @Id
    @SequenceGenerator(name="book_sequence",initialValue = 1,sequenceName = "book_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_sequence")
    private Integer id;
    @Column(name="name",length = 100)
    private String name;
    @Column(name="noofpages",length = 100)
    private Integer noofpages;
    @Column(name="isbn",length = 100,unique = true)
    private String isbn;
    @Column(name="rating",length = 100)
    private Integer rating;
    @Column(name="stockcount",length = 100)
    private Integer stockcount;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Column(name="publisheddate",length = 100)
    private Date publisheddate;
    @Column(name="filepath",length = 100)
    private String filepath;
    @ManyToOne
    @JoinColumn(name="category_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_book_category"))
    private Category category;
    @ManyToMany
    @JoinTable(name = "authors_book", joinColumns =@JoinColumn(name="book_id"),
            inverseJoinColumns =@JoinColumn(name= "author_id"))
    private List<Author> authorList;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookTransaction> bookTransactions = new HashSet<>();

}
