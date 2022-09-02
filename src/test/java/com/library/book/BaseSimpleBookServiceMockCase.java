package com.library.book;

import com.library.book.application.port.out.BookRepository;
import com.library.book.application.service.SimpleBookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class BaseSimpleBookServiceMockCase {

    private AutoCloseable autoCloseable;

    @Mock
    protected BookRepository bookRepository;

    protected SimpleBookService simpleBookService;

    @BeforeEach
    public void beforeEach() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.simpleBookService = new SimpleBookService(this.bookRepository, 10);
    }

    @AfterEach
    public void afterEach() throws Exception {
        this.autoCloseable.close();
    }
}
