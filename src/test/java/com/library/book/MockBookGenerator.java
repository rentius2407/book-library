package com.library.book;

import com.library.book.domain.Author;
import com.library.book.domain.Book;
import com.library.book.domain.pagination.DefaultPage;
import com.library.book.domain.pagination.Page;

import java.util.ArrayList;
import java.util.List;

public final class MockBookGenerator {

    public static Page<Book> generatePageable(int numberOfBooks) {
        List<Book> mockBooks = generate(numberOfBooks);
        return new DefaultPage<>(
                mockBooks,
                0,
                10,
                mockBooks.size()
        );
    }

    public static Book generate() {
        List<Book> books = generate(1);
        return books.iterator().next();
    }

    private static List<Book> generate(int numberOfBooks) {
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < numberOfBooks; i++) {
            Author testAuthor = new Author("Name " + i, "Surname " + i);
            Book testBook = new Book("Title " + i, testAuthor, "ISBN " + i);
            books.add(testBook);
        }

        return books;
    }
}
