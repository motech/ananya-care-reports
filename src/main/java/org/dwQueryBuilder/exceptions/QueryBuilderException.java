package org.dwQueryBuilder.exceptions;

public class QueryBuilderException extends RuntimeException {

    public QueryBuilderException(String message) {
        super(message);
    }

    public QueryBuilderException(Throwable cause) {
        super(cause);
    }

    public QueryBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

}
