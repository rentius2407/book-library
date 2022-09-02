package com.library.book.application.service;

public class BookDoesNotExistException extends RuntimeException {

    public BookDoesNotExistException(String message) {
        super(message);
    }
}
