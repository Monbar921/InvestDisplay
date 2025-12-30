package ru.invest.display.expense.tracking.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryRequest {
    private Long id;
    @NotEmpty
    private String name;
}
