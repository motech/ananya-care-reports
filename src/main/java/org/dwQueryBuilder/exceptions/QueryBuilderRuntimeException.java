package org.dwQueryBuilder.exceptions;

public class QueryBuilderRuntimeException extends RuntimeException {

    public QueryBuilderRuntimeException(String message) {
        super(message);
    }

    public QueryBuilderRuntimeException(Throwable cause) {
        super(cause);
    }

    public QueryBuilderRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
