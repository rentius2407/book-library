package com.library.book.adapter.in.web.v1.mapper;

import com.library.book.adapter.in.web.v1.model.AuthorResource;
import com.library.book.domain.Author;

public interface AuthorResourceMapper {

    static AuthorResource map(Author author) {
        return new AuthorResource(author.name(), author.surname());
    }

    static Author map(AuthorResource author) {
        return new Author(author.getName(), author.getSurname());
    }
}
