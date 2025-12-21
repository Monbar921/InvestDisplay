package ru.invest.display.expense.tracking.expense.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.invest.display.expense.tracking.api.request.TransactionRequest;
import ru.invest.display.expense.tracking.expense.service.mapper.TransactionMapper;
import ru.invest.display.expense.tracking.expense.service.usecase.TransactionUseCase;

@RestController
@RequestMapping("/internal/rest/v1/invest/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionUseCase transactionUseCase;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public Long create(final TransactionRequest request) {
        return transactionUseCase.save(
                transactionMapper.fromRequest(request)
        );
    }
}
