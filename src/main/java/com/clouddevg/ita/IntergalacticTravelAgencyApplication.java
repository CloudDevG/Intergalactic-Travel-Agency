package com.clouddevg.ita;

import com.clouddevg.ita.entity.flight.*;
import com.clouddevg.ita.entity.user.Role;
import com.clouddevg.ita.entity.user.User;
import com.clouddevg.ita.entity.user.UserRoles;
import com.clouddevg.ita.repository.flight.*;
import com.clouddevg.ita.repository.user.RoleRepository;
import com.clouddevg.ita.repository.user.UserRepository;
import com.clouddevg.ita.util.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class IntergalacticTravelAgencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntergalacticTravelAgencyApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository,
                           SpaceportRepository spaceportRepository, PilotRepository pilotRepository,
                           SpacecraftRepository spacecraftRepository, FlightRepository flightRepository,
                           FlightPlanRepository flightPlanRepository) {
        return args -> {

            //Create Admin and Passenger Roles
            Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole(UserRoles.ADMIN);
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole(UserRoles.PASSENGER);
                roleRepository.save(userRole);
            }

            //Create an Admin user
            User admin = userRepository.findByEmail("admin@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin@gmail.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Noelle")
                        .setLastName("Doe")
                        .setMobileNumber("9425094250")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(admin);
            }

            //Create a Passenger user
            User passenger = userRepository.findByEmail("passenger@gmail.com");
            if (passenger == null) {
                passenger = new User()
                        .setEmail("passenger@gmail.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Luke")
                        .setLastName("Skywalker")
                        .setMobileNumber("8000110008")
                        .setRoles(Arrays.asList(userRole));
                userRepository.save(passenger);
            }

            //Create four Spaceports
            Spaceport spaceportA = spaceportRepository.findByCode("SW001");
            if (spaceportA == null) {
                spaceportA = new Spaceport()
                        .setName("Coruscant")
                        .setDetail("Home of the Jedi Temple")
                        .setCode("SW001");
                spaceportRepository.save(spaceportA);
            }

            Spaceport spaceportB = spaceportRepository.findByCode("SW002");
            if (spaceportB == null) {
                spaceportB = new Spaceport()
                        .setName("Tatooine")
                        .setDetail("Home of Luke Skywalker and Mo's Cantina")
                        .setCode("SW002");
                spaceportRepository.save(spaceportB);
            }

            Spaceport spaceportC = spaceportRepository.findByCode("SW003");
            if (spaceportC == null) {
                spaceportC = new Spaceport()
                        .setName("Naboo")
                        .setDetail("Home of the Amadala Clan")
                        .setCode("SW003");
                spaceportRepository.save(spaceportC);
            }

            Spaceport spaceportD = spaceportRepository.findByCode("SW004");
            if (spaceportD == null) {
                spaceportD = new Spaceport()
                        .setName("Kashyyyk")
                        .setDetail("Home of Chewbacca & The Wookies")
                        .setCode("STPD");
                spaceportRepository.save(spaceportD);
            }

            //Create a Pilot
            Pilot pilotA = pilotRepository.findByCode("PILOT-A");
            if (pilotA == null) {
                pilotA = new Pilot()
                        .setName("Han Solo")
                        .setCode("PILOT-A")
                        .setDetails("I got a bad feeling about this.....")
                        .setOwner(admin);
                pilotRepository.save(pilotA);
            }

            //Create a Spacecraft
            Spacecraft spacecraftA = spacecraftRepository.findByCode("PILOT-A-MF");
            if (spacecraftA == null) {
                spacecraftA = new Spacecraft()
                        .setCode("PILOT-A-MF")
                        .setPilot(pilotA)
                        .setCapacity(60);
                spacecraftRepository.save(spacecraftA);
            }

            //Add spacecraftA to set of spacecrafts owned by Pilot 'PILOTA'
            if (pilotA.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                pilotA.setSpacecrafts(spacecrafts);
                pilotA.getSpacecrafts().add(spacecraftA);
                pilotRepository.save(pilotA);
            }

            //Create a Flight
            Flight flight = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(spaceportA, spaceportB, spacecraftA);
            if (flight == null) {
                flight = new Flight()
                        .setOriginSpaceport(spaceportA)
                        .setDestinationSpaceport(spaceportB)
                        .setSpacecraft(spacecraftA)
                        .setPilot(pilotA)
                        .setFare(10000)
                        .setFlightDuration(700);
                flightRepository.save(flight);
            }

            //Create a Flight Plan
            FlightPlan flightPlan = flightPlanRepository.findByFlightDetailAndFlightDate(flight, DateUtils.todayStr());
            if (flightPlan == null) {
                flightPlan = new FlightPlan()
                        .setFlightDetail(flight)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flight.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlan);
            }
        };
    }
}

