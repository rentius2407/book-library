package com.library.book.application.service;

import com.library.book.BaseSimpleBookServiceMockCase;
import com.library.book.MockBookGenerator;
import com.library.book.domain.Book;
import com.library.book.domain.pagination.DefaultPage;
import com.library.book.domain.pagination.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

class SimpleBookServiceFindAllUnitTest extends BaseSimpleBookServiceMockCase {

    @Test
    void findAll_shouldReturnEmptyList() {
        int offset = 0;
        int limit = 5;

        when(this.bookRepository.findAll(offset, limit)).thenReturn(DefaultPage.empty());

        Page<Book> books = this.simpleBookService.findAll(offset, limit);
        Assertions.assertTrue(books.isEmpty());
    }

    @Test
    void findAll_shouldReturnFiveBooks() {
        int offset = 0;
        int limit = 5;

        Page<Book> mockBooks = MockBookGenerator.generatePageable(5);
        when(this.bookRepository.findAll(offset, limit)).thenReturn(mockBooks);

        Page<Book> books = this.simpleBookService.findAll(offset, limit);
        Assertions.assertEquals(5, books.getCount());
    }
}
