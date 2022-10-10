package com.clouddevg.ita.dto.mapper;

import com.clouddevg.ita.dto.entity.flight.FlightDto;
import com.clouddevg.ita.entity.flight.Flight;

public class FlightMapper {
    public static FlightDto toFlightDto(Flight flight) {
        return new FlightDto()
                .setId(flight.getId())
                .setPilotCode(flight.getPilot().getCode())
                .setOriginSpaceportCode(flight.getOriginSpaceport().getCode())
                .setOriginSpaceportName(flight.getOriginSpaceport().getName())
                .setDestinationSpaceportCode(flight.getDestinationSpaceport().getCode())
                .setDestinationSpaceportName(flight.getDestinationSpaceport().getName())
                .setSpacecraftCode(flight.getSpacecraft().getCode())
                .setDepartureTime(flight.getDepartureTime())
                .setFare(flight.getFare());
    }
}
