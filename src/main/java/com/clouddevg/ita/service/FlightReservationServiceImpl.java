package com.clouddevg.ita.service;

import com.clouddevg.ita.dto.entity.flight.*;
import com.clouddevg.ita.dto.entity.user.UserDto;
import com.clouddevg.ita.dto.mapper.FlightMapper;
import com.clouddevg.ita.dto.mapper.FlightPlanMapper;
import com.clouddevg.ita.dto.mapper.TicketMapper;
import com.clouddevg.ita.entity.flight.*;
import com.clouddevg.ita.entity.user.User;
import com.clouddevg.ita.exception.EntityType;
import com.clouddevg.ita.exception.ExceptionType;
import com.clouddevg.ita.exception.ITAException;
import com.clouddevg.ita.repository.flight.*;
import com.clouddevg.ita.dto.entity.*;
import com.clouddevg.ita.repository.user.UserRepository;
import com.clouddevg.ita.util.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.clouddevg.ita.exception.EntityType.*;
import static com.clouddevg.ita.exception.ExceptionType.*;

@Component
public class FlightReservationServiceImpl implements FlightReservationService {

    private final PilotRepository pilotRepository;

    private final SpacecraftRepository spacecraftRepository;


    private final SpaceportRepository spaceportRepository;


    private final TicketRepository ticketRepository;


    private final FlightRepository flightRepository;


    private final FlightPlanRepository flightPlanRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public FlightReservationServiceImpl(PilotRepository pilotRepository, SpacecraftRepository spacecraftRepository,
                                        SpaceportRepository spaceportRepository, TicketRepository ticketRepository,
                                        FlightRepository flightRepository, FlightPlanRepository flightPlanRepository,
                                        UserRepository userRepository, ModelMapper modelMapper) {
        this.pilotRepository = pilotRepository;
        this.spacecraftRepository = spacecraftRepository;
        this.spaceportRepository = spaceportRepository;
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.flightPlanRepository = flightPlanRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<SpaceportDto> getAllSpaceports() {
        return StreamSupport
                .stream(spaceportRepository.findAll().spliterator(), false)
                .map(stop -> modelMapper.map(stop, SpaceportDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public SpaceportDto getSpaceportByCode(String spaceportCode) {
        Optional<Spaceport> spaceport = Optional.ofNullable(spaceportRepository.findByCode(spaceportCode));
        if (spaceport.isPresent()) {
            return modelMapper.map(spaceport.get(), SpaceportDto.class);
        }
        throw exception(SPACEPORT, ENTITY_NOT_FOUND, spaceportCode);
    }

    @Override
    public PilotDto getPilot(UserDto userDto) {
        User user = getUser(userDto.getEmail());
        if (user != null) {
            Optional<Pilot> pilot = Optional.ofNullable(pilotRepository.findByOwner(user));
            if (pilot.isPresent()) {
                return modelMapper.map(pilot.get(), PilotDto.class);
            }
            throw exceptionWithId(PILOT, ENTITY_NOT_FOUND, 2, user.getEmail());
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public PilotDto addPilot(PilotDto pilotDto) {
        User admin = getUser(pilotDto.getOwner().getEmail());
        if (admin != null) {
            Optional<Pilot> pilot = Optional.ofNullable(pilotRepository.findByName(pilotDto.getName()));
            if (!pilot.isPresent()) {
                Pilot pilotModel = new Pilot()
                        .setName(pilotDto.getName())
                        .setDetails(pilotDto.getDetails())
                        .setCode(RandomStringUtils.getAlphaNumericString(8, pilotDto.getName()))
                        .setOwner(admin);
                pilotRepository.save(pilotModel);
                return modelMapper.map(pilotModel, PilotDto.class);
            }
            throw exception(PILOT, DUPLICATE_ENTITY, pilotDto.getName());
        }
        throw exception(USER, ENTITY_NOT_FOUND, pilotDto.getOwner().getEmail());
    }

    @Transactional
    public PilotDto updatePilot(PilotDto pilotDto, SpacecraftDto spacecraftDto) {
        Pilot pilot = getPilot(pilotDto.getCode());
        if (pilot != null) {
            if (spacecraftDto != null) {
                Optional<Spacecraft> spacecraft = Optional.ofNullable(spacecraftRepository.findByCodeAndPilot(spacecraftDto.getCode(), pilot));
                if (!spacecraft.isPresent()) {
                    Spacecraft spacecraftModel = new Spacecraft()
                            .setPilot(pilot)
                            .setCode(spacecraftDto.getCode())
                            .setCapacity(spacecraftDto.getCapacity())
                            .setMake(spacecraftDto.getMake());
                    spacecraftRepository.save(spacecraftModel);
                    if (pilot.getSpacecrafts() == null) {
                        pilot.setSpacecrafts(new HashSet<>());
                    }
                    pilot.getSpacecrafts().add(spacecraftModel);
                    return modelMapper.map(pilotRepository.save(pilot), PilotDto.class);
                }
                throw exceptionWithId(SPACECRAFT, DUPLICATE_ENTITY, 2, spacecraftDto.getCode(), pilotDto.getCode());
            } else {
                pilot.setName(pilotDto.getName())
                        .setDetails(pilotDto.getDetails());
                return modelMapper.map(pilotRepository.save(pilot), PilotDto.class);
            }
        }
        throw exceptionWithId(PILOT, ENTITY_NOT_FOUND, 2, pilotDto.getOwner().getEmail());
    }

    @Override
    public FlightDto getFlightById(Long flightID) {
        Optional<Flight> flight = flightRepository.findById(flightID);
        if (flight.isPresent()) {
            return FlightMapper.toFlightDto(flight.get());
        }
        throw exception(FLIGHT, ENTITY_NOT_FOUND, flightID.toString());
    }

    @Override
    @Transactional
    public List<FlightDto> addFlight(FlightDto flightDto) {
        Spaceport originSpaceport = getSpaceport(flightDto.getOriginSpaceportCode());
        if (originSpaceport != null) {
            Spaceport destinationSpaceport = getSpaceport(flightDto.getDestinationSpaceportCode());
            if (destinationSpaceport != null) {
                if (!originSpaceport.getCode().equalsIgnoreCase(destinationSpaceport.getCode())) {
                    Pilot pilot = getPilot(flightDto.getPilotCode());
                    if (pilot != null) {
                        Spacecraft spacecraft = getSpacecraft(flightDto.getSpacecraftCode());
                        if (spacecraft != null) {
                            List<FlightDto> flights = new ArrayList<>(2);
                            Flight toFlight = new Flight()
                                    .setOriginSpaceport(originSpaceport)
                                    .setDestinationSpaceport(destinationSpaceport)
                                    .setPilot(pilot)
                                    .setSpacecraft(spacecraft)
                                    .setDepartureTime(flightDto.getDepartureTime())
                                    .setFare(flightDto.getFare());
                            flights.add(FlightMapper.toFlightDto(flightRepository.save(toFlight)));

                            Flight fromFlight = new Flight()
                                    .setOriginSpaceport(destinationSpaceport)
                                    .setDestinationSpaceport(originSpaceport)
                                    .setPilot(pilot)
                                    .setSpacecraft(spacecraft)
                                    .setDepartureTime(flightDto.getDepartureTime())
                                    .setFare(flightDto.getFare());
                            flights.add(FlightMapper.toFlightDto(flightRepository.save(fromFlight)));
                            return flights;
                        }
                        throw exception(SPACECRAFT, ENTITY_NOT_FOUND, flightDto.getSpacecraftCode());
                    }
                    throw exception(PILOT, ENTITY_NOT_FOUND, flightDto.getPilotCode());
                }
                throw exception(FLIGHT, ENTITY_EXCEPTION, "");
            }
            throw exception(SPACEPORT, ENTITY_NOT_FOUND, flightDto.getDestinationSpaceportCode());
        }
        throw exception(SPACEPORT, ENTITY_NOT_FOUND, flightDto.getOriginSpaceportCode());
    }

    @Override
    public List<FlightDto> getPilotFlights(String pilotCode) {
        Pilot pilot = getPilot(pilotCode);
        if (pilot != null) {
            List<Flight> pilotFlights = flightRepository.findByPilot(pilot);
            if (!pilotFlights.isEmpty()) {
                return pilotFlights
                        .stream()
                        .map(flight -> FlightMapper.toFlightDto(flight))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        }
        throw exception(PILOT, ENTITY_NOT_FOUND, pilotCode);
    }

    @Override
    public List<FlightDto> getAvailableFlightsBetweenSpaceports(String originSpaceportCode, String destinationSpaceportCode) {
        List<Flight> availableFlights = findFlightsBetweenSpaceports(originSpaceportCode, destinationSpaceportCode);
        if (!availableFlights.isEmpty()) {
            return availableFlights
                    .stream()
                    .map(flight -> FlightMapper.toFlightDto(flight))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<FlightPlanDto> getAvailableFlightPlans(String originSpaceportCode, String destinationSpaceportCode, String flightDate) {
        List<Flight> availableFlights = findFlightsBetweenSpaceports(originSpaceportCode, destinationSpaceportCode);
        if (!availableFlights.isEmpty()) {
            return availableFlights
                    .stream()
                    .map(flight -> getFlightPlan(FlightMapper.toFlightDto(flight), flightDate, true))
                    .filter(flightPlanDto -> flightPlanDto != null)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public FlightPlanDto getFlightPlan(FlightDto flightDto, String flightDate, boolean createScheduleForFlightPlan) {
        Optional<Flight> flight = flightRepository.findById(flightDto.getId());
        if (flight.isPresent()) {
            Optional<FlightPlan> flightPlan = Optional.ofNullable(flightPlanRepository.findByFlightDetailAndFlightDate(flight.get(), flightDate));
            if (flightPlan.isPresent()) {
                return FlightPlanMapper.toFlightPlanDto(flightPlan.get());
            } else {
                if (createScheduleForFlightPlan) { //create the schedule
                    FlightPlan tempFlightPlan = new FlightPlan()
                            .setFlightDetail(flight.get())
                            .setFlightDate(flightDate)
                            .setAvailableSeats(flight.get().getSpacecraft().getCapacity());
                    return FlightPlanMapper.toFlightPlanDto(flightPlanRepository.save(tempFlightPlan));
                } else {
                    throw exceptionWithId(FLIGHT, ENTITY_NOT_FOUND, 2, flightDto.getId().toString(), flightDate);
                }
            }
        }
        throw exception(FLIGHT, ENTITY_NOT_FOUND, flightDto.getId().toString());
    }

    @Override
    @Transactional
    public TicketDto bookTicket(FlightPlanDto flightPlanDto, UserDto userDto) {
        User user = getUser(userDto.getEmail());
        if (user != null) {
            Optional<FlightPlan> flightPlan = flightPlanRepository.findById(flightPlanDto.getId());
            if (flightPlan.isPresent()) {
                Ticket ticket = new Ticket()
                        .setCancellable(false)
                        .setFlightDate(flightPlan.get().getFlightDate())
                        .setPassenger(user)
                        .setFlightPlan(flightPlan.get())
                        .setSeatNumber(flightPlan.get().getFlightDetail().getSpacecraft().getCapacity() - flightPlan.get().getAvailableSeats());
                ticketRepository.save(ticket);
                flightPlan.get().setAvailableSeats(flightPlan.get().getAvailableSeats() - 1); //reduce availability by 1
                flightPlanRepository.save(flightPlan.get());//update schedule
                return TicketMapper.toTicketDto(ticket);
            }
            throw exceptionWithId(FLIGHT, ENTITY_NOT_FOUND, 2, flightPlanDto.getFlightId().toString(), flightPlanDto.getFlightDate());
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }


    private List<Flight> findFlightsBetweenSpaceports(String originSpaceportCode, String destinationSpaceportCode) {
        Optional<Spaceport> originSpaceport = Optional
                .ofNullable(spaceportRepository.findByCode(originSpaceportCode));
        if (originSpaceport.isPresent()) {
            Optional<Spaceport> destinationSpaceport = Optional
                    .ofNullable(spaceportRepository.findByCode(destinationSpaceportCode));
            if (destinationSpaceport.isPresent()) {
                List<Flight> availableFlights = flightRepository.findAllByOriginSpaceportAndDestinationSpaceport(originSpaceport.get(), destinationSpaceport.get());
                if (!availableFlights.isEmpty()) {
                    return availableFlights;
                }
                return Collections.emptyList();
            }
            throw exception(SPACEPORT, ENTITY_NOT_FOUND, destinationSpaceportCode);
        }
        throw exception(SPACEPORT, ENTITY_NOT_FOUND, originSpaceportCode);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Spaceport getSpaceport(String spaceportCode) {
        return spaceportRepository.findByCode(spaceportCode);
    }

    private Spacecraft getSpacecraft(String spacecraftCode) {
        return spacecraftRepository.findByCode(spacecraftCode);
    }

    private Pilot getPilot(String pilotCode) {
        return pilotRepository.findByCode(pilotCode);
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ITAException.throwException(entityType, exceptionType, args);
    }

    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return ITAException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
