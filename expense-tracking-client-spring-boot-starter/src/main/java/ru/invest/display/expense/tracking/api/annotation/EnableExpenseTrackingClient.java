package ru.invest.display.expense.tracking.api.annotation;

import org.springframework.context.annotation.Import;
import ru.invest.display.expense.tracking.api.autoconfiguration.ExpenseTrackingClientAutoConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ExpenseTrackingClientAutoConfiguration.class)
public @interface EnableExpenseTrackingClient {
    String value() default "expense-tracking-client";
}
