package ru.netology.users_table.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.users_table.entity.Person;
import ru.netology.users_table.entity.PersonId;
import ru.netology.users_table.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public List<Person> getAll() {
        return service.getAll();
    }

    @GetMapping("/by-id")
    public ResponseEntity<Person> getById(@RequestParam String name,
                                          @RequestParam String surname,
                                          @RequestParam Integer age) {
        PersonId id = new PersonId(name, surname, age);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        // ожидает JSON с id: {name,surname,age}
        return ResponseEntity.ok(service.create(person));
    }

    @PutMapping("/by-id")
    public ResponseEntity<Person> update(@RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam Integer age,
                                         @RequestBody Person person) {
        PersonId id = new PersonId(name, surname, age);
        return service.update(id, person)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/by-id")
    public ResponseEntity<Void> delete(@RequestParam String name,
                                       @RequestParam String surname,
                                       @RequestParam Integer age) {
        PersonId id = new PersonId(name, surname, age);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 1) city -> List<Person>
    @GetMapping("/city/{city}")
    public List<Person> getByCity(@PathVariable String city) {
        return service.getByCity(city);
    }

    // 2) age -> меньше age и сортировка по возрастанию
    @GetMapping("/age-lt/{age}")
    public List<Person> getYoungerThan(@PathVariable Integer age) {
        return service.getYoungerThan(age);
    }

    // 3) name + surname -> Optional<Person>
    @GetMapping("/full-name")
    public ResponseEntity<Person> getByNameAndSurname(@RequestParam String name,
                                                      @RequestParam String surname) {
        return service.getByNameAndSurname(name, surname)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
