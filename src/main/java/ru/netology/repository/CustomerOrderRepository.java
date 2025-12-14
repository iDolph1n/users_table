package ru.netology.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getProductNames(String name) {
        return entityManager.createQuery(
                        "select o.productName " +
                                "from Order o " +
                                "where o.customer.name = :name",
                        String.class
                )
                .setParameter("name", name)
                .getResultList();
    }
}
