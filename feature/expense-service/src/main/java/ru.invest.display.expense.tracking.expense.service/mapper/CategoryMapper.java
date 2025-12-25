package ru.invest.display.expense.tracking.expense.service.mapper;

import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import ru.invest.display.expense.tracking.common.domain.Category;
import ru.invest.display.expense.tracking.common.exception.not.found.CategoryNotFoundException;
import ru.invest.display.expense.tracking.common.mapper.DateTimeMapper;
import ru.invest.display.expense.tracking.common.model.CategoryModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class CategoryMapper {
    @Setter(onMethod = @__({@Autowired}))
    private DateTimeMapper dateTimeMapper;

    public List<Category> persistCategories(final List<Category> existed, final List<CategoryModel> requested) {
        final Set<Long> idSet = Optional.ofNullable(requested)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(CategoryModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        deleteCategories(existed, requested, idSet);
        updateCategories(existed, requested, idSet);

        return createCategories(existed, requested);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userUid", source = "userUid")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    protected abstract Category updateCategory(@MappingTarget Category entity, CategoryModel model);

    protected void deleteCategories(final List<Category> existed, final List<CategoryModel> requested, final Set<Long> requestedIdSet) {
        if (CollectionUtils.isEmpty(existed)) {
            return;
        }

        if (CollectionUtils.isEmpty(requestedIdSet) || CollectionUtils.isEmpty(requested)) {
            existed.clear();
            return;
        }

        final List<Category> toDelete = existed
                .stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getId() != null)
                .filter(c -> !requestedIdSet.contains(c.getId()))
                .toList();

        if (CollectionUtils.isNotEmpty(toDelete)) {
            existed.removeAll(toDelete);
        }
    }

    protected void updateCategories(final List<Category> existed, final List<CategoryModel> requested, final Set<Long> requestedIdSet) {
        if (CollectionUtils.isEmpty(existed) || CollectionUtils.isEmpty(requestedIdSet) || CollectionUtils.isEmpty(requested)) {
            return;
        }

        final List<Category> toUpdate = existed
                .stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getId() != null)
                .filter(c -> requestedIdSet.contains(c.getId()))
                .toList();

        toUpdate
                .forEach(category -> {
                    final CategoryModel categoryModel = getCategoryModel(requested, category.getId());
                    category.setUpdatedAt(dateTimeMapper.getCurrentDateTimeUtc());
                    updateCategory(category, categoryModel);
                });
    }

    protected List<Category> createCategories(final List<Category> existed, final List<CategoryModel> requested) {
        if (CollectionUtils.isEmpty(requested)) {
            return Collections.emptyList();
        }

        final List<Category> created = requested
                .stream()
                .filter(Objects::nonNull)
                .filter(category -> category.getId() != null)
                .map(categoryModel -> {
                    final Category entity = new Category()
                            .setCreatedAt(dateTimeMapper.getCurrentDateTimeUtc());
                    return updateCategory(entity, categoryModel);
                })
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(existed)) {
            return created;
        } else {
            existed.addAll(created);
            return existed;
        }
    }

    protected CategoryModel getCategoryModel(final List<CategoryModel> requested, final Long id) {
        return Optional.ofNullable(requested)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(category -> ObjectUtils.nullSafeEquals(category.getId(), id))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
