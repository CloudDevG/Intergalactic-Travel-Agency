package com.clouddevg.ita.dto.mapper;

import com.clouddevg.ita.dto.entity.flight.TicketDto;
import com.clouddevg.ita.entity.flight.Ticket;

public class TicketMapper {
    public static TicketDto toTicketDto(Ticket ticket) {
        return new TicketDto()
                .setId(ticket.getId())
                .setSpacecraftCode(ticket.getFlightPlan().getFlightDetail().getSpacecraft().getCode())
                .setSeatNumber(ticket.getSeatNumber())
                .setOriginSpaceport(ticket.getFlightPlan().getFlightDetail().getOriginSpaceport().getName())
                .setDestinationSpaceport(ticket.getFlightPlan().getFlightDetail().getDestinationSpaceport().getName())
                .setCancellable(false)
                .setFlightDate(ticket.getFlightDate())
                .setPassengerName(ticket.getPassenger().getFullName())
                .setPassengerMobileNumber(ticket.getPassenger().getMobileNumber());
    }
}
