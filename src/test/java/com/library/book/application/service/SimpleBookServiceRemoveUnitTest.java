package com.library.book.application.service;

import com.library.book.BaseSimpleBookServiceMockCase;
import com.library.book.domain.Author;
import com.library.book.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class SimpleBookServiceRemoveUnitTest extends BaseSimpleBookServiceMockCase {

    @Test
    void remove_bookNotPresent_shouldThrowBookDoesNotExistException() {
        final String isbn = "77463-992823-11";

        when(this.bookRepository.findByISBN(isbn)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                BookDoesNotExistException.class,
                () -> this.simpleBookService.remove(isbn)
        );

        verify(this.bookRepository, times(0)).remove(isbn);
    }

    @Test
    void remove_shouldBeSuccessful() {
        final String isbn = "77463-992823-11";

        Book book = new Book("Test title", new Author("Test name", "Test surname"), isbn);
        when(this.bookRepository.findByISBN(book.isbn())).thenReturn(Optional.of(book));

        this.simpleBookService.remove(isbn);

        verify(this.bookRepository, times(1)).remove(isbn);
    }
}
