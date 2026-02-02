package com.waste.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.waste.bean.Vehicle;
import com.waste.util.DBUtil;

public class VehicleDAO {

    public Vehicle findVehicle(String vehicleID) {
        Vehicle v = null;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT * FROM VEHICLE_TBL_NEW WHERE VEHICLE_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vehicleID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                v = new Vehicle();
                v.setVehicleID(rs.getString("VEHICLE_ID"));
                v.setVehicleType(rs.getString("VEHICLE_TYPE"));
                v.setCapacityKg(rs.getInt("CAPACITY_KG"));
                v.setSuitability(rs.getString("SUITABILITY"));
                v.setStatus(rs.getString("STATUS"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public boolean insertVehicle(Vehicle v) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "INSERT INTO VEHICLE_TBL_NEW VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, v.getVehicleID());
            ps.setString(2, v.getVehicleType());
            ps.setInt(3, v.getCapacityKg());
            ps.setString(4, v.getSuitability());
            ps.setString(5, v.getStatus());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean updateVehicle(Vehicle v) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "UPDATE VEHICLE_TBL_NEW SET VEHICLE_TYPE=?, CAPACITY_KG=?, SUITABILITY=?, STATUS=? WHERE VEHICLE_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, v.getVehicleType());
            ps.setInt(2, v.getCapacityKg());
            ps.setString(3, v.getSuitability());
            ps.setString(4, v.getStatus());
            ps.setString(5, v.getVehicleID());

            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteVehicle(String vehicleID) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "DELETE FROM VEHICLE_TBL_NEW WHERE VEHICLE_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vehicleID);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Vehicle> viewAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT * FROM VEHICLE_TBL_NEW";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setVehicleID(rs.getString("VEHICLE_ID"));
                v.setVehicleType(rs.getString("VEHICLE_TYPE"));
                v.setCapacityKg(rs.getInt("CAPACITY_KG"));
                v.setSuitability(rs.getString("SUITABILITY"));
                v.setStatus(rs.getString("STATUS"));
                vehicles.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}
