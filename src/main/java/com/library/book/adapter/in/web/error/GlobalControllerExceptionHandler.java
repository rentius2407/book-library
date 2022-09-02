package com.library.book.adapter.in.web.error;

import com.library.book.application.service.BookDoesNotExistException;
import com.library.book.application.service.DuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder logErrorMessage = new StringBuilder();
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseType.FIELD_ERROR);

        int commaCounter = 0;
        for (org.springframework.validation.FieldError fieldError : ex.getFieldErrors()) {
            errorResponse.addMessage(new FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
            logErrorMessage.append(fieldError.getField());

            if (commaCounter + 1 < ex.getFieldErrors().size()) {
                logErrorMessage.append(",");
                commaCounter++;
            }
        }

        LOG.error("Fields required. [fields={}]", logErrorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Void> handleBookExistsException(DuplicateException duplicateException) {
        LOG.error("Book already exists. [message={}]", duplicateException.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(BookDoesNotExistException.class)
    public ResponseEntity<Void> handleBookDoesNotExistException(BookDoesNotExistException bookDoesNotExistException) {
        LOG.error("Book not found. [message={}]", bookDoesNotExistException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
