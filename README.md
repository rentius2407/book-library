# RESTful book-library application
At the center of the application is the domain layer, which consists of use cases/services and domain objects. The responsibility of a service is to focus on a business use case.
All dependecies are inverted so that changes in the lower and upper layers don't affect the business layer.

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
Add a book to the library

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

