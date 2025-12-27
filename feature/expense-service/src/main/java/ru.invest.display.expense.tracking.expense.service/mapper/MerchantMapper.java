package ru.invest.display.expense.tracking.expense.service.mapper;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.invest.display.expense.tracking.common.domain.Merchant;
import ru.invest.display.expense.tracking.common.exception.not.found.TransactionNotFoundException;
import ru.invest.display.expense.tracking.common.mapper.DateTimeMapper;
import ru.invest.display.expense.tracking.common.mapper.UserMapper;
import ru.invest.display.expense.tracking.common.repository.MerchantRepository;

import java.util.Optional;

@Mapper(uses = {UserMapper.class})
@SuppressWarnings("checkstyle:AbstractClassName")
public abstract class MerchantMapper {
    @Setter(onMethod = @__({@Autowired}))
    private MerchantRepository merchantRepository;
    @Setter(onMethod = @__({@Autowired}))
    private DateTimeMapper dateTimeMapper;

    @Named("findMerchantById")
    public Merchant findMerchantById(final Long id) {
        return Optional.ofNullable(id)
                .flatMap(merchantRepository::findById)
                .map(this::setUpdated)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    protected Merchant setUpdated(final Merchant merchant) {
        return Optional.ofNullable(merchant)
                .map(entity -> entity.setUpdatedAt(
                        dateTimeMapper.getCurrentDateTimeUtc()
                ))
                .orElse(null);
    }
}
