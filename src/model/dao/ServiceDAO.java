package model.dao;

import model.bean.Service;
import java.sql.*;
import java.util.ArrayList;

public class ServiceDAO extends DBconnect {

    // Lấy danh sách tất cả các dịch vụ
    public ArrayList<Service> getAllServices() throws SQLException {
        connect();  // Kết nối cơ sở dữ liệu

        String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Service> services = new ArrayList<>();
        while (rs.next()) {
            Service service = new Service();
            service.setServiceID(rs.getString("ServiceID"));
            service.setServiceName(rs.getString("ServiceName"));
            service.setPrice(rs.getDouble("Price"));
            service.setMaxDistance(rs.getString("MaxDistance"));
            services.add(service);
        }
        return services;  // Trả về danh sách dịch vụ
    }

    // Thêm dịch vụ mới
    public void addService(Service service) throws SQLException {
        connect();

        String sql = "INSERT INTO Service (ServiceID, ServiceName, Price, MaxDistance) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, service.getServiceID());
        stmt.setString(2, service.getServiceName());
        stmt.setDouble(3, service.getPrice());
        stmt.setString(4, service.getMaxDistance());

        stmt.executeUpdate();  // Thực thi câu lệnh thêm dịch vụ
    }

    // Cập nhật dịch vụ
    public void updateService(Service service) throws SQLException {
        connect();

        String sql = "UPDATE Service SET ServiceName = ?, Price = ?, MaxDistance = ? WHERE ServiceID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, service.getServiceName());
        stmt.setDouble(2, service.getPrice());
        stmt.setString(3, service.getMaxDistance());
        stmt.setString(4, service.getServiceID());

        stmt.executeUpdate();  // Thực thi câu lệnh cập nhật dịch vụ
    }

    // Xóa dịch vụ
    public void deleteService(String serviceID) throws SQLException {
        connect();

        String sql = "DELETE FROM Service WHERE ServiceID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, serviceID);

        stmt.executeUpdate();  // Thực thi câu lệnh xóa dịch vụ
    }
}


