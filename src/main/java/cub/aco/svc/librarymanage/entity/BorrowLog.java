package cub.aco.svc.librarymanage.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BORROW_LOG")
public class BorrowLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "borrow_serial_no")
    private Integer borrowSerialNo;

    @Column(name = "borrower_ID", length = 10)
    private String borrowerId;

    @Column(name = "borrow_book_ISBN", length = 30)
    private String borrowBookIsbn;

    @Column(name = "borrow_action", length = 2)
    private String borrowAction;

    @CreationTimestamp
    @Column(name = "action_date")
    private LocalDate actionDate;

}
