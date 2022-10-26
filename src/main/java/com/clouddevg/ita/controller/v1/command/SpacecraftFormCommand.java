package com.clouddevg.ita.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class SpacecraftFormCommand {
    @NotBlank
    @Size(min = 4, max = 8)
    private String code;

    @NotBlank
    private String make;

    @Min(value = 2, message = "Cannot enroll a spacecraft with capacity smaller than 2")
    private int capacity;
}
