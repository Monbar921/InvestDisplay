package aero.s7.smi.hotels.api.dto;

import aero.s7.smi.hotels.api.dto.enums.Currency;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class TransactionDto {
    private Long id;
    private String userUid;
    private BigDecimal amount;
    private Currency currency;
    private TransactionCategoryDto category;
    private MerchantDto merchant;
    private ZonedDateTime timestamp;
    private String description;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
