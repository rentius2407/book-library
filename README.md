# RESTful book-library application

## Overview

The book-library application is a service which provides CRUD operations on a Book resource.

The application can either be packaged as a jar or war.
Packaging the application as a war requires an environment which has a web server. 
Spring Boot embeds a tomcat web server within the jar file when packaging as a jar, which means there is no need to install or configure a web server. 

At the center of the application is the domain layer, which consists of use cases/services and domain objects. The responsibility of a service is to focus on a business use case.
All dependecies are inverted so that changes in the lower and upper layers don't affect the business layer.

Remove book is used instead of delete book and register book is used instead of add book. Remove is a more natural desciption than delete. E.g. remove a book from the library rather than delete a book out of the library. Register or add can be used, but normally a book is registered on the system.

Contructor injection forces depenencies to be mandatory and it also makes mocking dependencies a lot easier. If field injection was used, then a mocking framework which can inject a mock via reflection is required.

Unit tests are used to assist in the design and not just for testing.

## Code organization
Organize by feature with expressive sturcture is used instead of organize by layer. It makes it easier to navigate and identify certain features and also introduce natural boundaries between code which can help identify when layers and/or features leak into other layers. 

![image](https://user-images.githubusercontent.com/5244466/188117238-f770b164-7039-4055-a8e7-c34098e2bfe9.png)

At the root level package is the feature name book, indicating this is the implementation for Book.
The application package contains the service layer for use cases (register, search, update and remove book). 

Inside the application port (in & out) package is the inverted dependencies. 
Each service will implement an use case residing in the port in package for outside layers to use when talking inward. 
When a service needs to talk to the ourside layer i.e. persistence layer, the port out package would contain the interface.

Adapters are classified as the outside layer, they are also organized as in and out.
A typical example of an in adapter is a RESTful service and an example of an out adapter is JDBC.

## RESTful service

### Versioning
Versioning of the API allows for backward compatibility.
When introducing a new feature with breaking changes it will give clients enough time to migrate.
New features can also be deployed and tested in isolation.

### API

### Invalid fields (null or empty)

#### POST /v1/books
Register a book with empty title and no author name. The response body will contain a response indicating the type and the fields that is either null or empty.

**Request:**

```
{
  "title": "",
  "author": {
    "surname":"Larsson"
  },
  "isbn":"14439519874"
}
```

**Response:**

```
HTTP/1.1 400 Bad Request
content-type: application/json

{
  "type": "FIELD_ERROR",
  "data": [
    {
      "field": "title",
      "message": "must not be blank"
    },
    {
      "field": "author.name",
      "message": "must not be blank"
    }
  ]
}
```


#### GET /v1/books
Find all books in the library.


**Request:**
http://localhost:8080/library/v1/books

Optional offset and limit query parametes are used for pagination, and if omitted, sensible defaults will be used.
```/v1/books?offset=3&limit=1```

**Response:**

```
HTTP/1.1 200 OK
content-type: application/json

{
  "books": [
    {
      "title": "To Kill a Mockingbird",
      "author": {
        "name": "Harper",
        "surname": "Lee"
      },
      "isbn": "0446310786"
    },
    {
      "title": "Catch-22",
      "author": {
        "name": "Joseph",
        "surname": "Heller"
      },
      "isbn": "069512545"
    },
    {
      "title": "The Fellowship of the Ring",
      "author": {
        "name": "J.R.R",
        "surname": "Tolkien"
      },
      "isbn": "1046322799"
    },
    {
      "title": "The Shining",
      "author": {
        "name": "Stephen",
        "surname": "King"
      },
      "isbn": "1432310111"
    },
    {
      "title": "The Girl With the Dragon Tattoo",
      "author": {
        "name": "Stieg",
        "surname": "Larsson"
      },
      "isbn": "93866217091"
    },
    {
      "title": "Dune",
      "author": {
        "name": "Frank",
        "surname": "Herbert"
      },
      "isbn": "9987421777"
    }
  ],
  "count": 6,
  "offset": 0,
  "limit": 10,
  "total": 6
}
```
**Empty response:**

```
{
  "books": [],
  "count": 0,
  "offset": 0,
  "limit": 0,
  "total": 0
}
```


#### GET /v1/books/{isbn}
Search for a specific resource with the corresponding isbn. 

**Request:**
http://localhost:8080/library/v1/books/069512545

**Response:**

```
HTTP/1.1 200 OK
content-type: application/json

{
  "title": "Catch-22",
  "author": {
    "name": "Joseph",
    "surname": "Heller"
  },
  "isbn": "069512545"
}
```

**Empty response:**
No resource was found with isbn.

```
HTTP/1.1 204 No Content
```

#### POST /v1/books
Register a book

**Request:**

```
{
  "title": "The Girl With the Dragon Tattoo",
  "author": {
    "name":"Stieg",
    "surname":"Larsson"
  },
  "isbn":"14439519874"
}
```

**Response:**

```
Status 201 Created
```

**Duplicate book response:**

```
Status 409 Conflict
```

#### PUT /v1/books/{isbn}
Update a specific resource with the corresponding isbn. 

**Request:**
http://localhost:8080/library/v1/books/069512545

```
{
  "title": "Catch22",
  "author": {
    "name": "Joseph",
    "surname": "Heller"
  },
  "isbn": "069512545"
}
```

**Response:**

```
HTTP/1.1 204 No Content
```

**Not found response:**
No resource was found with isbn.

```
HTTP/1.1 404 Not Found
```

#### DETELE /v1/books/{isbn}
Remove a specific resource with the corresponding isbn. 

**Request:**
http://localhost:8080/library/v1/books/069512545

**Response:**

```
HTTP/1.1 204 No Content
```

**Not found response:**
No resource was found with isbn.

```
HTTP/1.1 404 Not Found
```
