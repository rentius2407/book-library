package com.library.book.adapter.in.web.v1.mapper;

import com.library.book.adapter.in.web.v1.model.BookResource;
import com.library.book.domain.Book;

public interface BookResourceMapper {

    static BookResource map(Book book) {
        return new BookResource(
                book.title(),
                AuthorResourceMapper.map(book.author()),
                book.isbn()
        );
    }

    static Book map(BookResource bookResource) {
        return new Book(
                bookResource.getTitle(),
                AuthorResourceMapper.map(bookResource.getAuthor()),
                bookResource.getIsbn()
        );
    }
}
