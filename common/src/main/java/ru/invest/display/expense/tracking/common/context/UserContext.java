package ru.invest.display.expense.tracking.common.context;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {
    private String currentUserUid;

    public void clear() {
        this.currentUserUid = null;
    }

    public boolean hasUserContext() {
        return StringUtils.isNotBlank(this.currentUserUid);
    }
}