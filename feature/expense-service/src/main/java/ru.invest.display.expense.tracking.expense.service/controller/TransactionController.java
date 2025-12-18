package ru.invest.display.expense.tracking.expense.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.invest.display.expense.tracking.api.dto.TransactionDto;
import ru.invest.display.expense.tracking.api.request.TransactionRequest;

@RestController
@RequestMapping("/internal/rest/v1/invest/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    public TransactionDto create(final TransactionRequest request) {
        return null;
    }
}
