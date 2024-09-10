package org.example.repositories;

import org.example.models.Vehicle;

import java.util.Optional;

public class VehicleRepository {
    public Optional<Vehicle> findVehicleByVehicleNumber(String vehicleNumber) {
        return Optional.empty();
    }

    public Vehicle save(Vehicle vehicle) { // update + insert.
        //save
        return null;
    }
}
