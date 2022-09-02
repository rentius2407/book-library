package com.library.book.application.port.out;

import com.library.book.domain.Book;
import com.library.book.domain.pagination.Page;

import java.util.Optional;

public interface BookRepository {

    Page<Book> findAll(int offset, int limit);

    Optional<Book> findByISBN(String isbn);

    void register(Book book);

    void update(String isbn, Book book);

    void remove(String isbn);
}
