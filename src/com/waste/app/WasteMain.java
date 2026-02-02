package com.waste.app;

import com.waste.bean.Zone;
import com.waste.bean.Vehicle;
import com.waste.service.WasteService;

import java.sql.Date;

public class WasteMain {

    private static WasteService service = new WasteService();

    public static void main(String[] args) {
        Zone z = new Zone();
        z.setZoneID("ZN01");
        z.setZoneName("Hub West");
        z.setZoneType("Commercial");
        z.setAreaSqKm(5.2);
        z.setStatus("INACTIVE");
        service.registerZone(z);
        System.out.println("Zone Registered");
        Vehicle v = new Vehicle();
        v.setVehicleID("VH01");
        v.setVehicleType("Mini-Truck");
        v.setCapacityKg(1200);
        v.setSuitability("Commercial");
        v.setStatus("AVAILABLE");
        service.registerVehicle(v);
        System.out.println("Vehicle Registered");
        Date d = new Date(System.currentTimeMillis());
        service.schedulePickup("ZN01", "VH01", d, "07:45", 500);
        System.out.println("Pickup Scheduled");
    }
}
