package aero.s7.smi.hotels.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionCategoryDto {
    private Long id;
    private String name;
}
