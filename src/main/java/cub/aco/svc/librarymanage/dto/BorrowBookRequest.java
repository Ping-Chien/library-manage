package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class BorrowBookRequest {

    @Size(max = 30, message = "英數字{max}位")
    @NotBlank(message = "必填")
    @JsonProperty("book_ISBN")
    private String bookIsbn;

    @Size(max = 10, message = "英數字{max}位")
    @NotBlank(message = "必填")
    @JsonProperty("book_borrower_ID")
    private String bookBorrowerId;
}
