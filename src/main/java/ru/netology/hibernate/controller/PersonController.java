package ru.netology.hibernate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.netology.hibernate.entity.Person;
import ru.netology.hibernate.repository.PersonRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping("/persons/by-city")
    public List<Person> getByCity(@RequestParam String city) {
        return personRepository.getPersonsByCity(city);
    }
}
