package com.clouddevg.ita.controller.v1.ui;

import com.clouddevg.ita.controller.v1.command.*;
import com.clouddevg.ita.dto.entity.flight.FlightDto;
import com.clouddevg.ita.dto.entity.flight.PilotDto;
import com.clouddevg.ita.dto.entity.flight.SpacecraftDto;
import com.clouddevg.ita.dto.entity.flight.SpaceportDto;
import com.clouddevg.ita.dto.entity.user.UserDto;
import com.clouddevg.ita.service.FlightReservationService;
import com.clouddevg.ita.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class DashboardController {

    private final UserService userService;

    private final FlightReservationService flightReservationService;

    public DashboardController(UserService userService, FlightReservationService flightReservationService) {
        this.userService = userService;
        this.flightReservationService = flightReservationService;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @GetMapping(value = "/pilot")
    public ModelAndView pilotDetails() {
        ModelAndView modelAndView = new ModelAndView("pilot");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PilotDto pilotDto = flightReservationService.getPilot(userDto);
        PilotFormCommand pilotFormCommand = new PilotFormCommand()
                .setPilotName(pilotDto.getName())
                .setPilotDetails(pilotDto.getDetails());
        modelAndView.addObject("pilotFormData", pilotFormCommand);
        modelAndView.addObject("pilot", pilotDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/pilot")
    public ModelAndView updatePilot(@Valid @ModelAttribute("pilotFormData") PilotFormCommand pilotFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("pilot");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PilotDto pilotDto = flightReservationService.getPilot(userDto);
        modelAndView.addObject("pilot", pilotDto);
        modelAndView.addObject("userName", userDto.getFullName());
        if (!bindingResult.hasErrors()) {
            if (pilotDto != null) {
                pilotDto.setName(pilotFormCommand.getPilotName())
                        .setDetails(pilotFormCommand.getPilotDetails());
                flightReservationService.updatePilot(pilotDto, null);
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/spacecraft")
    public ModelAndView spacecraftDetails() {
        ModelAndView modelAndView = new ModelAndView("spacecraft");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PilotDto pilotDto = flightReservationService.getPilot(userDto);
        modelAndView.addObject("pilot", pilotDto);
        modelAndView.addObject("spacecraftFormData", new SpacecraftFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/spacecraft")
    public ModelAndView addNewSpacecraft(@Valid @ModelAttribute("spacecraftFormData") SpacecraftFormCommand spacecraftFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("spacecraft");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PilotDto pilotDto = flightReservationService.getPilot(userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("pilot", pilotDto);
        if (!bindingResult.hasErrors()) {
            try {
                SpacecraftDto spacecraftDto = new SpacecraftDto()
                        .setCode(spacecraftFormCommand.getCode())
                        .setCapacity(spacecraftFormCommand.getCapacity())
                        .setMake(spacecraftFormCommand.getMake());
                PilotDto updatedPilotDto = flightReservationService.updatePilot(pilotDto, spacecraftDto);
                modelAndView.addObject("pilot", updatedPilotDto);
                modelAndView.addObject("spacecraftFormData", new SpacecraftFormCommand());
            } catch (Exception ex) {
                bindingResult.rejectValue("code", "error.spacecraftFormCommand", ex.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/flight")
    public ModelAndView flightDetails() {
        ModelAndView modelAndView = new ModelAndView("flight");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PilotDto pilotDto = flightReservationService.getPilot(userDto);
        Set<SpaceportDto> spaceports = flightReservationService.getAllSpaceports();
        List<FlightDto> flights = flightReservationService.getPilotFlights(pilotDto.getCode());
        modelAndView.addObject("pilot", pilotDto);
        modelAndView.addObject("spaceports", spaceports);
        modelAndView.addObject("flights", flights);
        modelAndView.addObject("flightFormData", new FlightFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

//    @PostMapping(value = "/flight")
//    public ModelAndView addNewFlight(@Valid @ModelAttribute("flightFormData") FlightFormCommand flightFormCommand, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView("flight");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto userDto = userService.findUserByEmail(auth.getName());
//        PilotDto pilotDto = flightReservationService.getPilot(userDto);
//        Set<SpaceportDto> spaceports = flightReservationService.getAllSpaceports();
//        List<FlightDto> flights = flightReservationService.getPilotFlights(pilotDto.getCode());
//
//        modelAndView.addObject("spaceports", spaceports);
//        modelAndView.addObject("pilot", pilotDto);
//        modelAndView.addObject("userName", userDto.getFullName());
//        modelAndView.addObject("flights", flights);
//
//        if (!bindingResult.hasErrors()) {
//            try {
//                FlightDto flightDto = new FlightDto()
//                        .setOriginSpaceportCode(flightFormCommand.getOriginSpaceport())
//                        .setDestinationSpaceportCode(flightFormCommand.getDestinationSpaceport())
//                        .setSpacecraftCode(flightFormCommand.getSpacecraftCode())
//                        .setFlightDuration(flightFormCommand.getFlightDuration())
//                        .setFare(flightFormCommand.getFlightFare())
//                        .setPilotCode(pilotDto.getCode());
//                flightReservationService.addFlight(flightDto);
//
//                flights = flightReservationService.getPilotFlights(pilotDto.getCode());
//                modelAndView.addObject("flights", flights);
//                modelAndView.addObject("flightFormData", new FlightFormCommand());
//            } catch (Exception ex) {
//                bindingResult.rejectValue("sourceSpaceport", "error.flightFormData", ex.getMessage());
//            }
//        }
//        return modelAndView;
//    }

    @PostMapping(value = "/flight")
    public ModelAndView addNewFlight(@Valid @ModelAttribute("flightFormData") FlightFormCommand flightFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("flight");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PilotDto pilotDto = flightReservationService.getPilot(userDto);
        Set<SpaceportDto> spaceports = flightReservationService.getAllSpaceports();
        List<FlightDto> flights = flightReservationService.getPilotFlights(pilotDto.getCode());

        modelAndView.addObject("spaceports", spaceports);
        modelAndView.addObject("pilot", pilotDto);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("flights", flights);

        System.out.println("<========= Boolean RoundTrip: " + flightFormCommand.getRoundTrip() + " =========>");

        if (!bindingResult.hasErrors()) {
            try {
                FlightDto flightDto = new FlightDto()
                        .setOriginSpaceportCode(flightFormCommand.getOriginSpaceport())
                        .setDestinationSpaceportCode(flightFormCommand.getDestinationSpaceport())
                        .setSpacecraftCode(flightFormCommand.getSpacecraftCode())
                        .setFlightDuration(flightFormCommand.getFlightDuration())
                        .setFare(flightFormCommand.getFlightFare())
                        .setPilotCode(pilotDto.getCode());
                if (flightFormCommand.getRoundTrip()) {
                    flightReservationService.addRoundTripFlight(flightDto);
                } else {
                    flightReservationService.addFlight(flightDto);
                }
                flights = flightReservationService.getPilotFlights(pilotDto.getCode());
                modelAndView.addObject("flights", flights);
                modelAndView.addObject("flightFormData", new FlightFormCommand());
            } catch (Exception ex) {
                bindingResult.rejectValue("originSpaceport", "error.flightFormData", ex.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/profile")
    public ModelAndView getUserProfile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setMobileNumber(userDto.getMobileNumber());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("profileForm", profileFormCommand);
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/profile")
    public ModelAndView updateProfile(@Valid @ModelAttribute("profileForm") ProfileFormCommand profileFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
        if (!bindingResult.hasErrors()) {
            userDto.setFirstName(profileFormCommand.getFirstName())
                    .setLastName(profileFormCommand.getLastName())
                    .setMobileNumber(profileFormCommand.getMobileNumber());
            userService.updateProfile(userDto);
            modelAndView.addObject("userName", userDto.getFullName());
        }
        return modelAndView;
    }

    @PostMapping(value = "/password")
    public ModelAndView changePassword(@Valid @ModelAttribute("passwordForm") PasswordFormCommand passwordFormCommand, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("profile");
            ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            modelAndView.addObject("profileForm", profileFormCommand);
            modelAndView.addObject("userName", userDto.getFullName());
            return modelAndView;
        } else {
            userService.changePassword(userDto, passwordFormCommand.getPassword());
            return new ModelAndView("login");
        }
    }



}
