package org.example.repositories;

import org.example.models.Ticket;

import java.util.Optional;

public class TicketRepository {
    public Optional<Ticket> findTicketById(Long ticketId) {
        return Optional.empty();
    }

    public Ticket save(Ticket ticket) {
        return null;
    }
}
