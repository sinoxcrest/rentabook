package com.example.RentingBooks.entity;

import com.example.RentingBooks.enums.RentType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_book_transaction")
public class BookTransaction implements Serializable {
    @Id
    @SequenceGenerator(name="book_transaction_sequence",initialValue = 1,sequenceName = "book_transaction_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_transaction_sequence")
    private Integer id;
    @Column(name="code",length = 100)
    private String code;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Column(name="fromdate",length = 100)
    private Date fromdate;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Column(name="todate",length = 100)
    private Date todate;
    @Enumerated(value = EnumType.STRING)
    private RentType rentType;
    @ManyToOne
    @JoinColumn(name="book_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_book_book_transaction"))
    private Book book;
    @ManyToOne
    @JoinColumn(name="member_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_member_book_transaction"))
    private Member member;
}
