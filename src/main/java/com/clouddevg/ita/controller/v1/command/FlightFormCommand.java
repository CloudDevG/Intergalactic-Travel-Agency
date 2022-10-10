package com.clouddevg.ita.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Accessors(chain = true)
public class FlightFormCommand {
    @NotBlank
    private String originSpaceport;

    @NotBlank
    private String destinationSpaceport;

    @NotBlank
    private String spacecraftCode;

    @Positive
    private int flightDuration;

    @Positive
    private int flightFare;
}
