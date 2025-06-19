package cub.aco.svc.librarymanage.controller;

import cub.aco.svc.librarymanage.dto.*;
import cub.aco.svc.librarymanage.enums.ResponseCode;
import cub.aco.svc.librarymanage.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequestMapping("/book")
@RestController
@RequiredArgsConstructor
public class BookController {

    final BookService bookService;

    @ResponseBody
    @PostMapping(value = "/add")
    public ActionResponse addBook(@Valid @RequestBody AddBookRequest request) {

        bookService.create(request);

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                        .returnCode(ResponseCode.SUCCESS.getCode())
                        .returnDesc(ResponseCode.SUCCESS.getDescription())
                .build()).build();
    }

    @ResponseBody
    @PostMapping(value = "/modify")
    public ActionResponse modifyBook(@Valid @RequestBody UpdateBookRequest request) {

        bookService.update(request);

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.SUCCESS.getCode())
                .returnDesc(ResponseCode.SUCCESS.getDescription())
                .build()).build();
    }

    @ResponseBody
    @PostMapping(value = "/delete")
    public ActionResponse deleteBook(@Valid @RequestBody DeleteBookRequest request) {

        bookService.delete(request);

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.SUCCESS.getCode())
                .returnDesc(ResponseCode.SUCCESS.getDescription())
                .build()).build();
    }

    @ResponseBody
    @PostMapping(value = "/query")
    public QueryBookResponse queryBook(@Valid @RequestBody QueryBookRequest request) {

        final List<BookInfo> books = bookService.query(request);

        return QueryBookResponse.builder()
                .mwHeader(ActionDetail.builder()
                        .returnCode(ResponseCode.SUCCESS.getCode())
                        .returnDesc(ResponseCode.SUCCESS.getDescription())
                        .build())
                .bookCount(books.size())
                .bookList(books).build();
    }

    @ResponseBody
    @PostMapping(value = "/borrow")
    public ActionResponse borrowBook(@Valid @RequestBody BorrowBookRequest request) {

        bookService.borrow(request);

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.SUCCESS.getCode())
                .returnDesc(ResponseCode.SUCCESS.getDescription())
                .build()).build();
    }

    @ResponseBody
    @PostMapping(value = "/return")
    public ActionResponse returnBook(@Valid @RequestBody ReturnBookRequest request) {

        bookService.returnBook(request);

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.SUCCESS.getCode())
                .returnDesc(ResponseCode.SUCCESS.getDescription())
                .build()).build();
    }

    @ResponseBody
    @PostMapping(value = "/overdue")
    public QueryOverdueBookResponse queryOverdueBook(@Valid @RequestBody QueryOverdueBookRequest request) {

        final List<BookInfo> books = bookService.queryOverdueBooks(request);

        return QueryOverdueBookResponse.builder()
                .mwHeader(ActionDetail.builder()
                        .returnCode(ResponseCode.SUCCESS.getCode())
                        .returnDesc(ResponseCode.SUCCESS.getDescription())
                        .build())
                .queryCount(books.size())
                .queryList(books).build();
    }
}
