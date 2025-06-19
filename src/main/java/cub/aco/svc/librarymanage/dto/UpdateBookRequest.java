package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cub.aco.svc.librarymanage.enums.BookStatus;
import cub.aco.svc.librarymanage.enums.Language;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UpdateBookRequest {

    @Size(max = 30, message = "英數字{max}位")
    @NotBlank(message = "必填")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "英數字30位")
    @JsonProperty("book_ISBN")
    private String bookIsbn;

    @NotNull(message = "必填選項 1-中文(繁體), 2-中文(簡體), 3-英文")
    @JsonProperty("book_language")
    private Language bookLanguage;

    @Size(max = 200, message = "文字{max}位(含中文)")
    @NotBlank(message = "必填")
    @JsonProperty("book_name")
    private String bookName;

    @Size(max = 200, message = "文字{max}位(含中文)")
    @JsonProperty("book_author")
    private String bookAuthor;

    @Size(max = 200, message = "文字{max}位(含中文)")
    @JsonProperty("book_publisher")
    private String bookPublisher;

    @JsonProperty("book_pub_date")
    private LocalDate bookPubDate;

    @NotNull(message = "必填")
    @JsonProperty("book_create_date")
    private LocalDate bookCreateDate;

    @NotNull(message = "必填 1-未借出, 2-已借出, 3-遺失或損毀")
    @JsonProperty("book_status")
    private BookStatus bookStatus;
}
