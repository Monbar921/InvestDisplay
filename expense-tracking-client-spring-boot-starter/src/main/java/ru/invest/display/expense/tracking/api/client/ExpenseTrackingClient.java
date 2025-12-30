package ru.invest.display.expense.tracking.api.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.invest.display.expense.tracking.api.request.CategoryRequest;
import ru.invest.display.expense.tracking.api.request.MerchantRequest;
import ru.invest.display.expense.tracking.api.request.TransactionRequest;

public interface ExpenseTrackingClient {
    // category
    @PostMapping("/category")
    Long createCategory(@RequestBody CategoryRequest request);

    // merchant
    @PostMapping("/merchant")
    Long createMerchant(@RequestBody MerchantRequest request);

    // transaction
    @PostMapping("/transaction")
    Long createTransaction(@RequestBody TransactionRequest request);
}
