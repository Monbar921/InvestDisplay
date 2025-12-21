package ru.invest.display.expense.tracking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.invest.display.expense.tracking.common.domain.Merchant;
import ru.invest.display.expense.tracking.common.domain.Transaction;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
