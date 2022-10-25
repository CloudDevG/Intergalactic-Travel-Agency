package com.clouddevg.ita.service;

import com.clouddevg.ita.dto.entity.flight.*;
import com.clouddevg.ita.dto.entity.user.UserDto;

import java.util.List;
import java.util.Set;


public interface FlightReservationService {

    Set<SpaceportDto> getAllSpaceports();

    SpaceportDto getSpaceportByCode(String spaceportCode);

    //Pilot related methods
    PilotDto getPilot(UserDto userDto);

    PilotDto addPilot(PilotDto pilotDto);

    PilotDto updatePilot(PilotDto pilotDto, SpacecraftDto spacecraftDto);

    //Flight related methods
    FlightDto getFlightById(Long flightID);

    List<FlightDto> addFlight(FlightDto flightDto);

    List<FlightDto> getPilotFlights(String pilotCode);

    List<FlightDto> getAvailableFlightsBetweenSpaceports(String originSpaceportCode, String destinationSpaceportCode);

    //Flight Plan related methods
    List<FlightPlanDto> getAvailableFlightPlans(String originSpaceportCode, String destinationSpaceportCode, String flightDate);

    FlightPlanDto getFlightPlan(FlightDto flightDto, String flightDate, boolean createScheduleForFlightPlan);

    //Ticket related method
    TicketDto bookTicket(FlightPlanDto flightPlanDto, UserDto passenger);
}
