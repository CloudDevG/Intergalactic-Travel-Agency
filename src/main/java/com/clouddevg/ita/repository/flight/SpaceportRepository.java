package com.clouddevg.ita.repository.flight;

import com.clouddevg.ita.entity.flight.Spaceport;
import org.springframework.data.repository.CrudRepository;

public interface SpaceportRepository extends CrudRepository<Spaceport, Long> {
    Spaceport findByCode(String code);
}
