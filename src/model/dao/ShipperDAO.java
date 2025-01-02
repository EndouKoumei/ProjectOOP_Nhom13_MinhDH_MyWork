package model.dao;

import model.bean.Shipper;

import java.sql.*;
import java.util.ArrayList;

public class ShipperDAO extends DBconnect {

    // Lấy danh sách tất cả các shipper
    public ArrayList<Shipper> getAllShippers() throws SQLException {
        connect();

        String sql = "SELECT * FROM Shipper";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Shipper> shippers = new ArrayList<>();
        while (rs.next()) {
            Shipper shipper = new Shipper();
            shipper.setEmployeeID(rs.getString("EmployeeID"));
            shipper.setLastName(rs.getString("LastName"));
            shipper.setMiddleName(rs.getString("MiddleName"));
            shipper.setFirstName(rs.getString("FirstName"));
            shipper.setGender(rs.getString("Gender"));
            shipper.setBirthday(rs.getDate("Birthday"));
            shipper.setPhone(rs.getString("Phone"));
            shipper.setHomeTown(rs.getString("HomeTown"));
            shippers.add(shipper);
        }
        return shippers;
    }

    // Thêm shipper mới
    public void addShipper(Shipper shipper) throws SQLException {
        connect();

        String sql = "INSERT INTO Shipper (EmployeeID, LastName, MiddleName, FirstName, Gender, Birthday, Phone, HomeTown) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, shipper.getEmployeeID());
        stmt.setString(2, shipper.getLastName());
        stmt.setString(3, shipper.getMiddleName());
        stmt.setString(4, shipper.getFirstName());
        stmt.setString(5, shipper.getGender());
        stmt.setDate(6, new java.sql.Date(shipper.getBirthday().getTime()));
        stmt.setString(7, shipper.getPhone());
        stmt.setString(8, shipper.getHomeTown());

        stmt.executeUpdate();
    }

    // Cập nhật thông tin shipper
    public void updateShipper(Shipper shipper) throws SQLException {
        connect();

        String sql = "UPDATE Shipper SET LastName = ?, MiddleName = ?, FirstName = ?, Gender = ?, Birthday = ?, Phone = ?, HomeTown = ? " +
                "WHERE EmployeeID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, shipper.getLastName());
        stmt.setString(2, shipper.getMiddleName());
        stmt.setString(3, shipper.getFirstName());
        stmt.setString(4, shipper.getGender());
        stmt.setDate(5, new java.sql.Date(shipper.getBirthday().getTime()));
        stmt.setString(6, shipper.getPhone());
        stmt.setString(7, shipper.getHomeTown());
        stmt.setString(8, shipper.getEmployeeID());

        stmt.executeUpdate();
    }

    // Xóa shipper
    public void deleteShipper(String employeeID) throws SQLException {
        connect();

        String sql = "DELETE FROM Shipper WHERE EmployeeID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, employeeID);

        stmt.executeUpdate();
    }
}

