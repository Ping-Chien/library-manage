package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class DeleteBookRequest {

    @Size(max = 30, message = "英數字{max}位")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "英數字30位")
    @NotBlank(message = "必填")
    @JsonProperty("book_ISBN")
    private String bookIsbn;
}
