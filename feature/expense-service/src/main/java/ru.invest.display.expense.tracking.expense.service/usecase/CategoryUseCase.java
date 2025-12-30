package ru.invest.display.expense.tracking.expense.service.usecase;

import ru.invest.display.expense.tracking.common.model.CategoryModel;

public interface CategoryUseCase {
    Long save(CategoryModel category);
}
