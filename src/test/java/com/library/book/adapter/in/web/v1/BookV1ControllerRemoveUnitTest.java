package com.library.book.adapter.in.web.v1;

import com.library.book.application.port.in.BookService;
import com.library.book.application.service.BookDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookV1Controller.class)
class BookV1ControllerRemoveUnitTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void remove_successful_shouldBeNoContent() throws Exception {
        final String isbn = "4487-8569-52";
        this.mockMvc.perform(
                        delete("/v1/books/{isbn}", isbn)
                )
                .andExpect(status().isNoContent());

        verify(this.bookService, times(1)).remove(isbn);
    }

    @Test
    void remove_noBookFound_shouldBeNotFound() throws Exception {
        final String isbn = "4487-8569-52";
        doThrow(BookDoesNotExistException.class).when(this.bookService).remove(isbn);

        this.mockMvc.perform(
                        delete("/v1/books/{isbn}", isbn)
                )
                .andExpect(status().isNotFound());

        verify(this.bookService, times(1)).remove(isbn);
    }
}
