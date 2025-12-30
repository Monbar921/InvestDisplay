package ru.invest.display.expense.tracking.expense.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.invest.display.expense.tracking.api.request.CategoryRequest;
import ru.invest.display.expense.tracking.expense.service.mapper.CategoryMapper;
import ru.invest.display.expense.tracking.expense.service.usecase.CategoryUseCase;

@RestController
@RequestMapping("/internal/rest/v1/invest/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryUseCase categoryUseCase;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public Long create(final CategoryRequest request) {
        return categoryUseCase.save(
                categoryMapper.fromRequest(request)
        );
    }
}
