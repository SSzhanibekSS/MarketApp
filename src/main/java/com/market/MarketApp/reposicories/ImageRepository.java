package com.market.MarketApp.reposicories;

import com.market.MarketApp.models.ImageMeta;
import com.market.MarketApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageMeta, Integer> {
    Optional<ImageMeta> findByOwner(Person person);
    void removeByOwner (Person person);
}
