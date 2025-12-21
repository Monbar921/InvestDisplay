package ru.invest.display.expense.tracking.expense.service.mapper;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.invest.display.expense.tracking.api.request.TransactionRequest;
import ru.invest.display.expense.tracking.common.domain.Merchant;
import ru.invest.display.expense.tracking.common.domain.Transaction;
import ru.invest.display.expense.tracking.common.exception.not.found.TransactionNotFoundException;
import ru.invest.display.expense.tracking.common.exception.validation.TransactionValidationException;
import ru.invest.display.expense.tracking.common.mapper.DateTimeMapper;
import ru.invest.display.expense.tracking.common.mapper.UserMapper;
import ru.invest.display.expense.tracking.common.model.TransactionModel;
import ru.invest.display.expense.tracking.common.repository.MerchantRepository;
import ru.invest.display.expense.tracking.common.repository.TransactionRepository;

import java.util.Optional;

@Mapper(uses = {UserMapper.class})
public abstract class MerchantMapper {
    @Setter(onMethod = @__({@Autowired}))
    private MerchantRepository merchantRepository;
    @Setter(onMethod = @__({@Autowired}))
    private DateTimeMapper dateTimeMapper;

    @Named("findByIdValidated")
    public Merchant findByIdValidated(final Long id){
        return Optional.ofNullable(id)
                .flatMap(merchantRepository::findById)
                .map(this::setUpdated)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    protected Merchant setUpdated(final Merchant merchant){
        return Optional.ofNullable(merchant)
                .map(entity -> entity.setUpdatedAt(
                        dateTimeMapper.getCurrentDateTimeUtc()
                ))
                .orElse(null);
    }
}
