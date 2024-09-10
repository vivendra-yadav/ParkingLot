package org.example.services;

import org.example.controllers.TicketController;
import org.example.dtos.IssueTicketRequestDto;
import org.example.exceptions.GateNotFoundException;
import org.example.factories.ParkingSpotAssignmentStrategyFactory;
import org.example.models.*;
import org.example.repositories.GateRepository;
import org.example.repositories.ParkingLotRepository;
import org.example.repositories.TicketRepository;
import org.example.repositories.VehicleRepository;
import org.example.strategies.ParkingSpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;

public class TicketService { // Chef
    private GateRepository gateRepository;
    private ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;
    private TicketRepository ticketRepository;

    public TicketService(GateRepository gateRepository,
                         ParkingLotRepository parkingLotRepository,
                         VehicleRepository vehicleRepository,
                         TicketRepository ticketRepository) {
        this.gateRepository = gateRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
    }


    public Ticket issueTicket(Long gateId,
                              String vehicleNumber,
                              String vehicleOwnerName,
                              VehicleType vehicleType
                       ) throws GateNotFoundException {

        /*
        1. get the Gate object from gateId.
        2. Check if the vehicle with the given number is already present in the DB,
        3. If yes, proceed. If not, save the vehicle in the DB.
        4. Assign the Parking Spot.
        5. Create the ticket object.
         */

        Optional<Gate> optionalGate = gateRepository.findGateById(gateId);

        if (optionalGate.isEmpty()) {
            //throw exception
            throw new GateNotFoundException("Gate with id: " + gateId + " doesn't exist");
        }

        Gate gate = optionalGate.get();

        Ticket ticket = new Ticket();
        ticket.setGate(gate);
        ticket.setEntryTime(new Date());

        //Get the Vehicle object.
        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleByVehicleNumber(vehicleNumber);
        Vehicle savedVehicle = null;

        if (optionalVehicle.isEmpty()) {
            //Create and save Vehicle to DB.
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicle.setOwnerName(vehicleOwnerName);

            savedVehicle = vehicleRepository.save(vehicle);
        } else {
            savedVehicle = optionalVehicle.get();
        }
        ticket.setVehicle(savedVehicle);

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findParkingLotByGateId(gateId);

        if (optionalParkingLot.isEmpty()) {
            throw new RuntimeException("Invalid ParkingLot");
        }

        ParkingLot parkingLot = optionalParkingLot.get();

        ParkingSpotAssignmentStrategyType strategyType =
                parkingLot.getParkingSpotAssignmentStrategyType();

        ParkingSpotAssignmentStrategy assignmentStrategy =
                ParkingSpotAssignmentStrategyFactory.getParkingSpotAssignmentStrategy(strategyType);

        ParkingSpot parkingSpot = assignmentStrategy.assignParkingSpot(gate, savedVehicle);
        ticket.setParkingSpot(parkingSpot);

        return ticketRepository.save(ticket);
    }
}

//Clean Code.
