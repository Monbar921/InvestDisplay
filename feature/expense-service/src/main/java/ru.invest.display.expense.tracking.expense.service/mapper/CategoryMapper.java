package ru.invest.display.expense.tracking.expense.service.mapper;

import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.invest.display.expense.tracking.common.domain.Category;
import ru.invest.display.expense.tracking.common.domain.Merchant;
import ru.invest.display.expense.tracking.common.exception.not.found.TransactionNotFoundException;
import ru.invest.display.expense.tracking.common.mapper.DateTimeMapper;
import ru.invest.display.expense.tracking.common.mapper.UserMapper;
import ru.invest.display.expense.tracking.common.repository.MerchantRepository;

import java.util.List;
import java.util.Optional;

@Mapper
public abstract class CategoryMapper {
    @Setter(onMethod = @__({@Autowired}))
    private MerchantRepository merchantRepository;
    @Setter(onMethod = @__({@Autowired}))
    private DateTimeMapper dateTimeMapper;

    public List<Category> persistCategories(final List<Category> existed, final List<Long> requested){
        deleteCategories(existed, requested);
        updateCategories(existed, requested);

        return createCategories(existed, requested);
    }

    protected void deleteCategories(final List<Category> existed, final List<Long> requested){
        if(CollectionUtils.isEmpty(existed)){
            return;
        }


    }

    protected Merchant setUpdated(final Merchant merchant){
        return Optional.ofNullable(merchant)
                .map(entity -> entity.setUpdatedAt(
                        dateTimeMapper.getCurrentDateTimeUtc()
                ))
                .orElse(null);
    }
}
