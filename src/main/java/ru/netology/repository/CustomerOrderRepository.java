package ru.netology.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerOrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String productsByNameSql;

    public CustomerOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.productsByNameSql = read("products.sql");
    }

    public List<String> getProductNames(String name) {
        var params = new MapSqlParameterSource()
                .addValue("name", name);
        return jdbcTemplate.query(
                productsByNameSql,
                params,
                (rs, rowNum) -> rs.getString("product_name")
        );
    }

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
