package com.waste.util;

public class ActivePickupExistsException extends Exception {
    public String toString() {
        return "Active pickup exists. Cannot remove.";
    }
}
