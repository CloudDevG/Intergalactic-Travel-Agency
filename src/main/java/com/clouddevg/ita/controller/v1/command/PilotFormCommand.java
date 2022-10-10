package com.clouddevg.ita.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class PilotFormCommand {

    @NotBlank
    @Size(min = 5, max = 100)
    private String pilotName;

    @NotBlank
    @Size(max = 100)
    private String pilotDetails;
}
