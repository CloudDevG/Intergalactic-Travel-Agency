package com.clouddevg.ita.entity.flight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "flight_plan")
public class FlightPlan {
    @Id
    @Column(name = "flight_plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flightDetail;

    @OneToMany(mappedBy = "flightPlan", cascade = CascadeType.ALL)
    private Set<Ticket> ticketsSold;

    @Column(name = "flight_date")
    private String flightDate;

    @Column(name = "available_seats")
    private int availableSeats;
}
