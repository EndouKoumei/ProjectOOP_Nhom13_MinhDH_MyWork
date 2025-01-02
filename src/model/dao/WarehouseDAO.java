package model.dao;

import model.bean.Warehouse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;

public class WarehouseDAO extends DBconnect {
    // Lấy danh sách tất cả các kho
    public ArrayList<Warehouse> getAllWarehouses() throws SQLException {
        connect();  // Kết nối cơ sở dữ liệu

        String sql = "SELECT WarehouseID, WareName, City, District, Ward, Address FROM Warehouse";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Warehouse> warehouses = new ArrayList<>();
        while (rs.next()) {
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseID(rs.getString("WarehouseID"));
            warehouse.setWareName(rs.getString("WareName"));
            warehouse.setCity(rs.getString("City"));
            warehouse.setDistrict(rs.getString("District"));
            warehouse.setWard(rs.getString("Ward"));
            warehouse.setAddress(rs.getString("Address"));
            warehouses.add(warehouse);
        }
        return warehouses;  // Trả về danh sách kho
    }

    // Cập nhật thông tin Warehouse
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        connect();
        String sql = "UPDATE Warehouse SET WareName = ?, City = ?, District = ?, Ward = ?, Address = ? WHERE WarehouseID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, warehouse.getWareName());
        stmt.setString(2, warehouse.getCity());
        stmt.setString(3, warehouse.getDistrict());
        stmt.setString(4, warehouse.getWard());
        stmt.setString(5, warehouse.getAddress());
        stmt.setString(6, warehouse.getWarehouseID());

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Warehouse updated successfully.");
        }
    }

    // Lấy thông tin Warehouse theo WarehouseID
    public Warehouse getWarehouseByID(String warehouseID) throws SQLException {
        connect();
        String sql = "SELECT * FROM Warehouse WHERE WarehouseID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, warehouseID);
        ResultSet rs = stmt.executeQuery();

        Warehouse warehouse = null;
        if (rs.next()) {
            warehouse = new Warehouse();
            warehouse.setWarehouseID(rs.getString("WarehouseID"));
            warehouse.setWareName(rs.getString("WareName"));
            warehouse.setCity(rs.getString("City"));
            warehouse.setDistrict(rs.getString("District"));
            warehouse.setWard(rs.getString("Ward"));
            warehouse.setAddress(rs.getString("Address"));
        }

        return warehouse;
    }

    // Lấy thông tin WarehouseLog
    public void getWarehouseLogs() throws SQLException {
        connect();
        String sql = "SELECT * FROM WarehouseLog";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (!rs.isBeforeFirst()) {
            System.out.println("Không có dữ liệu nhật ký thay đổi kho.");
            return;
        }

        while (rs.next()) {
            System.out.println("LogID: " + rs.getInt("LogID") +
                    ", WarehouseID: " + rs.getString("WarehouseID") +
                    ", OldWareName: " + rs.getString("OldWareName") +
                    ", NewWareName: " + rs.getString("NewWareName") +
                    ", ChangeTime: " + rs.getTimestamp("ChangeTime"));
        }
    }

}

