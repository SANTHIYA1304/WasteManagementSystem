package com.waste.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.waste.bean.Zone;
import com.waste.util.DBUtil;

public class ZoneDAO {

	public Zone findZone(String zoneID) {
	    Zone z = null;
	    try (Connection con = DBUtil.getDBConnection()) {
	        String sql = "SELECT * FROM ZONE_TBL_NEW WHERE ZONE_ID=?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, zoneID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            z = new Zone();
	            z.setZoneID(rs.getString(1));
	            z.setZoneName(rs.getString(2));
	            z.setZoneType(rs.getString(3));
	            z.setAreaSqKm(rs.getDouble(4));
	            z.setStatus(rs.getString(5));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return z;
	}
    public boolean insertZone(Zone z) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "INSERT INTO ZONE_TBL_NEW VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, z.getZoneID());
            ps.setString(2, z.getZoneName());
            ps.setString(3, z.getZoneType());
            ps.setDouble(4, z.getAreaSqKm());
            ps.setString(5, z.getStatus());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateZone(Zone z) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "UPDATE ZONE_TBL_NEW SET ZONE_NAME=?, ZONE_TYPE=?, AREA_SQKM=?, STATUS=? WHERE ZONE_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, z.getZoneName());
            ps.setString(2, z.getZoneType());
            ps.setDouble(3, z.getAreaSqKm());
            ps.setString(4, z.getStatus());
            ps.setString(5, z.getZoneID());

            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean deleteZone(String zoneID) {
        boolean result = false;
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "DELETE FROM ZONE_TBL_NEW WHERE ZONE_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, zoneID);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<Zone> viewAllZones() {
        List<Zone> zones = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection()) {
            String sql = "SELECT * FROM ZONE_TBL_NEW";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Zone z = new Zone();
                z.setZoneID(rs.getString("ZONE_ID"));
                z.setZoneName(rs.getString("ZONE_NAME"));
                z.setZoneType(rs.getString("ZONE_TYPE"));
                z.setAreaSqKm(rs.getDouble("AREA_SQKM"));
                z.setStatus(rs.getString("STATUS"));
                zones.add(z);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zones;
    }
}
