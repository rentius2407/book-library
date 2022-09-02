package com.library.book.application.service;

import com.library.book.BaseSimpleBookServiceMockCase;
import com.library.book.domain.Author;
import com.library.book.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.when;

class SimpleBookServiceFindByISBNUnitTest extends BaseSimpleBookServiceMockCase {

    @Test
    void findByISBN_shouldReturnEmpty() {
        final String isbn = "992-18827-1233";

        when(this.bookRepository.findByISBN(isbn)).thenReturn(Optional.empty());

        Optional<Book> bookLookup = this.simpleBookService.findByISBN(isbn);
        Assertions.assertTrue(bookLookup.isEmpty());
    }

    @Test
    void findByISBN_shouldReturnOne() {
        final String isbn = "992-18827-1233";

        Book book = new Book("Test title", new Author("Test name", "Test surname"), isbn);
        when(this.bookRepository.findByISBN(isbn)).thenReturn(Optional.of(book));

        Optional<Book> bookLookup = this.simpleBookService.findByISBN(isbn);
        Assertions.assertTrue(bookLookup.isPresent());
        Assertions.assertEquals(isbn, bookLookup.get().isbn());

    }
}
