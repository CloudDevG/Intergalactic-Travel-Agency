package com.clouddevg.ita.repository.flight;

import com.clouddevg.ita.entity.flight.Pilot;
import com.clouddevg.ita.entity.flight.Spacecraft;
import org.springframework.data.repository.CrudRepository;

public interface SpacecraftRepository extends CrudRepository<Spacecraft, Long> {
    Spacecraft findByCode(String spacecraftCode);
    Spacecraft findByCodeAndPilot(String spacecraftCode, Pilot pilot);
}
