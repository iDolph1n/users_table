package ru.netology.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.repository.CustomerOrderRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerOrderRepository repository;

    @Test
    void fetchProductsReturnsList() throws Exception {
        Mockito.when(repository.getProductNames(eq("Alex")))
                .thenReturn(List.of("Phone", "Laptop"));

        mockMvc.perform(get("/products/fetch-product")
                        .param("name", "Alex"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Phone\",\"Laptop\"]"));
    }

    @Test
    void fetchProductsReturnsEmptyList() throws Exception {
        Mockito.when(repository.getProductNames(eq("Unknown")))
                .thenReturn(List.of());

        mockMvc.perform(get("/products/fetch-product")
                        .param("name", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
