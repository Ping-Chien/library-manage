package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cub.aco.svc.librarymanage.enums.BookStatus;
import cub.aco.svc.librarymanage.enums.QueryType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class QueryBookRequest {

    @NotNull(message = "必填")
    @JsonProperty("query_type")
    private QueryType queryType;

    @Size(max = 30, message = "英數字{max}位")
    @JsonProperty("book_ISBN")
    private String bookIsbn;

    @JsonProperty("book_status")
    private BookStatus bookStatus;

    @Size(max = 200, message = "文字{max}位(含中文)")
    @JsonProperty("book_name")
    private String bookName;



}
