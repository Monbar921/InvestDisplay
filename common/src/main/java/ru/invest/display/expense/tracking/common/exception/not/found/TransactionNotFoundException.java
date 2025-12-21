package ru.invest.display.expense.tracking.common.exception.not.found;

public class TransactionNotFoundException extends AbstractNotFoundException {
    private static final String MESSAGE = "Transaction with id=%d not found";

    public TransactionNotFoundException(final String message) {
        super(message);
    }

    public TransactionNotFoundException(final Long id){
        super(MESSAGE.formatted(id));
    }
}
