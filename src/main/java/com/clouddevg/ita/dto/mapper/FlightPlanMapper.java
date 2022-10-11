package com.clouddevg.ita.dto.mapper;

import com.clouddevg.ita.dto.entity.flight.FlightPlanDto;
import com.clouddevg.ita.entity.flight.Flight;
import com.clouddevg.ita.entity.flight.FlightPlan;

public class FlightPlanMapper {
    public static FlightPlanDto toFlightPlanDto(FlightPlan flightPlan) {
        Flight flightDetails = flightPlan.getFlightDetail();
        return new FlightPlanDto()
                .setId(flightPlan.getId())
                .setFlightId(flightDetails.getId())
                .setSpacecraftCode(flightDetails.getSpacecraft().getCode())
                .setAvailableSeats(flightPlan.getAvailableSeats())
                .setFare(flightDetails.getFare())
                .setFlightDuration(flightDetails.getFlightDuration())
                .setOriginSpaceport(flightDetails.getOriginSpaceport().getName())
                .setDestinationSpaceport(flightDetails.getDestinationSpaceport().getName());
    }
}
