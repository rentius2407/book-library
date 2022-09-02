package com.library.book.adapter.in.web.v1;

import com.library.book.adapter.in.web.v1.mapper.BookResourceMapper;
import com.library.book.adapter.in.web.v1.model.BookResource;
import com.library.book.adapter.in.web.v1.model.PageableBookResource;
import com.library.book.application.port.in.BookService;
import com.library.book.domain.Book;
import com.library.book.domain.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/books")
public class BookV1Controller {

    private final BookService bookService;

    @Autowired
    public BookV1Controller(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public PageableBookResource findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {

        Page<Book> bookPage = this.bookService.findAll(offset, limit);

        List<BookResource> books = bookPage.getContent()
                .stream()
                .map(BookResourceMapper::map)
                .toList();

        return PageableBookResource.of(books, bookPage.getOffset(), bookPage.getLimit(), bookPage.getTotal());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResource> findByISBN(@PathVariable("isbn") String isbn) {
        Optional<Book> bookLookup = this.bookService.findByISBN(isbn);
        return bookLookup
                .map(book -> ResponseEntity.ok(BookResourceMapper.map(book)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid BookResource bookResource) {
        Book book = BookResourceMapper.map(bookResource);
        this.bookService.register(book);
    }

    @PutMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("isbn") String isbn, @RequestBody @Valid BookResource bookResource) {
        Book book = BookResourceMapper.map(bookResource);
        this.bookService.update(isbn, book);
    }


    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("isbn") String isbn) {
        this.bookService.remove(isbn);
    }

}
