package ru.invest.display.expense.tracking.common.exception.not.found;

public class CategoryNotFoundException extends AbstractNotFoundException {
    private static final String MESSAGE = "Category with id=%d not found";

    public CategoryNotFoundException(final String message) {
        super(message);
    }

    public CategoryNotFoundException(final Long id){
        super(MESSAGE.formatted(id));
    }
}
