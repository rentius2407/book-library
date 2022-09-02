package com.library.book.adapter.in.web.v1;

import com.library.book.adapter.in.web.v1.model.AuthorResource;
import com.library.book.adapter.in.web.v1.model.BookResource;
import com.library.book.application.port.in.BookService;
import com.library.book.application.service.DuplicateException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookV1Controller.class)
class BookV1ControllerRegisterUnitTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    final Supplier<BookResource> newBookSupplier = () -> new BookResource(
            "The Girl With the Dragon Tattoo",
            new AuthorResource("Stieg", "Larsson"),
            "93866217091"
    );

    @Test
    void register_shouldBeSuccessful() throws Exception {
        this.mockMvc.perform(
                        post("/v1/books")
                                .content(BookToJsonMapper.convert(newBookSupplier.get()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        verify(this.bookService, times(1)).register(any(Book.class));
    }

    @Test
    void register_emptyTitleShouldBeBadRequest() throws Exception {
        BookResource bookResource = newBookSupplier.get();
        bookResource.setTitle(null);

        this.mockMvc.perform(
                        post("/v1/books")
                                .content(BookToJsonMapper.convert(bookResource))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

        verify(this.bookService, times(0)).register(any(Book.class));
    }

    @Test
    void register_emptyAuthorShouldBeBadRequest() throws Exception {
        BookResource bookResource = newBookSupplier.get();
        bookResource.setAuthor(null);

        this.mockMvc.perform(
                        post("/v1/books")
                                .content(BookToJsonMapper.convert(bookResource))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

        verify(this.bookService, times(0)).register(any(Book.class));
    }

    @Test
    void register_duplicateShouldBeConflict() throws Exception {
        doThrow(new DuplicateException("Unit test exception")).when(this.bookService).register(any(Book.class));

        this.mockMvc.perform(
                        post("/v1/books")
                                .content(BookToJsonMapper.convert(newBookSupplier.get()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());

        verify(this.bookService, times(1)).register(any(Book.class));
    }


}
