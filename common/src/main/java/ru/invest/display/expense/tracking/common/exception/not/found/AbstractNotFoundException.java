package ru.invest.display.expense.tracking.common.exception.not.found;

public class AbstractNotFoundException extends RuntimeException {
    public AbstractNotFoundException(final String message) {
        super(message);
    }

    public AbstractNotFoundException(){
        super();
    }
}
