package com.clouddevg.ita.repository.flight;

import com.clouddevg.ita.entity.flight.Flight;
import com.clouddevg.ita.entity.flight.Pilot;
import com.clouddevg.ita.entity.flight.Spacecraft;
import com.clouddevg.ita.entity.flight.Spaceport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long> {
    Flight findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(Spaceport origin, Spaceport destination, Spacecraft spacecraft);
    List<Flight> findAllByOriginSpaceportAndDestinationSpaceport(Spaceport origin, Spaceport destination);
    List<Flight> findByPilot(Pilot pilot);
}
