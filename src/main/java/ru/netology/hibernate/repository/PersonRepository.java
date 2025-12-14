package ru.netology.hibernate.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.netology.hibernate.entity.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getPersonsByCity(String city) {
        List<Person> persons = entityManager
                .createQuery("select p from Person p", Person.class)
                .getResultList();

        return persons.stream()
                .filter(p -> Objects.equals(p.getCityOfLiving(), city))
                .collect(Collectors.toList());
    }
}
