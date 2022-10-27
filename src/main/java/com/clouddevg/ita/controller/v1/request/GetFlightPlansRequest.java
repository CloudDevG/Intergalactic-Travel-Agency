package com.clouddevg.ita.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetFlightPlansRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String originSpaceport;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String destinationSpaceport;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate flightDate;
}
