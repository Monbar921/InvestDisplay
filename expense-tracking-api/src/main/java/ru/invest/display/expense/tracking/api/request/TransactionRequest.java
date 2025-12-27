package ru.invest.display.expense.tracking.api.request;

import ru.invest.display.expense.tracking.api.dto.enums.Currency;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class TransactionRequest {
    private Long id;
    private BigDecimal amount;
    private Currency currency;
    private List<Long> categories;
    private Long merchantId;
    private ZonedDateTime operationTimestamp;
    private String description;
}
