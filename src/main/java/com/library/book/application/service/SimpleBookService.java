package com.library.book.application.service;

import com.library.book.application.port.in.BookService;
import com.library.book.application.port.out.BookRepository;
import com.library.book.domain.Book;
import com.library.book.domain.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleBookService implements BookService {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleBookService.class);
    private static final String BOOK_NOT_FOUND_MESSAGE = "Book with ISBN {} does not exist.";

    private final BookRepository bookRepository;
    private final int defaultLimit;

    @Autowired
    public SimpleBookService(BookRepository bookRepository,
                             @Value("${book.search.default-limit}") int defaultLimit) {
        this.bookRepository = bookRepository;
        this.defaultLimit = defaultLimit;
    }

    @Override
    public Page<Book> findAll(int offset, int limit) {
        if (limit == 0) {
            LOG.warn("Limit not specified. Applying default limit of {}.", this.defaultLimit);
            limit = this.defaultLimit;
        }

        return this.bookRepository.findAll(offset, limit);
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        Optional<Book> bookLookup = this.bookRepository.findByISBN(isbn);
        if (bookLookup.isEmpty()) {
            LOG.warn(BOOK_NOT_FOUND_MESSAGE, isbn);
        }

        return bookLookup;
    }

    @Override
    public void register(Book book) throws DuplicateException {
        if (book == null) throw new IllegalArgumentException("Book required.");

        Optional<Book> bookExist = this.bookRepository.findByISBN(book.isbn());
        if (bookExist.isPresent()) {
            LOG.error("Book with ISBN {} already exists.", book.isbn());
            throw new DuplicateException("Unable to add book.");
        }

        this.bookRepository.register(book);
    }

    @Override
    public void update(String isbn, Book book) throws BookDoesNotExistException {
        Optional<Book> bookLookup = this.bookRepository.findByISBN(isbn);
        if (bookLookup.isEmpty()) {
            LOG.error(BOOK_NOT_FOUND_MESSAGE, book.isbn());
            throw new BookDoesNotExistException("Book is required.");
        }

        this.bookRepository.update(isbn, book);
    }

    @Override
    public void remove(String isbn) {
        Optional<Book> bookLookup = this.bookRepository.findByISBN(isbn);
        if (bookLookup.isEmpty()) {
            LOG.error(BOOK_NOT_FOUND_MESSAGE, isbn);
            throw new BookDoesNotExistException("Book is required.");
        }
        this.bookRepository.remove(isbn);
    }
}
