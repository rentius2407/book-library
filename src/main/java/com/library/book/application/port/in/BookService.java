package com.library.book.application.port.in;

import com.library.book.application.service.BookDoesNotExistException;
import com.library.book.application.service.DuplicateException;
import com.library.book.domain.Book;
import com.library.book.domain.pagination.Page;

import java.util.Optional;

public interface BookService {

    /**
     * A search that will return all {@code Book}s using offset and limit
     *
     * @param offset the starting position. Default is 0
     * @param limit  the amount of {@code Book}s to return. Default is 10
     * @return a list of {@code Book}s or empty list
     */
    Page<Book> findAll(int offset, int limit);

    /**
     * Method to Add a {@code Book}
     *
     * @param book the {@code Book} being added
     * @throws DuplicateException when the {@code Book} already exists
     */
    void register(Book book) throws DuplicateException;

    /**
     * Find a {@code Book} by its ISBN
     *
     * @param isbn the ISBN
     * @return a book or empty {@code Optional}
     */
    Optional<Book> findByISBN(String isbn);

    /**
     * Update a {@code Book}
     *
     * @param isbn to identify the {@code Book} to update
     * @param book the {@code Book} content
     * @throws BookDoesNotExistException when the {@code Book} is not found
     */
    void update(String isbn, Book book) throws BookDoesNotExistException;

    /**
     * Remove a {@code Book}
     * @param isbn the isbn for the {@code Book} to remove
     */
    void remove(String isbn);
}
