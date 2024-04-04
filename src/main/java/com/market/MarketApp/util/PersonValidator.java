package com.market.MarketApp.util;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService service;
    @Autowired
    public PersonValidator(PersonService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person)o;

        if(service.getPersonByEmail(person.getEmail()).isPresent()){
            errors.rejectValue("email","","Sorry but this email already exist!");
        }

    }
}
