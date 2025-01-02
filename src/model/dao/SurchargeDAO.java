package model.dao;

import model.bean.Surcharge;
import java.sql.*;
import java.util.ArrayList;

public class SurchargeDAO extends DBconnect {

    // Lấy danh sách tất cả các phụ phí
    public ArrayList<Surcharge> getAllSurcharges() throws SQLException {
        connect();  // Kết nối cơ sở dữ liệu

        String sql = "SELECT SurchargeID, SurchargeName, Price FROM Surcharge";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Surcharge> surcharges = new ArrayList<>();
        while (rs.next()) {
            Surcharge surcharge = new Surcharge();
            surcharge.setSurchargeID(rs.getString("SurchargeID"));
            surcharge.setSurchargeName(rs.getString("SurchargeName"));
            surcharge.setPrice(rs.getDouble("Price"));
            surcharges.add(surcharge);
        }
        return surcharges;  // Trả về danh sách phụ phí
    }

    // Thêm phụ phí mới
    public void addSurcharge(Surcharge surcharge) throws SQLException {
        connect();

        String sql = "INSERT INTO Surcharge (SurchargeID, SurchargeName, Price) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, surcharge.getSurchargeID());
        stmt.setString(2, surcharge.getSurchargeName());
        stmt.setDouble(3, surcharge.getPrice());

        stmt.executeUpdate();  // Thực thi câu lệnh INSERT để thêm phụ phí
    }

    // Cập nhật phụ phí
    public void updateSurcharge(Surcharge surcharge) throws SQLException {
        connect();

        String sql = "UPDATE Surcharge SET SurchargeName = ?, Price = ? WHERE SurchargeID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, surcharge.getSurchargeName());
        stmt.setDouble(2, surcharge.getPrice());
        stmt.setString(3, surcharge.getSurchargeID());

        stmt.executeUpdate();  // Thực thi câu lệnh UPDATE để cập nhật phụ phí
    }

    // Xóa phụ phí
    public void deleteSurcharge(String surchargeID) throws SQLException {
        connect();

        String sql = "DELETE FROM Surcharge WHERE SurchargeID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, surchargeID);

        stmt.executeUpdate();  // Thực thi câu lệnh DELETE để xóa phụ phí
    }
}
