package com.library.book.adapter.in.web.v1.model;

import javax.validation.constraints.NotBlank;

public class AuthorResource {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    public AuthorResource(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
