package com.library.book.adapter.in.web.v1;

import com.library.book.MockBookGenerator;
import com.library.book.domain.Book;
import com.library.book.application.port.in.BookService;
import com.library.book.domain.pagination.DefaultPage;
import com.library.book.domain.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookV1Controller.class)
class BookV1ControllerFindAllUnitTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void findAll_emptyBooks() throws Exception {
        int offset = 0;
        int limit = 10;

        when(this.bookService.findAll(offset, limit)).thenReturn(DefaultPage.empty());

        this.mockMvc.perform(get("/v1/books")
                        .param("offset", offset + "")
                        .param("limit", limit + ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(0));
    }

    @Test
    void findAll_shouldReturnThree() throws Exception {
        int offset = 0;
        int limit = 10;

        Page<Book> generatedBooks = MockBookGenerator.generatePageable(3);
        when(this.bookService.findAll(offset, limit)).thenReturn(generatedBooks);

        this.mockMvc.perform(get("/v1/books")
                        .param("offset", offset + "")
                        .param("limit", limit + ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(3))
                .andExpect(jsonPath("$.count").value(3));
    }
}
