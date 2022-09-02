package com.library.book.adapter.out.repository;

import com.library.book.domain.Book;
import com.library.book.domain.pagination.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HardCodedBookRepositoryFindAllUnitTest {

    private HardCodedBookRepository hardCodedBookRepository;

    @BeforeEach
    void beforeEach() {
        this.hardCodedBookRepository = new HardCodedBookRepository();
    }

    @Test
    void findAll_offsetGreaterShouldReturnEmpty() {
        Page<Book> pageableBooks = this.hardCodedBookRepository.findAll(6, 10);
        Assertions.assertTrue(pageableBooks.isEmpty());
    }

    @Test
    void findAll_limitZeroShouldReturnEmpty() {
        Page<Book> pageableBooks = this.hardCodedBookRepository.findAll(6, 0);
        Assertions.assertTrue(pageableBooks.isEmpty());
    }

    @Test
    void findAll_shouldReturnOne() {
        Page<Book> pageableBooks = this.hardCodedBookRepository.findAll(2, 1);
        Assertions.assertEquals(1, pageableBooks.getCount());
    }

    @Test
    void findAll_shouldReturnFive() {
        Page<Book> pageableBooks = this.hardCodedBookRepository.findAll(0, 6);
        Assertions.assertEquals(5, pageableBooks.getCount());
    }
    @Test
    void findAll_shouldReturnTwo() {
        Page<Book> pageableBooks = this.hardCodedBookRepository.findAll(3, 2);
        Assertions.assertEquals(2, pageableBooks.getCount());
    }
}
