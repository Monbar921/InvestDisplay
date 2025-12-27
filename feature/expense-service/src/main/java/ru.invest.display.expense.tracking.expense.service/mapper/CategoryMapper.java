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
import ru.invest.display.expense.tracking.common.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
@SuppressWarnings("checkstyle:AbstractClassName")
public abstract class CategoryMapper {
    @Setter(onMethod = @__({@Autowired}))
    private DateTimeMapper dateTimeMapper;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;

    public List<Category> persistCategories(final List<Category> existed, final List<CategoryModel> requested) {
        final Set<Long> idSet = Optional.ofNullable(requested)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(CategoryModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        deleteCategories(existed, idSet);
        updateCategories(existed, requested, idSet);

        return createCategories(existed, requested);
    }

    public List<Category> persistCategoriesNotCreateEntity(final List<Category> existed, final List<Long> requested) {
        final Set<Long> idSet = Optional.ofNullable(requested)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        deleteCategories(existed, idSet);
        return addNewCategories(existed, requested);
    }

    public Category toEntity(final Long id) {
        return findById(id);
    }

    public List<Category> toEntity(final List<Long> categories) {
        return Optional.ofNullable(categories)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toEntity)
                .toList();
    }

//    @ObjectFactory
//    protected Category objectFactory(){
//
//    }

    protected Category findById(final Long id) {
        return Optional.ofNullable(id)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userUid", source = "userUid")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    protected abstract Category updateCategory(@MappingTarget Category entity, CategoryModel model);

    protected void deleteCategories(final List<Category> existed, final Set<Long> requestedIdSet) {
        if (CollectionUtils.isEmpty(existed)) {
            return;
        }

        if (CollectionUtils.isEmpty(requestedIdSet)) {
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

    protected List<Category> addNewCategories(final List<Category> existed, final List<Long> requested) {
        if (CollectionUtils.isEmpty(requested)) {
            return Collections.emptyList();
        }

        final Set<Long> existedIdSet = Optional.ofNullable(existed)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(Category::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        final List<Long> toCreate = requested
                .stream()
                .filter(Objects::nonNull)
                .filter(id -> !existedIdSet.contains(id))
                .toList();

        return toEntity(toCreate);
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
