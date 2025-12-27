package ru.invest.display.expense.tracking.common.mapper;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.invest.display.expense.tracking.common.context.UserContext;

@Mapper
@SuppressWarnings("checkstyle:AbstractClassName")
public abstract class UserMapper {
    @Setter(onMethod = @__({@Autowired}))
    private UserContext userContext;

    @Named("getUserUid")
    public String getUserUid(final Object object) {
        return userContext.getCurrentUserUid();
    }
}
