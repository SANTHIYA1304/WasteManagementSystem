package com.waste.service;

import com.waste.bean.Zone;
import com.waste.bean.Pickup;
import com.waste.bean.Vehicle;
import com.waste.dao.ZoneDAO;
import com.waste.dao.PickupDAO;
import com.waste.dao.VehicleDAO;

import java.sql.Date;

public class WasteService {

    ZoneDAO zoneDAO = new ZoneDAO();
    VehicleDAO vehicleDAO = new VehicleDAO();

    public boolean registerZone(Zone z) {
        Zone existing = zoneDAO.findZone(z.getZoneID());

        if (existing == null) {
            return zoneDAO.insertZone(z);
        } else {
            return zoneDAO.updateZone(z);
        }
    }
    public boolean registerVehicle(Vehicle v) {
        if (v.getCapacityKg() <= 0) {
            return false;
        }

        Vehicle existing = vehicleDAO.findVehicle(v.getVehicleID());

        if (existing == null) {
            return vehicleDAO.insertVehicle(v);  
        } else {
            return vehicleDAO.updateVehicle(v);   
        }
    }

    public boolean schedulePickup(String zoneID, String vehicleID,
                                  Date date, String time, int expectedVolume) {

        if (expectedVolume <= 0) {
            return false;
        }

        PickupDAO pickupDAO = new PickupDAO();
        Pickup p = new Pickup();

        p.setPickupID(pickupDAO.generatePickupID());
        p.setZoneID(zoneID);
        p.setVehicleID(vehicleID);
        p.setPickupDate(date);
        p.setStartTime(time);
        p.setExpectedVolumeKg(expectedVolume);
        p.setStatus("SCHEDULED");

        return pickupDAO.insertPickup(p);
    }
    public boolean completePickup(int pickupID, int actualVolume) {

        if (actualVolume <= 0) {
            return false;
        }

        PickupDAO pickupDAO = new PickupDAO();
        return pickupDAO.updateCompletion(pickupID, actualVolume);
    }
}
