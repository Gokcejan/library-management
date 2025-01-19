package cz.demo.librarymanagement.application.exceptions;

import static java.lang.String.format;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args) {
        super(format(message, args));
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
