package com.library.book.adapter.in.web.v1.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookResource {

    @NotBlank
    private String title;
    @NotNull
    @Valid
    private AuthorResource author;
    @NotBlank
    private String isbn;

    public BookResource(String title, AuthorResource author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorResource getAuthor() {
        return author;
    }

    public void setAuthor(AuthorResource author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
