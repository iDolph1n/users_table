package ru.netology.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerOrderRepository {

    private static final String PRODUCTS_SQL_FILE = "products.sql";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String productsSql;

    public CustomerOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.productsSql = read(PRODUCTS_SQL_FILE);
    }

    public List<String> getProductNames(String name) {
        var params = new MapSqlParameterSource()
                .addValue("name", name);

        return jdbcTemplate.query(
                productsSql,
                params,
                (rs, rowNum) -> rs.getString("product_name")
        );
    }

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read SQL file from classpath: " + scriptFileName, e);
        }
    }
}
