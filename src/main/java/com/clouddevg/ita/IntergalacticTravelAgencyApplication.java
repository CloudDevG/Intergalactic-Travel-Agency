package com.clouddevg.ita;

import com.clouddevg.ita.dto.mapper.TicketMapper;
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
import java.util.Optional;
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
                           FlightPlanRepository flightPlanRepository, TicketRepository ticketRepository) {
        return args -> {

            // <======= CREATE (2) USER-ROLE TYPES =======>

            // #1: Admin a.k.a. "Pilot"
            Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole(UserRoles.ADMIN);
                roleRepository.save(adminRole);
            }

            // #2 User a.k.a. "Passenger"
            Role userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole(UserRoles.PASSENGER);
                roleRepository.save(userRole);
            }

            // <======= CREATE (6) PILOT (ADMIN) USERS =======>

            // #1: Rei Skywalker (Star Wars)
            User reiSkywalker = userRepository.findByEmail("rei.skywalker@ita.com");
            if (reiSkywalker == null) {
                reiSkywalker = new User()
                        .setEmail("rei.skywalker@ita.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Rei")
                        .setLastName("Skywalker")
                        .setMobileNumber("9425094250")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(reiSkywalker);
            }
            Pilot rSkywalker = pilotRepository.findByCode("P-0001");
            if (rSkywalker == null) {
                rSkywalker = new Pilot()
                        .setName("Rei Skywalker")
                        .setCode("P-0001")
                        .setDetails("May The Force Be With You")
                        .setOwner(reiSkywalker);
                pilotRepository.save(rSkywalker);
            }

            // #2: Han Solo (Star Wars)
            User hanSolo = userRepository.findByEmail("han.solo@ita.com");
            if (hanSolo == null) {
                hanSolo = new User()
                        .setEmail("han.solo@ita.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Han")
                        .setLastName("Solo")
                        .setMobileNumber("7659021456")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(hanSolo);
            }
            Pilot hSolo = pilotRepository.findByCode("P-0002");
            if (hSolo == null) {
                hSolo = new Pilot()
                        .setName("Han Solo")
                        .setCode("P-0002")
                        .setDetails("I got a bad feeling about this.....")
                        .setOwner(hanSolo);
                pilotRepository.save(hSolo);
            }

            // #3: Padme Amidala (Star Wars)
            User padmeAmidala = userRepository.findByEmail("padme.amidala@ita.com");
            if (padmeAmidala == null) {
                padmeAmidala = new User()
                        .setEmail("padme.amidala@ita.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Padme")
                        .setLastName("Amidala")
                        .setMobileNumber("2316758960")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(padmeAmidala);
            }
            Pilot pAmidala = pilotRepository.findByCode("P-0003");
            if (pAmidala == null) {
                pAmidala = new Pilot()
                        .setName("Padme Amidala")
                        .setCode("P-0003")
                        .setDetails("For the Galactic Republic!")
                        .setOwner(padmeAmidala);
                pilotRepository.save(pAmidala);
            }

            // #4: Capt. James T. Kirk (Star Trek)
            User jamesKirk = userRepository.findByEmail("james.kirk@ita.com");
            if (jamesKirk == null) {
                jamesKirk = new User()
                        .setEmail("james.kirk@ita.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("James")
                        .setLastName("Kirk")
                        .setMobileNumber("3473216190")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(jamesKirk);
            }
            Pilot jKirk = pilotRepository.findByCode("P-0004");
            if (jKirk == null) {
                jKirk = new Pilot()
                        .setName("Capt. James T. Kirk")
                        .setCode("P-0004")
                        .setDetails("Space: The Final Frontier....")
                        .setOwner(jamesKirk);
                pilotRepository.save(jKirk);
            }

            // #5: Dr. Elizabeth Shaw (Alien/Prometheus)
            User elizabethShaw = userRepository.findByEmail("elizabeth.shaw@ita.com");
            if (elizabethShaw == null) {
                elizabethShaw = new User()
                        .setEmail("elizabeth.shaw@ita.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Elizabeth")
                        .setLastName("Shaw")
                        .setMobileNumber("9036127213")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(elizabethShaw);
            }
            Pilot eShaw = pilotRepository.findByCode("P-0005");
            if (eShaw == null) {
                eShaw = new Pilot()
                        .setName("Dr. Elizabeth Shaw")
                        .setCode("P-0005")
                        .setDetails("On a Quest To Find Our Origins Amongst The Stars")
                        .setOwner(elizabethShaw);
                pilotRepository.save(eShaw);
            }

            // #6: Master Chief (Halo)
            User masterChief = userRepository.findByEmail("master.chief@ita.com");
            if (masterChief == null) {
                masterChief = new User()
                        .setEmail("master.chief@ita.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("John")
                        .setLastName("117")
                        .setMobileNumber("7543902156")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(masterChief);
            }
            Pilot mChief = pilotRepository.findByCode("P-0006");
            if (mChief == null) {
                mChief = new Pilot()
                        .setName("Master Chief")
                        .setCode("P-0006")
                        .setDetails("Wake Me When You Need Me...")
                        .setOwner(masterChief);
                pilotRepository.save(mChief);
            }

            // <======= CREATE (4) PASSENGER USERS =======>

            // #1: Leia Organa (Star Wars)
            User lOrgana = userRepository.findByEmail("leia.organa@starwars.com");
            if (lOrgana == null) {
                lOrgana = new User()
                        .setEmail("leia.organa@starwars.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Leia")
                        .setLastName("Organa")
                        .setMobileNumber("7879084312")
                        .setRoles(Arrays.asList(userRole));
                userRepository.save(lOrgana);
            }

            // #2: Master Yoda (Star Wars)
            User yoda = userRepository.findByEmail("master.yoda@starwars.com");
            if (yoda == null) {
                yoda = new User()
                        .setEmail("master.yoda@starwars.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Master")
                        .setLastName("Yoda")
                        .setMobileNumber("3057214509")
                        .setRoles(Arrays.asList(userRole));
                userRepository.save(yoda);
            }

            // #3: LT. Nyota Uhura (Star Trek)
            User nUhura = userRepository.findByEmail("nyota.uhura@startrek.com");
            if (nUhura == null) {
                nUhura = new User()
                        .setEmail("nyota.uhura@startrek.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Nyota")
                        .setLastName("Uhura")
                        .setMobileNumber("9015184108")
                        .setRoles(Arrays.asList(userRole));
                userRepository.save(nUhura);
            }

            // #4: Anakin Skywalker (Star Wars)
            User aSkywalker = userRepository.findByEmail("anakin.skywalker@starwars.com");
            if (aSkywalker == null) {
                aSkywalker = new User()
                        .setEmail("anakin.skywalker@starwars.com")
                        .setPassword("$2a$10$Wfvx5dQ3LpvnH/jAfkiWeuL/K43dEN2xCE53eSqz1lW5K17U30vkm") // "123456"
                        .setFirstName("Anakin")
                        .setLastName("Skywalker")
                        .setMobileNumber("5058741294")
                        .setRoles(Arrays.asList(userRole));
                userRepository.save(aSkywalker);
            }

            // <======= CREATE (20) SPACEPORTS =======>

            // #1: Coruscant (Star Wars)
            Spaceport coruscant = spaceportRepository.findByCode("SW-0001");
            if (coruscant == null) {
                coruscant = new Spaceport()
                        .setName("Coruscant")
                        .setDetail("Home of the Jedi Temple and Intergalactic Republic")
                        .setCode("SW-0001");
                spaceportRepository.save(coruscant);
            }

            // #2: Tatooine (Star Wars)
            Spaceport tatooine = spaceportRepository.findByCode("SW-0002");
            if (tatooine == null) {
                tatooine = new Spaceport()
                        .setName("Tatooine")
                        .setDetail("Home of Luke Skywalker and Mo's Cantina")
                        .setCode("SW-0002");
                spaceportRepository.save(tatooine);
            }

            // #3: Naboo (Star Wars)
            Spaceport naboo = spaceportRepository.findByCode("SW-0003");
            if (naboo == null) {
                naboo = new Spaceport()
                        .setName("Naboo")
                        .setDetail("Home of the Amidala Clan")
                        .setCode("SW-0003");
                spaceportRepository.save(naboo);
            }

            // #4: Kashyyyk (Star Wars)
            Spaceport kashyyyk = spaceportRepository.findByCode("SW-0004");
            if (kashyyyk == null) {
                kashyyyk = new Spaceport()
                        .setName("Kashyyyk")
                        .setDetail("Home of Chewbacca & The Wookies")
                        .setCode("SW-0004");
                spaceportRepository.save(kashyyyk);
            }

            // #5: Kamino (Star Wars)
            Spaceport kamino = spaceportRepository.findByCode("SW-0005");
            if (kamino == null) {
                kamino = new Spaceport()
                        .setName("Kamino")
                        .setDetail("Home of the Kaminoan people, Jango Fett, and the Republic's Clone Army")
                        .setCode("SW-0005");
                spaceportRepository.save(kamino);
            }

            // #6: Batuu (Star Wars)
            Spaceport batuu = spaceportRepository.findByCode("SW-0006");
            if (batuu == null) {
                batuu = new Spaceport()
                        .setName("Batuu")
                        .setDetail("A Remote Planet in the Outer Rim and Home To The Ancients")
                        .setCode("SW-0006");
                spaceportRepository.save(batuu);
            }

            // #7: Hoth (Star Wars)
            Spaceport hoth = spaceportRepository.findByCode("SW-0007");
            if (hoth == null) {
                hoth = new Spaceport()
                        .setName("Hoth")
                        .setDetail("A Frozen Tundra and a Famous Battle-Site between The Empire and Rebellion")
                        .setCode("SW-0007");
                spaceportRepository.save(hoth);
            }

            // #8: Endor (Star Wars)
            Spaceport endor = spaceportRepository.findByCode("SW-0008");
            if (endor == null) {
                endor = new Spaceport()
                        .setName("Endor")
                        .setDetail("Home of Ewoks and the site of the collapse of the Galactic Empire")
                        .setCode("SW-0008");
                spaceportRepository.save(endor);
            }

            // #9: Earth (Star Trek)
            Spaceport earth = spaceportRepository.findByCode("ST-0001");
            if (earth == null) {
                earth = new Spaceport()
                        .setName("Earth")
                        .setDetail("Home of Humanity, Starfleet's HQ, and ITA's primary spaceport")
                        .setCode("ST-0001");
                spaceportRepository.save(earth);
            }

            // #10: Neptune (Star Trek)
            Spaceport neptune = spaceportRepository.findByCode("ST-0002");
            if (neptune == null) {
                neptune = new Spaceport()
                        .setName("Neptune")
                        .setDetail("Home to the Soon-To-Be Opened Hotel/Resort: 'The Glass Ocean'")
                        .setCode("ST-0002");
                spaceportRepository.save(neptune);
            }

            // #11: Mars (Star Trek)
            Spaceport mars = spaceportRepository.findByCode("ST-0003");
            if (mars == null) {
                mars = new Spaceport()
                        .setName("Mars")
                        .setDetail("The 2nd Largest Planet in Human Population and Starfleet's Base For Long Range Missions")
                        .setCode("ST-0003");
                spaceportRepository.save(mars);
            }

            // #12: The Moon (Star Trek)
            Spaceport moon = spaceportRepository.findByCode("ST-0004");
            if (moon == null) {
                moon = new Spaceport()
                        .setName("Moon")
                        .setDetail("Humanity's First Interstellar Journey and Home to the Armstrong, Aldrin, and Collins Museum")
                        .setCode("ST-0004");
                spaceportRepository.save(moon);
            }

            // #13: Risa (Star Trek)
            Spaceport risa = spaceportRepository.findByCode("ST-0005");
            if (risa == null) {
                risa = new Spaceport()
                        .setName("Risa")
                        .setDetail("The Infamous Beach and Ocean Home to the Risians")
                        .setCode("ST-0005");
                spaceportRepository.save(risa);
            }

            // #14: Cybertron (Transformers)
            Spaceport cybertron = spaceportRepository.findByCode("TR-0001");
            if (cybertron == null) {
                cybertron = new Spaceport()
                        .setName("Cybertron")
                        .setDetail("The Home to AutoBots, Decepticons, and Legendary Hero: Optimus Prime")
                        .setCode("TR-0001");
                spaceportRepository.save(cybertron);
            }

            // #15: Pandora (Avatar)
            Spaceport pandora = spaceportRepository.findByCode("AV-0001");
            if (pandora == null) {
                pandora = new Spaceport()
                        .setName("Pandora")
                        .setDetail("The Home to the Na'vi People and The Tree of Souls")
                        .setCode("AV-0001");
                spaceportRepository.save(pandora);
            }

            // #16: The Reach (Halo)
            Spaceport theReach = spaceportRepository.findByCode("HA-0001");
            if (theReach == null) {
                theReach = new Spaceport()
                        .setName("The Reach")
                        .setDetail("The Previous Home of The Forerunners and UNSC Powerhouse Base")
                        .setCode("HA-0001");
                spaceportRepository.save(theReach);
            }

            // #17: The Halo Array (Halo)
            Spaceport haloArray = spaceportRepository.findByCode("HA-0002");
            if (haloArray == null) {
                haloArray = new Spaceport()
                        .setName("The Halo Array")
                        .setDetail("A Network of (7) Ring-Shaped Artificial Worlds Created by The Forerunners")
                        .setCode("HA-0002");
                spaceportRepository.save(haloArray);
            }

            // #18: Caladan (Dune)
            Spaceport caladan = spaceportRepository.findByCode("DU-0001");
            if (caladan == null) {
                caladan = new Spaceport()
                        .setName("Caladan")
                        .setDetail("The Lush Oceanic Home World of House Atreides")
                        .setCode("DU-0001");
                spaceportRepository.save(caladan);
            }

            // #19: Arrakis (Dune)
            Spaceport arrakis = spaceportRepository.findByCode("DU-0002");
            if (arrakis == null) {
                arrakis = new Spaceport()
                        .setName("Arrakis")
                        .setDetail("A Harsh Desert Planet, Known for Being the Universe's Single Source for Spice Melange")
                        .setCode("DU-0002");
                spaceportRepository.save(arrakis);
            }

            // #20: LV223 (Alien/Prometheus)
            Spaceport lv223 = spaceportRepository.findByCode("AP-0001");
            if (lv223 == null) {
                lv223 = new Spaceport()
                        .setName("LV-223")
                        .setDetail("An Abandoned Military-Base Planet belonging to the Engineers")
                        .setCode("AP-0001");
                spaceportRepository.save(lv223);
            }

            // <======= CREATE (8) SPACECRAFTS + ADD TO APPROPRIATE PILOT USER'S FLEET =======>

            // #1: Rei Skywalker - (2) Spacecraft
            Spacecraft iShuttle = spacecraftRepository.findByCode("SC-0001");
            if (iShuttle == null) {
                iShuttle = new Spacecraft()
                        .setCode("SC-0001")
                        .setPilot(rSkywalker)
                        .setCapacity(10)
                        .setMake("Lambda-Class Imperial Shuttle");
                spacecraftRepository.save(iShuttle);
            }
            Spacecraft rCruiser = spacecraftRepository.findByCode("SC-0002");
            if (rCruiser == null) {
                rCruiser = new Spacecraft()
                        .setCode("SC-0002")
                        .setPilot(rSkywalker)
                        .setCapacity(150)
                        .setMake("Nebulon-B Frigate");
                spacecraftRepository.save(rCruiser);
            }
            if (rSkywalker.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                rSkywalker.setSpacecrafts(spacecrafts);
                rSkywalker.getSpacecrafts().add(iShuttle);
                rSkywalker.getSpacecrafts().add(rCruiser);
                pilotRepository.save(rSkywalker);
            }

            // #2: Han Solo - (1) Spacecraft
            Spacecraft mFalcon = spacecraftRepository.findByCode("SC-0003");
            if (mFalcon == null) {
                mFalcon = new Spacecraft()
                        .setCode("SC-0003")
                        .setPilot(hSolo)
                        .setCapacity(60)
                        .setMake("Millennium Falcon");
                spacecraftRepository.save(mFalcon);
            }
            if (hSolo.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                hSolo.setSpacecrafts(spacecrafts);
                hSolo.getSpacecrafts().add(mFalcon);
                pilotRepository.save(hSolo);
            }

            // #3: Padme Amidala - (1) Spacecraft
            Spacecraft nCrusier = spacecraftRepository.findByCode("SC-0004");
            if (nCrusier == null) {
                nCrusier = new Spacecraft()
                        .setCode("SC-0004")
                        .setPilot(pAmidala)
                        .setCapacity(20)
                        .setMake("Naboo Royal Starship");
                spacecraftRepository.save(nCrusier);
            }
            if (pAmidala.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                pAmidala.setSpacecrafts(spacecrafts);
                pAmidala.getSpacecrafts().add(nCrusier);
                pilotRepository.save(pAmidala);
            }

            // #4: Master Chief - (2) Spacecraft
            Spacecraft hGunship = spacecraftRepository.findByCode("SC-0005");
            if (hGunship == null) {
                hGunship = new Spacecraft()
                        .setCode("SC-0005")
                        .setPilot(mChief)
                        .setCapacity(10)
                        .setMake("D77-TC Pelican");
                spacecraftRepository.save(hGunship);
            }
            Spacecraft hCruiser = spacecraftRepository.findByCode("SC-0006");
            if (hCruiser == null) {
                hCruiser = new Spacecraft()
                        .setCode("SC-0006")
                        .setPilot(mChief)
                        .setCapacity(100)
                        .setMake("UNSC Herodotus");
                spacecraftRepository.save(hCruiser);
            }
            if (mChief.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                mChief.setSpacecrafts(spacecrafts);
                mChief.getSpacecrafts().add(hGunship);
                mChief.getSpacecrafts().add(hCruiser);
                pilotRepository.save(mChief);
            }

            // #5: Elizabeth Shaw - (1) Spacecraft
            Spacecraft prometheus = spacecraftRepository.findByCode("SC-0007");
            if (prometheus == null) {
                prometheus = new Spacecraft()
                        .setCode("SC-0007")
                        .setPilot(eShaw)
                        .setCapacity(400)
                        .setMake("USCSS Prometheus");
                spacecraftRepository.save(prometheus);
            }
            if (eShaw.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                eShaw.setSpacecrafts(spacecrafts);
                eShaw.getSpacecrafts().add(prometheus);
                pilotRepository.save(eShaw);
            }

            // #5: Capt. Jame T. Kirk - (1) Spacecraft
            Spacecraft ussEnterprise = spacecraftRepository.findByCode("SC-0008");
            if (ussEnterprise == null) {
                ussEnterprise = new Spacecraft()
                        .setCode("SC-0008")
                        .setPilot(jKirk)
                        .setCapacity(300)
                        .setMake("U.S.S. Enterprise");
                spacecraftRepository.save(ussEnterprise);
            }
            if (jKirk.getSpacecrafts() == null) {
                Set<Spacecraft> spacecrafts = new HashSet<>();
                jKirk.setSpacecrafts(spacecrafts);
                jKirk.getSpacecrafts().add(ussEnterprise);
                pilotRepository.save(jKirk);
            }

            // <======= CREATE (10) FLIGHTS =======>

            // #1: Coruscant to Naboo - P.Amidala (Naboo Royal Starship)
            Flight flightA = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(coruscant, naboo, nCrusier);
            if (flightA == null) {
                flightA = new Flight()
                        .setOriginSpaceport(coruscant)
                        .setDestinationSpaceport(naboo)
                        .setSpacecraft(nCrusier)
                        .setPilot(pAmidala)
                        .setFare(25000)
                        .setFlightDuration(20);
                flightRepository.save(flightA);
            }

            // #2: Kashyyyk to Tatooine - H.Solo (Millennium Falcon)
            Flight flightB = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(kashyyyk, tatooine, mFalcon);
            if (flightB == null) {
                flightB = new Flight()
                        .setOriginSpaceport(kashyyyk)
                        .setDestinationSpaceport(tatooine)
                        .setSpacecraft(mFalcon)
                        .setPilot(hSolo)
                        .setFare(35000)
                        .setFlightDuration(30);
                flightRepository.save(flightB);
            }

            // #3: Earth to Neptune - M.Chief (UNSC Herodotus)
            Flight flightC = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(earth, neptune, hCruiser);
            if (flightC == null) {
                flightC = new Flight()
                        .setOriginSpaceport(earth)
                        .setDestinationSpaceport(neptune)
                        .setSpacecraft(hCruiser)
                        .setPilot(mChief)
                        .setFare(15000)
                        .setFlightDuration(10);
                flightRepository.save(flightC);
            }

            // #4: Mars to Earth - J.Kirk (USS Enterprise)
            Flight flightD = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(mars, earth, ussEnterprise);
            if (flightD == null) {
                flightD = new Flight()
                        .setOriginSpaceport(mars)
                        .setDestinationSpaceport(earth)
                        .setSpacecraft(ussEnterprise)
                        .setPilot(jKirk)
                        .setFare(5000)
                        .setFlightDuration(5);
                flightRepository.save(flightD);
            }

            // #5: Cybertron to The Moon - R.Skywalker (Nebulon-B Frigate)
            Flight flightE = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(cybertron, moon, rCruiser);
            if (flightE == null) {
                flightE = new Flight()
                        .setOriginSpaceport(cybertron)
                        .setDestinationSpaceport(moon)
                        .setSpacecraft(rCruiser)
                        .setPilot(rSkywalker)
                        .setFare(100000)
                        .setFlightDuration(50);
                flightRepository.save(flightE);
            }

            // #6: Caladan to Arrakis - P.Amidala (Naboo Royal Starship)
            Flight flightF = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(caladan, arrakis, nCrusier);
            if (flightF == null) {
                flightF = new Flight()
                        .setOriginSpaceport(caladan)
                        .setDestinationSpaceport(arrakis)
                        .setSpacecraft(nCrusier)
                        .setPilot(pAmidala)
                        .setFare(75000)
                        .setFlightDuration(40);
                flightRepository.save(flightF);
            }

            // #7: Risa to Earth - J.Kirk (USS Enterprise)
            Flight flightG = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(risa, earth, ussEnterprise);
            if (flightG == null) {
                flightG = new Flight()
                        .setOriginSpaceport(risa)
                        .setDestinationSpaceport(earth)
                        .setSpacecraft(ussEnterprise)
                        .setPilot(jKirk)
                        .setFare(100000)
                        .setFlightDuration(50);
                flightRepository.save(flightG);
            }

            // #8: Mars to Pandora - R.Skywalker (Lambda-Class Imperial Shuttle)
            Flight flightH = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(mars, pandora, iShuttle);
            if (flightH == null) {
                flightH = new Flight()
                        .setOriginSpaceport(mars)
                        .setDestinationSpaceport(pandora)
                        .setSpacecraft(iShuttle)
                        .setPilot(rSkywalker)
                        .setFare(150000)
                        .setFlightDuration(60);
                flightRepository.save(flightH);
            }

            // #9: The Reach to The Halo Array - M.Chief (D77-TC Pelican)
            Flight flightI = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(theReach, haloArray, hGunship);
            if (flightI == null) {
                flightI = new Flight()
                        .setOriginSpaceport(theReach)
                        .setDestinationSpaceport(haloArray)
                        .setSpacecraft(hGunship)
                        .setPilot(mChief)
                        .setFare(125000)
                        .setFlightDuration(55);
                flightRepository.save(flightI);
            }

            // #10: Earth to LV-223 - E.Shaw (USCSS Prometheus)
            Flight flightJ = flightRepository.findByOriginSpaceportAndDestinationSpaceportAndSpacecraft(earth, lv223, prometheus);
            if (flightJ == null) {
                flightJ = new Flight()
                        .setOriginSpaceport(earth)
                        .setDestinationSpaceport(lv223)
                        .setSpacecraft(prometheus)
                        .setPilot(eShaw)
                        .setFare(250000)
                        .setFlightDuration(100);
                flightRepository.save(flightJ);
            }

            // <======= CREATE (10) FLIGHT-PLANS =======>

            // #1: Flight-A - Departing: Today's Current Date
            FlightPlan flightPlanA = flightPlanRepository.findByFlightDetailAndFlightDate(flightA, DateUtils.todayStr());
            if (flightPlanA == null) {
                flightPlanA = new FlightPlan()
                        .setFlightDetail(flightA)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightA.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanA);
            }

            // #2: Flight-B - Departing: Today's Current Date
            FlightPlan flightPlanB = flightPlanRepository.findByFlightDetailAndFlightDate(flightB, DateUtils.todayStr());
            if (flightPlanB == null) {
                flightPlanB = new FlightPlan()
                        .setFlightDetail(flightB)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightB.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanB);
            }

            // #3: Flight-C - Departing: Today's Current Date
            FlightPlan flightPlanC = flightPlanRepository.findByFlightDetailAndFlightDate(flightC, DateUtils.todayStr());
            if (flightPlanC == null) {
                flightPlanC = new FlightPlan()
                        .setFlightDetail(flightC)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightC.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanC);
            }

            // #4: Flight-D - Departing: Today's Current Date
            FlightPlan flightPlanD = flightPlanRepository.findByFlightDetailAndFlightDate(flightD, DateUtils.todayStr());
            if (flightPlanD == null) {
                flightPlanD = new FlightPlan()
                        .setFlightDetail(flightD)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightD.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanD);
            }

            // #5: Flight-E - Departing: Today's Current Date
            FlightPlan flightPlanE = flightPlanRepository.findByFlightDetailAndFlightDate(flightE, DateUtils.todayStr());
            if (flightPlanE == null) {
                flightPlanE = new FlightPlan()
                        .setFlightDetail(flightE)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightE.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanE);
            }

            // #6: Flight-F - Departing: Today's Current Date
            FlightPlan flightPlanF = flightPlanRepository.findByFlightDetailAndFlightDate(flightF, DateUtils.todayStr());
            if (flightPlanF == null) {
                flightPlanF = new FlightPlan()
                        .setFlightDetail(flightF)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightF.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanF);
            }

            // #7: Flight-G - Departing: Today's Current Date
            FlightPlan flightPlanG = flightPlanRepository.findByFlightDetailAndFlightDate(flightG, DateUtils.todayStr());
            if (flightPlanG == null) {
                flightPlanG = new FlightPlan()
                        .setFlightDetail(flightG)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightG.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanG);
            }

            // #8: Flight-H - Departing: Today's Current Date
            FlightPlan flightPlanH = flightPlanRepository.findByFlightDetailAndFlightDate(flightH, DateUtils.todayStr());
            if (flightPlanH == null) {
                flightPlanH = new FlightPlan()
                        .setFlightDetail(flightH)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightH.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanH);
            }

            // #9: Flight-I - Departing: Today's Current Date
            FlightPlan flightPlanI = flightPlanRepository.findByFlightDetailAndFlightDate(flightI, DateUtils.todayStr());
            if (flightPlanI == null) {
                flightPlanI = new FlightPlan()
                        .setFlightDetail(flightI)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightI.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanI);
            }

            // #10: Flight-J - Departing: Today's Current Date
            FlightPlan flightPlanJ = flightPlanRepository.findByFlightDetailAndFlightDate(flightJ, DateUtils.todayStr());
            if (flightPlanJ == null) {
                flightPlanJ = new FlightPlan()
                        .setFlightDetail(flightJ)
                        .setFlightDate(DateUtils.todayStr())
                        .setAvailableSeats(flightJ.getSpacecraft().getCapacity());
                flightPlanRepository.save(flightPlanJ);
            }

            // <======= CREATE (5) TICKETS =======>

            // #1: Master Yoda - FlightPlan-B - Today's Current Date
            if(flightPlanB.getAvailableSeats() > 0) {
                Ticket ticket = new Ticket()
                        .setCancellable(false)
                        .setFlightDate(flightPlanB.getFlightDate())
                        .setPassenger(yoda)
                        .setFlightPlan(flightPlanB)
                        .setSeatNumber((flightPlanB.getFlightDetail().getSpacecraft().getCapacity() - flightPlanB.getAvailableSeats()) + 1);
                ticketRepository.save(ticket);
                flightPlanB.setAvailableSeats(flightPlanB.getAvailableSeats() - 1);
                flightPlanRepository.save(flightPlanB);
            }

            // #2: Leia Organa - FlightPlan-A - Today's Current Date
            if(flightPlanA.getAvailableSeats() > 0) {
                Ticket ticket = new Ticket()
                        .setCancellable(false)
                        .setFlightDate(flightPlanA.getFlightDate())
                        .setPassenger(lOrgana)
                        .setFlightPlan(flightPlanA)
                        .setSeatNumber((flightPlanA.getFlightDetail().getSpacecraft().getCapacity() - flightPlanA.getAvailableSeats()) + 1);
                ticketRepository.save(ticket);
                flightPlanA.setAvailableSeats(flightPlanA.getAvailableSeats() - 1);
                flightPlanRepository.save(flightPlanA);
            }

            // #3: Anakin Skywalker - FlightPlan-A - Today's Current Date
            if(flightPlanA.getAvailableSeats() > 0) {
                Ticket ticket = new Ticket()
                        .setCancellable(false)
                        .setFlightDate(flightPlanA.getFlightDate())
                        .setPassenger(aSkywalker)
                        .setFlightPlan(flightPlanA)
                        .setSeatNumber((flightPlanA.getFlightDetail().getSpacecraft().getCapacity() - flightPlanA.getAvailableSeats()) + 1);
                ticketRepository.save(ticket);
                flightPlanA.setAvailableSeats(flightPlanA.getAvailableSeats() - 1);
                flightPlanRepository.save(flightPlanA);
            }

            // #4: Nyota Uhura - FlightPlan-D - Today's Current Date
            if(flightPlanD.getAvailableSeats() > 0) {
                Ticket ticket = new Ticket()
                        .setCancellable(false)
                        .setFlightDate(flightPlanD.getFlightDate())
                        .setPassenger(nUhura)
                        .setFlightPlan(flightPlanD)
                        .setSeatNumber((flightPlanD.getFlightDetail().getSpacecraft().getCapacity() - flightPlanD.getAvailableSeats()) + 1);
                ticketRepository.save(ticket);
                flightPlanD.setAvailableSeats(flightPlanD.getAvailableSeats() - 1);
                flightPlanRepository.save(flightPlanD);
            }

            // #5: Nyota Uhura - FlightPlan-G - Today's Current Date
            if(flightPlanG.getAvailableSeats() > 0) {
                Ticket ticket = new Ticket()
                        .setCancellable(false)
                        .setFlightDate(flightPlanG.getFlightDate())
                        .setPassenger(nUhura)
                        .setFlightPlan(flightPlanG)
                        .setSeatNumber((flightPlanG.getFlightDetail().getSpacecraft().getCapacity() - flightPlanG.getAvailableSeats()) + 1);
                ticketRepository.save(ticket);
                flightPlanG.setAvailableSeats(flightPlanG.getAvailableSeats() - 1);
                flightPlanRepository.save(flightPlanG);
            }
        };
    }
}