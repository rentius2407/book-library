package com.library.book.adapter.in.web.v1;

import com.library.book.adapter.in.web.v1.model.AuthorResource;
import com.library.book.adapter.in.web.v1.model.BookResource;
import com.library.book.application.port.in.BookService;
import com.library.book.application.service.BookDoesNotExistException;
import com.library.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookV1Controller.class)
class BookV1ControllerUpdateUnitTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    final Supplier<BookResource> updateBookSupplier = () -> new BookResource(
            "The Girl With the Dragon Tattoo",
            new AuthorResource("Stieg", "Larsson"),
            "93866217091"
    );

    @Test
    void update_bookUpdated_shouldBeNoContent() throws Exception {
        BookResource bookResource = updateBookSupplier.get();

        this.mockMvc.perform(
                        put("/v1/books/{isbn}", bookResource.getIsbn())
                                .content(BookToJsonMapper.convert(bookResource))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(this.bookService, times(1)).update(anyString(), any(Book.class));
    }

    @Test
    void update_noBookFound_shouldBeNotFound() throws Exception {
        doThrow(BookDoesNotExistException.class).when(this.bookService).update(anyString(), any(Book.class));

        BookResource bookResource = updateBookSupplier.get();

        this.mockMvc.perform(
                        put("/v1/books/{isbn}", bookResource.getIsbn())
                                .content(BookToJsonMapper.convert(bookResource))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        verify(this.bookService, times(1)).update(anyString(), any(Book.class));
    }

    @Test
    void add_emptyAuthorShouldBeBadRequest() throws Exception {
        BookResource bookResource = updateBookSupplier.get();
        bookResource.setAuthor(null);

        this.mockMvc.perform(
                        put("/v1/books/{isbn}", bookResource.getIsbn())
                                .content(BookToJsonMapper.convert(bookResource))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

        verify(this.bookService, times(0)).register(any(Book.class));
    }
}
