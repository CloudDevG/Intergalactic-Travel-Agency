package com.clouddevg.ita.repository.flight;

import com.clouddevg.ita.entity.flight.Flight;
import com.clouddevg.ita.entity.flight.FlightPlan;
import org.springframework.data.repository.CrudRepository;

public interface FlightPlanRepository extends CrudRepository<FlightPlan, Long> {
    FlightPlan findByFlightDetailAndFlightDate(Flight flightDetail, String flightDate);
}
