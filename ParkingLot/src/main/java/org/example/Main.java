package org.example;

import org.example.controllers.TicketController;
import org.example.dtos.IssueTicketRequestDto;
import org.example.dtos.IssueTicketResponseDto;
import org.example.models.VehicleType;
import org.example.repositories.GateRepository;
import org.example.repositories.ParkingLotRepository;
import org.example.repositories.TicketRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.TicketService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        GateRepository gateRepository = new GateRepository();
        TicketRepository ticketRepository = new TicketRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();

        TicketService ticketService = new TicketService(
                gateRepository,
                parkingLotRepository,
                vehicleRepository,
                ticketRepository
        );

        TicketController ticketController = new TicketController(ticketService);

        IssueTicketRequestDto requestDto = new IssueTicketRequestDto();
        requestDto.setGateId(123);
        requestDto.setOwnerName("Deepak");
        requestDto.setVehicleNumber("HR26X7890");
        requestDto.setVehicleType(VehicleType.SEDAN);

        IssueTicketResponseDto responseDto = ticketController.issueTicket(requestDto);

    }
}