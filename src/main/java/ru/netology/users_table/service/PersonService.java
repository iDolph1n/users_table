package ru.netology.users_table.service;

import org.springframework.stereotype.Service;
import ru.netology.users_table.entity.Person;
import ru.netology.users_table.entity.PersonId;
import ru.netology.users_table.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Optional<Person> getById(PersonId id) {
        return repository.findById(id);
    }

    public Person create(Person person) {
        // Для composite PK: person.id должен быть заполнен (name/surname/age)
        return repository.save(person);
    }

    public Optional<Person> update(PersonId id, Person updated) {
        return repository.findById(id)
                .map(existing -> {
                    // PK (id) не меняем. Меняем только неключевые поля.
                    existing.setPhoneNumber(updated.getPhoneNumber());
                    existing.setCityOfLiving(updated.getCityOfLiving());
                    return repository.save(existing);
                });
    }

    public void delete(PersonId id) {
        repository.deleteById(id);
    }

    public List<Person> getByCity(String city) {
        return repository.findByCityOfLiving(city);
    }

    public List<Person> getYoungerThan(Integer age) {
        return repository.findByIdAgeLessThanOrderByIdAgeAsc(age);
    }

    public Optional<Person> getByNameAndSurname(String name, String surname) {
        return repository.findFirstByIdNameAndIdSurname(name, surname);
    }
}
