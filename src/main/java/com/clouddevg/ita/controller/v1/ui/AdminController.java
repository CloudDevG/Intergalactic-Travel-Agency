package com.clouddevg.ita.controller.v1.ui;

import com.clouddevg.ita.controller.v1.command.AdminSignupFormCommand;
import com.clouddevg.ita.dto.entity.flight.PilotDto;
import com.clouddevg.ita.dto.entity.user.UserDto;
import com.clouddevg.ita.service.FlightReservationService;
import com.clouddevg.ita.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController {

    final FlightReservationService flightReservationService;

    private final UserService userService;

    public AdminController(FlightReservationService flightReservationService, UserService userService) {
        this.flightReservationService = flightReservationService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping(value = {"/logout"})
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:login";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "redirect:dashboard";
    }

    @GetMapping(value = "/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("adminSignupFormData", new AdminSignupFormCommand());
        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createNewAdmin(@Valid @ModelAttribute("adminSignupFormData") AdminSignupFormCommand adminSignupFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("signup");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            try {
                UserDto newUser = registerAdmin(adminSignupFormCommand);
            } catch (Exception exception) {
                bindingResult.rejectValue("email", "error.adminSignupFormCommand", exception.getMessage());
                return modelAndView;
            }
        }
        return new ModelAndView("login");
    }

    /**
     * Register a new user in the database
     *
     * @param adminSignupRequest
     * @return
     */
    private UserDto registerAdmin(@Valid AdminSignupFormCommand adminSignupRequest) {
        UserDto userDto = new UserDto()
                .setEmail(adminSignupRequest.getEmail())
                .setPassword(adminSignupRequest.getPassword())
                .setFirstName(adminSignupRequest.getFirstName())
                .setLastName(adminSignupRequest.getLastName())
                .setMobileNumber(adminSignupRequest.getMobileNumber())
                .setAdmin(true);
        UserDto admin = userService.signup(userDto); //register the admin
        PilotDto pilotDto = new PilotDto()
                .setName(adminSignupRequest.getPilotName())
                .setDetails(adminSignupRequest.getPilotDetails())
                .setOwner(admin);
        flightReservationService.addPilot(pilotDto); //add the agency for this admin
        return admin;
    }
}
