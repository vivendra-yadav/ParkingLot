package org.example.factories;

import org.example.models.ParkingSpotAssignmentStrategyType;
import org.example.strategies.NearestParkingSpotAssignment;
import org.example.strategies.ParkingSpotAssignmentStrategy;
import org.example.strategies.RandomSpotAssignmentStrategy;

import java.util.Random;

public class ParkingSpotAssignmentStrategyFactory {
    public static ParkingSpotAssignmentStrategy getParkingSpotAssignmentStrategy(
            ParkingSpotAssignmentStrategyType strategyType
    ) {
        if (strategyType.equals(ParkingSpotAssignmentStrategyType.NEAREST)) {
            return new NearestParkingSpotAssignment();
        }

        return new RandomSpotAssignmentStrategy();
    }
}
