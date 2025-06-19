package cub.aco.svc.librarymanage.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BOOK_INFO")
public class Book {

    @Id
    @Column(name = "book_ISBN", length = 30)
    private String bookIsbn;

    @Column(name = "book_language", length = 2)
    private String bookLanguage;

    @Column(name = "book_name", length = 200)
    private String bookName;

    @Column(name = "book_author", length = 200)
    private String bookAuthor;

    @Column(name = "book_publisher", length = 200)
    private String bookPublisher;

    @Column(name = "book_pub_date")
    private LocalDate bookPubDate;

    @CreationTimestamp
    @Column(name = "book_create_date")
    private LocalDate bookCreateDate;

    @Column(name = "book_status", length = 2)
    private String bookStatus;

    @Column(name = "book_borrower_ID", length = 10)
    private String bookBorrowerId;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

}
