package ru.invest.display.expense.tracking.common.model;

import ru.invest.display.expense.tracking.common.domain.enums.Currency;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TransactionModel {
    private Long id;
    private String userUid;
    private BigDecimal amount;
    private Currency currency;
    private Long categoryId;
    private Long merchantId;
    private LocalDateTime timestamp;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
