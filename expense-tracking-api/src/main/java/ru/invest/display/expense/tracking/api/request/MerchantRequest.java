package ru.invest.display.expense.tracking.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MerchantRequest {
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Long categoryId;
}
