package ru.invest.display.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@DiscriminatorValue(value = "bank_account")
public class BankAccount extends Product<Long>{
    @Column(nullable = false)
    private double interest;
    private LocalDate startDate;
    private LocalDate endDate;

    public BankAccount(){
        super.setQuantity(1);
    }
}
