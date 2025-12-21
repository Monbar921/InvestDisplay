package ru.invest.display.expense.tracking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.invest.display.expense.tracking.common.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
