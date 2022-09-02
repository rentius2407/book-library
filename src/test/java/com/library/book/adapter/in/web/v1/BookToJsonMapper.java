package com.library.book.adapter.in.web.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book.adapter.in.web.v1.model.BookResource;

import java.io.IOException;
import java.io.StringWriter;

public final class BookToJsonMapper {

    final static ObjectMapper objectMapper = new ObjectMapper();

    public static String convert(BookResource newBookResource) throws IOException {
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, newBookResource);
        return stringWriter.toString();
    }
}
