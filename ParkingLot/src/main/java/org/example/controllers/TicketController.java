package org.example.controllers;

import org.example.dtos.IssueTicketRequestDto;
import org.example.dtos.IssueTicketResponseDto;
import org.example.dtos.ResponseStatus;
import org.example.models.Ticket;
import org.example.services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public IssueTicketResponseDto issueTicket(IssueTicketRequestDto requestDto) {
        IssueTicketResponseDto responseDto = new IssueTicketResponseDto();

        try {
            Ticket ticket = ticketService.issueTicket(
                    requestDto.getGateId(),
                    requestDto.getVehicleNumber(),
                    requestDto.getOwnerName(),
                    requestDto.getVehicleType()
            );

            responseDto.setTicket(ticket);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

//    public CancelTicketResponseDto cancelTicket(CancelTicketRequestDto requestDto) {
//        return null;
//    }
}
