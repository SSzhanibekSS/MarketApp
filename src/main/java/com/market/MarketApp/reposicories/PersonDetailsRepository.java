package com.market.MarketApp.reposicories;

import com.market.MarketApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDetailsRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String name);
}
