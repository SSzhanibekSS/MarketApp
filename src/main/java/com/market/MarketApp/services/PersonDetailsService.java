package com.market.MarketApp.services;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.reposicories.PersonDetailsRepository;
import com.market.MarketApp.security.PersonDetails;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonDetailsRepository repository;
    @Autowired
    public PersonDetailsService(PersonDetailsRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = repository.findByUsername(s);

        if(person.isEmpty())
            throw new UsernameNotFoundException("User with this name not found");


        return new PersonDetails(person.get());
    }
}
