package com.clouddevg.ita.repository.flight;

import com.clouddevg.ita.entity.flight.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> { }
