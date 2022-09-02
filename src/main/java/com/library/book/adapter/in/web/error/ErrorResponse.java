package com.library.book.adapter.in.web.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private ErrorResponseType type;
    private List<Object> data = new ArrayList<>();

    public ErrorResponse(ErrorResponseType type) {
        this.type = type;
    }

    public ErrorResponseType getType() {
        return type;
    }

    public void setType(ErrorResponseType type) {
        this.type = type;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public void addMessage(Object data) {
        this.data.add(data);
    }

}
