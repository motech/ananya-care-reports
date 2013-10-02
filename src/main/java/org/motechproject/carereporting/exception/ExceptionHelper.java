package org.motechproject.carereporting.exception;

public final class ExceptionHelper {

    private ExceptionHelper() {

    }

    public static Throwable getExceptionRealCause(Throwable exception) {
        Throwable realCause = exception;

        if (exception.getCause() != null) {
            realCause = getExceptionRealCause(exception.getCause());
        }

        return realCause;
    }
}
