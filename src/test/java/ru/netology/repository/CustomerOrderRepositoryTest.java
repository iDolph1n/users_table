package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CustomerOrderRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerOrderRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM ORDERS");
        jdbcTemplate.update("DELETE FROM CUSTOMERS");

        jdbcTemplate.update(
                "INSERT INTO CUSTOMERS(id, name, surname, age, phone_number) VALUES (1, 'Alex', 'Ivanov', 30, '123456')"
        );

        jdbcTemplate.update(
                "INSERT INTO ORDERS(id, customer_id, product_name, amount) VALUES (1, 1, 'Phone', 1)"
        );
        jdbcTemplate.update(
                "INSERT INTO ORDERS(id, customer_id, product_name, amount) VALUES (2, 1, 'Laptop', 2)"
        );
    }

    @Test
    void shouldReturnProductsByCustomerName() {
        List<String> products = repository.getProductNames("Alex");

        assertThat(products)
                .containsExactlyInAnyOrder("Phone", "Laptop");
    }

    @Test
    void shouldReturnEmptyListForUnknownName() {
        List<String> products = repository.getProductNames("Unknown");

        assertThat(products).isEmpty();
    }
}
