package ru.invest.display.rest.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/rest/v1/invest/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    public TransactionDto create(TransactionRequest request){

    }
}
