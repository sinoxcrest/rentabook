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
@Table(name="tbl_member")
public class Member implements Serializable {
    @Id
    @SequenceGenerator(name="member_sequence",initialValue = 1,sequenceName = "member_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_sequence")
    private Integer id;
    @Column(name="name",length = 100)
    private String name;
    @Column(name="address",length = 100)
    private String address;
    @Column(name="email",length = 100,unique = true)
    private String email;
    @Column(name="mobileno",length = 10,unique = true)
    private String mobileno;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookTransaction> bookTransactions = new HashSet<>();
}
