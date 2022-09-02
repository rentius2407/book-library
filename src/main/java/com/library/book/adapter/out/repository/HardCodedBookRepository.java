package com.library.book.adapter.out.repository;

import com.library.book.application.port.out.BookRepository;
import com.library.book.domain.Author;
import com.library.book.domain.Book;
import com.library.book.domain.pagination.DefaultPage;
import com.library.book.domain.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HardCodedBookRepository implements BookRepository {

    private final Map<String, Book> cachedBooks = new ConcurrentHashMap<>();

    public HardCodedBookRepository() {
        initBooks();
    }

    @Override
    public Page<Book> findAll(int offset, int limit) {
        if (offset >= this.cachedBooks.size() || limit == 0) {
            return DefaultPage.empty();
        }

        List<Book> sortedBooks = this.cachedBooks.values()
                .stream()
                .sorted(Comparator.comparing(Book::isbn))
                .toList();

        List<Book> books = new ArrayList<>();

        int counter = limit;
        for (int i = offset; i < sortedBooks.size(); i++) {
            books.add(sortedBooks.get(i));
            if (--counter <= 0) break;
        }

        return new DefaultPage<>(
                books,
                offset,
                limit,
                this.cachedBooks.size()
        );
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        Book book = this.cachedBooks.get(isbn);
        return Optional.ofNullable(book);
    }

    @Override
    public void register(Book book) {
        this.cachedBooks.put(book.isbn(), book);
    }

    @Override
    public void update(String isbn, Book book) {
        this.cachedBooks.remove(isbn);
        this.cachedBooks.put(book.isbn(), book);
    }

    @Override
    public void remove(String isbn) {
        this.cachedBooks.remove(isbn);
    }

    private void initBooks() {
        Book toKillAMockingBird = new Book("To Kill a Mockingbird", new Author("Harper", "Lee"), "0446310786");
        cachedBooks.put(toKillAMockingBird.isbn(), toKillAMockingBird);

        Book theShining = new Book("The Shining", new Author("Stephen", "King"), "1432310111");
        cachedBooks.put(theShining.isbn(), theShining);

        Book theFellowshipOfTheRing = new Book("The Fellowship of the Ring", new Author("J.R.R", "Tolkien"), "1046322799");
        cachedBooks.put(theFellowshipOfTheRing.isbn(), theFellowshipOfTheRing);

        Book dune = new Book("Dune", new Author("Frank", "Herbert"), "9987421777");
        cachedBooks.put(dune.isbn(), dune);

        Book catch22 = new Book("Catch-22", new Author("Joseph", "Heller"), "069512545");
        cachedBooks.put(catch22.isbn(), catch22);
    }
}
