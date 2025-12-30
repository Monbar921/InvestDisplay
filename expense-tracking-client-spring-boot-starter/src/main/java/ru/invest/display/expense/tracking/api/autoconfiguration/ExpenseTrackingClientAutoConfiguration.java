package ru.invest.display.expense.tracking.api.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.invest.display.expense.tracking.api.annotation.EnableExpenseTrackingClient;
import ru.invest.display.expense.tracking.api.client.ExpenseTrackingClient;

@Configuration
@ConditionalOnBean(annotation = EnableExpenseTrackingClient.class)
public class ExpenseTrackingClientAutoConfiguration extends AbstractClientAutoConfiguration<EnableExpenseTrackingClient> {
    private final String apiVersion;

    public ExpenseTrackingClientAutoConfiguration(
            final Environment environment,
            final ApplicationContext applicationContext,
            @Value("${spring.cloud.openfeign.client.config.expense-tracking-client.api-version:v1}") final String apiVersion
    ) {
        super(environment, applicationContext, EnableExpenseTrackingClient);
        this.apiVersion = apiVersion;
    }

    @Bean
    @ConditionalOnMissingBean
    public ExpenseTrackingClient expenseTrackingClient() {
        final String clientName = getClientName();
        return new FeignClientBuilder(getApplicationContext())
                .forType(
                        ExpenseTrackingClient.class,
                        clientName
                )
                .url(getClientUrl(clientName))
                .path("/internal/rest/" + apiVersion + "/invest")
                .build();
    }
}
