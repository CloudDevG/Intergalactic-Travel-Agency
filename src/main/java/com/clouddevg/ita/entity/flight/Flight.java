package com.clouddevg.ita.entity.flight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int fare;

    @Column(name = "flight_duration")
    private int flightDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_spaceport_id")
    private Spaceport originSpaceport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_spaceport_id")
    private Spaceport destinationSpaceport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spacecraft_id")
    private Spacecraft spacecraft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id")
    private Pilot pilot;
}
