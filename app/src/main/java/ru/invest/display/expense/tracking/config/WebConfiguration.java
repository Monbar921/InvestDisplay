package ru.invest.display.expense.tracking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.invest.display.expense.tracking.common.context.UserContext;
import ru.invest.display.expense.tracking.interceptor.UserUidHeaderInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final UserContext userContext;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new UserUidHeaderInterceptor(userContext));
    }
}
