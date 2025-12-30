package ru.invest.display.expense.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.invest.display.AbstractExpenseTrackingApplicationTest;
import ru.invest.display.expense.tracking.api.client.ExpenseTrackingClient;
import ru.invest.display.expense.tracking.api.request.CategoryRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CategoryTest extends AbstractExpenseTrackingApplicationTest {
    @Autowired
    private ExpenseTrackingClient expenseTrackingClient;

    @Test
    public void testCreate(){
        final CategoryRequest categoryRequest = readObjectFromFile("/json/category/create.json", CategoryRequest.class);

        final Long id = expenseTrackingClient.createCategory(categoryRequest);
        assertThat(id)
                .isNotNull();
    }
}
