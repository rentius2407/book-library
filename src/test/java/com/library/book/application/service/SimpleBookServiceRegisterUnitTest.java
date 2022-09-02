package com.library.book.application.service;

import com.library.book.BaseSimpleBookServiceMockCase;
import com.library.book.MockBookGenerator;
import com.library.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class SimpleBookServiceRegisterUnitTest extends BaseSimpleBookServiceMockCase {

    @Test
    void register_null_shouldThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> this.simpleBookService.register(null)
        );

    }

    @Test
    void register_duplicateISBN_shouldThrowBookExistException() {
        Book book = MockBookGenerator.generate();

        when(this.bookRepository.findByISBN(book.isbn())).thenReturn(Optional.of(book));

        assertThrows(
                DuplicateException.class,
                () -> this.simpleBookService.register(book)
        );

    }

    @Test
    void register_shouldBeAdded() {
        Book book = MockBookGenerator.generate();

        when(this.bookRepository.findByISBN(book.isbn())).thenReturn(Optional.empty());

        this.simpleBookService.register(book);
        Mockito.verify(this.bookRepository, times(1)).register(book);
    }
}
