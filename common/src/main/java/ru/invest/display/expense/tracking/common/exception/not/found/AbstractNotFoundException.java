package ru.invest.display.expense.tracking.common.exception.not.found;

public abstract class AbstractNotFoundException extends RuntimeException {
    public AbstractNotFoundException(final String message) {
        super(message);
    }

    public AbstractNotFoundException(){
        super();
    }
}
