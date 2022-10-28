package com.clouddevg.ita.controller.v1.api;

import com.clouddevg.ita.controller.v1.request.BookTicketRequest;
import com.clouddevg.ita.controller.v1.request.GetFlightPlansRequest;
import com.clouddevg.ita.dto.entity.flight.FlightDto;
import com.clouddevg.ita.dto.entity.flight.FlightPlanDto;
import com.clouddevg.ita.dto.entity.flight.TicketDto;
import com.clouddevg.ita.dto.entity.user.UserDto;
import com.clouddevg.ita.dto.response.Response;
import com.clouddevg.ita.service.FlightReservationService;
import com.clouddevg.ita.service.UserService;
import com.clouddevg.ita.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/reservation")
@Api(value = "ita-application", description = "Operations pertaining to pilot management and ticket booking in the ITA application")
public class FlightReservationController {

    private final FlightReservationService flightReservationService;

    private final UserService userService;

    public FlightReservationController(FlightReservationService flightReservationService, UserService userService) {
        this.flightReservationService = flightReservationService;
        this.userService = userService;
    }

    @GetMapping("/spaceports")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getAllSpaceports() {
        return Response
                .ok()
                .setPayload(flightReservationService.getAllSpaceports());
    }

    @GetMapping("/flightsbyspaceports")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getFlightsBySpaceports(@RequestBody @Valid GetFlightPlansRequest getFlightPlansRequest) {
        List<FlightDto> flightDtos = flightReservationService.getAvailableFlightsBetweenSpaceports(
                getFlightPlansRequest.getOriginSpaceport(),
                getFlightPlansRequest.getDestinationSpaceport());
        if (!flightDtos.isEmpty()) {
            return Response.ok().setPayload(flightDtos);
        }
        return Response.notFound()
                .setErrors(String.format("No flights between source spaceport - '%s' and destination spaceport - '%s' are available at this time.", getFlightPlansRequest.getOriginSpaceport(), getFlightPlansRequest.getDestinationSpaceport()));
    }

    @GetMapping("/flightplans")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getFlightPlans(@RequestBody @Valid GetFlightPlansRequest getFlightPlansRequest) {
        List<FlightPlanDto> flightPlanDtos = flightReservationService.getAvailableFlightPlans(
                getFlightPlansRequest.getOriginSpaceport(),
                getFlightPlansRequest.getDestinationSpaceport(),
                DateUtils.formattedDate(getFlightPlansRequest.getFlightDate()));
        if (!flightPlanDtos.isEmpty()) {
            return Response.ok().setPayload(flightPlanDtos);
        }
        return Response.notFound()
                .setErrors(String.format("No flights between source spaceport - '%s' and destination spaceport - '%s' on date - '%s' are available at this time.", getFlightPlansRequest.getOriginSpaceport(), getFlightPlansRequest.getDestinationSpaceport(), DateUtils.formattedDate(getFlightPlansRequest.getFlightDate())));
    }

    @PostMapping("/bookticket")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response bookTicket(@RequestBody @Valid BookTicketRequest bookTicketRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) auth.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            Optional<FlightDto> flightDto = Optional
                    .ofNullable(flightReservationService.getFlightById(bookTicketRequest.getFlightID()));
            if (flightDto.isPresent()) {
                Optional<FlightPlanDto> flightPlanDto = Optional
                        .ofNullable(flightReservationService.getFlightPlan(flightDto.get(), DateUtils.formattedDate(bookTicketRequest.getFlightDate()), true));
                if (flightPlanDto.isPresent()) {
                    Optional<TicketDto> ticketDto = Optional
                            .ofNullable(flightReservationService.bookTicket(flightPlanDto.get(), userDto.get()));
                    if (ticketDto.isPresent()) {
                        return Response.ok().setPayload(ticketDto.get());
                    }
                }
            }
        }
        return Response.badRequest().setErrors("Unable to process ticket booking.");
    }
}
