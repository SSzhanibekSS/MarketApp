package com.market.MarketApp.services;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.reposicories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository repository;
    public final PasswordEncoder encoder;

    @Autowired
    public PersonService(PersonRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }
    @Transactional
    public void register(Person person){

        person.setPassword(encoder.encode(person.getPassword()));

        repository.save(person);
    }

    public Person getPersonById(int id){
        return repository.getById(id);
    }
    public Optional<Person> getPersonByEmail(String email){
        return repository.findByEmail(email);
    }
}
