package com.clouddevg.ita.dto.entity.flight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDto {

    private Long id;

    private int fare;

    private int flightDuration;

    private String originSpaceportCode;

    private String originSpaceportName;

    private String destinationSpaceportCode;

    private String destinationSpaceportName;

    private String spacecraftCode;

    private String pilotCode;
}
