package com.waste.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.waste.bean.Pickup;
import com.waste.util.DBUtil;

public class PickupDAO {
    public int generatePickupID() {
        int pickupID = 0;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT PICKUP_SEQ_NEW.NEXTVAL FROM dual";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pickupID = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pickupID;
    }

    public boolean insertPickup(Pickup p) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "INSERT INTO PICKUP_TBL_NEW VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getPickupID());
            ps.setString(2, p.getZoneID());
            ps.setString(3, p.getVehicleID());
            ps.setDate(4, p.getPickupDate());
            ps.setString(5, p.getStartTime());
            ps.setInt(6, p.getExpectedVolumeKg());
            if(p.getActualVolumeKg() != null)
                ps.setInt(7, p.getActualVolumeKg());
            else
                ps.setNull(7, java.sql.Types.INTEGER);
            ps.setString(8, p.getStatus());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateCompletion(int pickupID, int actualVol) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "UPDATE PICKUP_TBL_NEW SET ACTUAL_VOLUME_KG=?, STATUS='COMPLETED' WHERE PICKUP_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, actualVol);
            ps.setInt(2, pickupID);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Pickup> findActiveByZone(String zoneID) {
        List<Pickup> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT * FROM PICKUP_TBL_NEW WHERE ZONE_ID=? AND STATUS='SCHEDULED'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, zoneID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Pickup p = new Pickup();
                p.setPickupID(rs.getInt("PICKUP_ID"));
                p.setZoneID(rs.getString("ZONE_ID"));
                p.setVehicleID(rs.getString("VEHICLE_ID"));
                p.setPickupDate(rs.getDate("PICKUP_DATE"));
                p.setStartTime(rs.getString("START_TIME"));
                p.setExpectedVolumeKg(rs.getInt("EXPECTED_VOLUME_KG"));
                p.setActualVolumeKg(rs.getInt("ACTUAL_VOLUME_KG"));
                p.setStatus(rs.getString("STATUS"));
                list.add(p);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Pickup> findActiveByVehicle(String vehicleID) {
        List<Pickup> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT * FROM PICKUP_TBL_NEW WHERE VEHICLE_ID=? AND STATUS='SCHEDULED'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vehicleID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Pickup p = new Pickup();
                p.setPickupID(rs.getInt("PICKUP_ID"));
                p.setZoneID(rs.getString("ZONE_ID"));
                p.setVehicleID(rs.getString("VEHICLE_ID"));
                p.setPickupDate(rs.getDate("PICKUP_DATE"));
                p.setStartTime(rs.getString("START_TIME"));
                p.setExpectedVolumeKg(rs.getInt("EXPECTED_VOLUME_KG"));
                p.setActualVolumeKg(rs.getInt("ACTUAL_VOLUME_KG"));
                p.setStatus(rs.getString("STATUS"));
                list.add(p);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }
    public List<Pickup> findConflicts(String zoneID, String vehicleID, Date date, String time) {
        List<Pickup> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT * FROM PICKUP_TBL_NEW WHERE PICKUP_DATE=? AND START_TIME=? " +
                         "AND (ZONE_ID=? OR VEHICLE_ID=?) AND STATUS='SCHEDULED'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setString(2, time);
            ps.setString(3, zoneID);
            ps.setString(4, vehicleID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Pickup p = new Pickup();
                p.setPickupID(rs.getInt("PICKUP_ID"));
                p.setZoneID(rs.getString("ZONE_ID"));
                p.setVehicleID(rs.getString("VEHICLE_ID"));
                p.setPickupDate(rs.getDate("PICKUP_DATE"));
                p.setStartTime(rs.getString("START_TIME"));
                p.setExpectedVolumeKg(rs.getInt("EXPECTED_VOLUME_KG"));
                p.setActualVolumeKg(rs.getInt("ACTUAL_VOLUME_KG"));
                p.setStatus(rs.getString("STATUS"));
                list.add(p);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }
}
