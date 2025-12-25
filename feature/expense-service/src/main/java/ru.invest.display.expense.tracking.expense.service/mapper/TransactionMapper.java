package ru.invest.display.expense.tracking.expense.service.mapper;

import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.invest.display.expense.tracking.api.request.TransactionRequest;
import ru.invest.display.expense.tracking.common.domain.Transaction;
import ru.invest.display.expense.tracking.common.exception.not.found.TransactionNotFoundException;
import ru.invest.display.expense.tracking.common.exception.validation.TransactionValidationException;
import ru.invest.display.expense.tracking.common.mapper.DateTimeMapper;
import ru.invest.display.expense.tracking.common.mapper.UserMapper;
import ru.invest.display.expense.tracking.common.model.TransactionModel;
import ru.invest.display.expense.tracking.common.repository.TransactionRepository;

import java.util.Optional;

@Mapper(uses = {UserMapper.class, MerchantMapper.class})
public abstract class TransactionMapper {
    @Setter(onMethod = @__({@Autowired}))
    private DateTimeMapper dateTimeMapper;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryMapper categoryMapper;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;

    @Mapping(target = "userUid", source = "request", qualifiedByName = "getUserUid")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract TransactionModel fromRequest(TransactionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "merchant", source = "merchantId", qualifiedByName = "findByIdValidated")
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Transaction toEntity(TransactionModel transaction);

    @ObjectFactory
    protected Transaction objectFactory(final TransactionModel transaction) {
        if (transaction == null) {
            throw new TransactionValidationException("Can not map null model to entity");
        }

        return Optional.ofNullable(transaction.getId())
                .map(this::findById)
                .orElseGet(this::createNewTransaction);
    }

    protected Transaction findById(final Long id) {
        if (id == null) {
            return null;
        }

        return Optional.of(id)
                .flatMap(transactionRepository::findById)
                .map(this::setUpdated)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    protected Transaction createNewTransaction() {
        return new Transaction()
                .setCreatedAt(
                        dateTimeMapper.getCurrentDateTimeUtc()
                );
    }

    protected Transaction setUpdated(final Transaction transaction) {
        return Optional.ofNullable(transaction)
                .map(entity -> entity.setUpdatedAt(
                        dateTimeMapper.getCurrentDateTimeUtc()
                ))
                .orElse(null);
    }

    @AfterMapping
    protected void afterMapping(final Transaction entity, final TransactionModel model) {
        if (ObjectUtils.anyNull(entity, model)) {
            return;
        }

        entity.setCategories(
                categoryMapper.persistCategories(entity.getCategories(), model.getCategories())
        );
    }
}
