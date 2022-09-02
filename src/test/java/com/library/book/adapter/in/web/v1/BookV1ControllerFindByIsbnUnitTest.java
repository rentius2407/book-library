package com.library.book.adapter.in.web.v1;

import com.library.book.application.port.in.BookService;
import com.library.book.domain.Author;
import com.library.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookV1Controller.class)
class BookV1ControllerFindByIsbnUnitTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void findByISBN_shouldReturnOne() throws Exception {
        Book book = new Book("Test", new Author("Test name", "Test surname"), "111111222222");
        when(this.bookService.findByISBN(book.isbn())).thenReturn(Optional.of(book));

        this.mockMvc.perform(get("/v1/books/{isbn}", book.isbn()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(book.isbn()));
    }

    @Test
    void findByISBN_noBookFound_shouldBeNoContent() throws Exception {
        when(this.bookService.findByISBN(anyString())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/v1/books/{isbn}", "111111222222"))
                .andExpect(status().isNoContent());
    }
}
