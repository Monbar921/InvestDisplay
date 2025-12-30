package ru.invest.display.expense.tracking.expense.service.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.invest.display.expense.tracking.common.domain.Category;
import ru.invest.display.expense.tracking.common.model.CategoryModel;
import ru.invest.display.expense.tracking.common.repository.CategoryRepository;
import ru.invest.display.expense.tracking.expense.service.mapper.CategoryMapper;
import ru.invest.display.expense.tracking.expense.service.usecase.CategoryUseCase;

@Component
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Long save(final CategoryModel category) {
        return saveCategory(category)
                .getId();
    }

    private Category saveCategory(final CategoryModel model) {
        return categoryRepository.save(
                categoryMapper.toEntity(model)
        );
    }
}
