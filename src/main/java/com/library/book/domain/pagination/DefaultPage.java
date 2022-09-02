package com.library.book.domain.pagination;

import com.library.book.domain.Book;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultPage<T> implements Page<T> {

    private final List<T> content = new ArrayList<>();
    private final int offset;
    private final int limit;
    private final int total;

    public DefaultPage(List<T> content, int offset, int limit, int total) {
        Assert.notNull(content, "Content must not be null");
        this.content.addAll(content);
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }

    public static Page<Book> empty() {
        return new DefaultPage<>(Collections.emptyList(), 0, 0, 0);
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public int getCount() {
        return this.content.size();
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public boolean isEmpty() {
        return this.content.isEmpty();
    }
}
