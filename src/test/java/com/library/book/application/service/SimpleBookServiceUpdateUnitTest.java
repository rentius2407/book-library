package com.library.book.application.service;

import com.library.book.BaseSimpleBookServiceMockCase;
import com.library.book.domain.Author;
import com.library.book.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class SimpleBookServiceUpdateUnitTest extends BaseSimpleBookServiceMockCase {

    @Test
    void update_bookNotPresent_shouldThrowBookDoesNotExistException() {
        final String isbn = "77463-992823-11";

        when(this.bookRepository.findByISBN(isbn)).thenReturn(Optional.empty());

        Book book = new Book("Test title", new Author("Test name", "Test surname"), isbn);
        Assertions.assertThrows(
                BookDoesNotExistException.class,
                () -> this.simpleBookService.update(isbn, book)
        );

        verify(this.bookRepository, times(0)).update(isbn, book);
    }

    @Test
    void update_shouldBeSuccessful() {
        Book book = new Book("Test title", new Author("Test name", "Test surname"), "77463-992823-11");
        when(this.bookRepository.findByISBN(book.isbn())).thenReturn(Optional.of(book));

        this.simpleBookService.update(book.isbn(), book);

        verify(this.bookRepository, times(1)).update(book.isbn(), book);
    }
}
