package ru.invest.display.expense.tracking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.invest.display.expense.tracking.common.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
