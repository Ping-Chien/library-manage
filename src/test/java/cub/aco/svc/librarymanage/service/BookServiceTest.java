package cub.aco.svc.librarymanage.service;

import cub.aco.svc.librarymanage.dto.*;
import cub.aco.svc.librarymanage.entity.Book;
import cub.aco.svc.librarymanage.entity.BorrowLog;
import cub.aco.svc.librarymanage.enums.BookStatus;
import cub.aco.svc.librarymanage.enums.Language;
import cub.aco.svc.librarymanage.enums.QueryType;
import cub.aco.svc.librarymanage.exception.ErrorException;
import cub.aco.svc.librarymanage.repository.BookRepository;
import cub.aco.svc.librarymanage.repository.BorrowLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Example;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    BookRepository bookRepository;
    @Mock
    BorrowLogRepository borrowLogRepository;
    @InjectMocks
    BookService bookService;
    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(bookService, "borrowDays", 40);
    }

    @Test
    void testCreate() {
        final AddBookRequest request = new AddBookRequest();
        request.setBookIsbn("bookIsbn");
        request.setBookLanguage(Language.CH);
        request.setBookName("bookName");
        request.setBookAuthor("bookAuthor");
        request.setBookPublisher("bookPublisher");
        request.setBookPubDate(LocalDate.now());

        bookService.create(request);

        verify(bookRepository).save(bookCaptor.capture());
        final Book book = bookCaptor.getValue();
        Assertions.assertEquals("1", book.getBookStatus());
        Assertions.assertEquals("2", book.getBookLanguage());
    }

    @Test
    void testCreate_throwsErrorException() {
        final AddBookRequest request = new AddBookRequest();
        request.setBookIsbn("bookIsbn");
        request.setBookLanguage(Language.CH);
        request.setBookName("bookName");
        request.setBookAuthor("bookAuthor");
        request.setBookPublisher("bookPublisher");
        request.setBookPubDate(LocalDate.of(2020, 1, 1));
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("bookLanguage")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("bookStatus")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("bookIsbn")).thenReturn(book);

        assertThatThrownBy(() -> bookService.create(request)).isInstanceOf(ErrorException.class).hasMessage("資料已存在");
    }

    @Test
    void testUpdate() {
        final UpdateBookRequest request = new UpdateBookRequest();
        request.setBookIsbn("bookIsbn");
        request.setBookLanguage(Language.CH);
        request.setBookName("bookName");
        request.setBookAuthor("bookAuthor");
        request.setBookPublisher("bookPublisher");
        request.setBookPubDate(LocalDate.of(2020, 1, 1));
        request.setBookCreateDate(LocalDate.of(2020, 1, 1));
        request.setBookStatus(BookStatus.IN);
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("bookLanguage")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("bookStatus")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("bookIsbn")).thenReturn(book);

        bookService.update(request);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testUpdate_BookRepositoryFindByIdReturnsAbsent() {
        final UpdateBookRequest request = new UpdateBookRequest();
        request.setBookIsbn("bookIsbn");
        request.setBookLanguage(Language.ZH);
        request.setBookName("bookName");
        request.setBookAuthor("bookAuthor");
        request.setBookPublisher("bookPublisher");
        request.setBookPubDate(LocalDate.of(2020, 1, 1));
        request.setBookCreateDate(LocalDate.of(2020, 1, 1));
        request.setBookStatus(BookStatus.IN);

        when(bookRepository.findById("bookIsbn")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(request)).isInstanceOf(ErrorException.class).hasMessage("資料不存在");
    }

    @Test
    void testDelete() {
        final DeleteBookRequest request = new DeleteBookRequest();
        request.setBookIsbn("bookIsbn");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("bookLanguage")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("bookStatus")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("bookIsbn")).thenReturn(book);

        bookService.delete(request);

        verify(bookRepository).delete(any(Book.class));
    }

    @Test
    void testDelete_BookRepositoryFindByIdReturnsAbsent() {
        final DeleteBookRequest request = new DeleteBookRequest();
        request.setBookIsbn("bookIsbn");

        when(bookRepository.findById("bookIsbn")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.delete(request)).isInstanceOf(ErrorException.class).hasMessage("資料不存在");
    }

    @Test
    void testQueryWithIsbn() {
        final QueryBookRequest request = new QueryBookRequest();
        request.setQueryType(QueryType.BOOK_ISBN);
        request.setBookIsbn("bookIsbn");
        final List<BookInfo> expectedResult = List.of(BookInfo.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage(Language.ZH)
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus(BookStatus.IN)
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        final List<Book> books = List.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("1")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findAll(any(Example.class))).thenReturn(books);

        final List<BookInfo> result = bookService.query(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testQueryWithStatus() {
        final QueryBookRequest request = new QueryBookRequest();
        request.setQueryType(QueryType.BOOK_STATUS);
        request.setBookStatus(BookStatus.IN);
        final List<BookInfo> expectedResult = List.of(BookInfo.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage(Language.ZH)
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus(BookStatus.IN)
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        final List<Book> books = List.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("1")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findAll(any(Example.class))).thenReturn(books);

        final List<BookInfo> result = bookService.query(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testQueryWithName() {
        final QueryBookRequest request = new QueryBookRequest();
        request.setQueryType(QueryType.BOOK_NAME);
        request.setBookName("bookName");
        final List<BookInfo> expectedResult = List.of(BookInfo.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage(Language.ZH)
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus(BookStatus.IN)
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        final List<Book> books = List.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("1")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findByBookNameContaining("bookName")).thenReturn(books);

        final List<BookInfo> result = bookService.query(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testBorrow() {
        final BorrowBookRequest request = new BorrowBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("1")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("borrowBookIsbn")).thenReturn(book);

        bookService.borrow(request);

        verify(bookRepository).save(any(Book.class));
        verify(borrowLogRepository).save(any(BorrowLog.class));
    }

    @Test
    void testBorrow_BookRepositoryFindByIdReturnsAbsent() {
        final BorrowBookRequest request = new BorrowBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");

        when(bookRepository.findById("borrowBookIsbn")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.borrow(request)).isInstanceOf(ErrorException.class).hasMessage("資料不存在");
    }

    @Test
    void testBorrow_returnBorrowOut() {
        final BorrowBookRequest request = new BorrowBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("2")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("borrowBookIsbn")).thenReturn(book);

        assertThatThrownBy(() -> bookService.borrow(request)).isInstanceOf(ErrorException.class).hasMessage("該書已借出");
    }

    @Test
    void testBorrow_returnBookLost() {
        final BorrowBookRequest request = new BorrowBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("3")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("borrowBookIsbn")).thenReturn(book);

        assertThatThrownBy(() -> bookService.borrow(request)).isInstanceOf(ErrorException.class).hasMessage("資料不存在");
    }

    @Test
    void testReturnBook() {
        final ReturnBookRequest request = new ReturnBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("bookLanguage")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("2")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("borrowBookIsbn")).thenReturn(book);

        bookService.returnBook(request);

        verify(bookRepository).save(any(Book.class));
        verify(borrowLogRepository).save(any(BorrowLog.class));
    }

    @Test
    void testReturnBook_returnBookAlreadyReturn() {
        final ReturnBookRequest request = new ReturnBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("bookLanguage")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("1")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("borrowBookIsbn")).thenReturn(book);

        assertThatThrownBy(() -> bookService.returnBook(request)).isInstanceOf(ErrorException.class).hasMessage("該書已歸還");
    }

    @Test
    void testReturnBook_returnBookNotExists() {
        final ReturnBookRequest request = new ReturnBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");
        final Optional<Book> book = Optional.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("bookLanguage")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("3")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.of(2020, 1, 1))
                .build());
        when(bookRepository.findById("borrowBookIsbn")).thenReturn(book);

        assertThatThrownBy(() -> bookService.returnBook(request)).isInstanceOf(ErrorException.class).hasMessage("資料不存在");
    }

    @Test
    void testReturnBook_BookRepositoryFindByIdReturnsAbsent() {
        final ReturnBookRequest request = new ReturnBookRequest();
        request.setBookIsbn("borrowBookIsbn");
        request.setBookBorrowerId("bookBorrowerId");

        when(bookRepository.findById("borrowBookIsbn")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.returnBook(request)).isInstanceOf(ErrorException.class).hasMessage("資料不存在");
    }

    @Test
    void testQueryOverdueBooks() {
        final QueryOverdueBookRequest request = new QueryOverdueBookRequest();
        request.setOverdue(1);
        final List<BookInfo> expectedResult = List.of(BookInfo.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage(Language.ZH)
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus(BookStatus.OUT)
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.now().minusDays(41))
                .overdueDays(2L)
                .build());
        final List<Book> books = List.of(Book.builder()
                .bookIsbn("bookIsbn")
                .bookLanguage("1")
                .bookName("bookName")
                .bookAuthor("bookAuthor")
                .bookPublisher("bookPublisher")
                .bookPubDate(LocalDate.of(2020, 1, 1))
                .bookCreateDate(LocalDate.of(2020, 1, 1))
                .bookStatus("2")
                .bookBorrowerId("bookBorrowerId")
                .borrowDate(LocalDate.now().minusDays(41))
                .build());
        when(bookRepository.findByBookStatusAndBorrowDateBeforeOrderByBorrowDateAscBookBorrowerId(any(), any())).thenReturn(books);

        final List<BookInfo> result = bookService.queryOverdueBooks(request);

        assertThat(result).isEqualTo(expectedResult);
    }
}
