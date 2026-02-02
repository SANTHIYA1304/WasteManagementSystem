package com.waste.util;

public class VehicleNotSuitableException extends Exception {
    public String toString() {
        return "Vehicle not suitable for this zone type";
    }
}
