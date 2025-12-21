package ru.invest.display.expense.tracking.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.invest.display.expense.tracking.common.context.UserContext;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUidHeaderInterceptor implements HandlerInterceptor {
    private static final String UID_HEADER_NAME = "x-user-uid";
    private static final String ERROR_MESSAGE = "Missing required header: " + UID_HEADER_NAME;

    private final UserContext userContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws IOException {
        final String userUid = Optional.ofNullable(request)
                .map(r -> r.getHeader(UID_HEADER_NAME))
                .orElse(null);

        if (StringUtils.isBlank(userUid)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ERROR_MESSAGE);
            return false;
        }

        userContext.setCurrentUserUid(userUid.trim());
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object handler,
                                final Exception ex) {
        userContext.clear();
    }
}
