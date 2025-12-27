package ru.invest.display.expense.tracking.common.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.invest.display.expense.tracking.common.domain.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class TransactionModel {
    private Long id;
    private String userUid;
    private BigDecimal amount;
    private Currency currency;
    private List<Long> categories;
    private Long merchantId;
    private ZonedDateTime operationTimestamp;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
