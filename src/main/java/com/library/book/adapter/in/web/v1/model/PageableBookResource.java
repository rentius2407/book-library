package com.library.book.adapter.in.web.v1.model;

import java.util.List;

public class PageableBookResource {

    private List<BookResource> books;
    private int count;
    private int offset;
    private int limit;
    private int total;

    public PageableBookResource(List<BookResource> books, int offset, int limit, int total) {
        this.books = books;
        this.count = books.size();
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }

    public static PageableBookResource of(List<BookResource> books, int offset, int limit, int total) {
        return new PageableBookResource(books, offset, limit, total);
    }

    public List<BookResource> getBooks() {
        return books;
    }

    public void setBooks(List<BookResource> books) {
        this.books = books;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
