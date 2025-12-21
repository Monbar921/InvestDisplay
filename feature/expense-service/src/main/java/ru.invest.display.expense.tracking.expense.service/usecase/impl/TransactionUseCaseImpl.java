package ru.invest.display.expense.tracking.expense.service.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.invest.display.expense.tracking.common.domain.Transaction;
import ru.invest.display.expense.tracking.common.model.TransactionModel;
import ru.invest.display.expense.tracking.common.repository.TransactionRepository;
import ru.invest.display.expense.tracking.expense.service.mapper.TransactionMapper;
import ru.invest.display.expense.tracking.expense.service.usecase.TransactionUseCase;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionUseCaseImpl implements TransactionUseCase {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Long save(final TransactionModel transaction) {
        return saveTransaction(transaction)
                .getId();
    }

    private Transaction saveTransaction(final TransactionModel transaction) {
        return transactionRepository.save(
                transactionMapper.toEntity(transaction)
        );
    }
}
