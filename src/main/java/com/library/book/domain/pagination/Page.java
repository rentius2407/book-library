package com.library.book.domain.pagination;

import java.util.List;

public interface Page<T> {

    List<T> getContent();

    int getCount();

    int getOffset();

    int getLimit();

    int getTotal();

    boolean isEmpty();
}
