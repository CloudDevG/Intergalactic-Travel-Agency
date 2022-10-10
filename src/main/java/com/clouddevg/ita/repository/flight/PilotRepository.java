package com.clouddevg.ita.repository.flight;

import com.clouddevg.ita.entity.flight.Pilot;
import com.clouddevg.ita.entity.user.User;
import org.springframework.data.repository.CrudRepository;

public interface PilotRepository extends CrudRepository<Pilot, Long> {

    Pilot findByCode(String pilotCode);

    Pilot findByOwner(User owner);

    Pilot findByName(String name);
}
