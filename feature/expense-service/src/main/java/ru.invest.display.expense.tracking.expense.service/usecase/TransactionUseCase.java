package ru.invest.display.expense.tracking.expense.service.usecase;

import ru.invest.display.expense.tracking.common.model.TransactionModel;

public interface TransactionUseCase {
    Long save(TransactionModel transaction);
}
