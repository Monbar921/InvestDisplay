package ru.invest.display.expense.tracking.common.exception.validation;

public abstract class AbstractValidationException extends RuntimeException {
    public AbstractValidationException(final String message) {
        super(message);
    }
}
