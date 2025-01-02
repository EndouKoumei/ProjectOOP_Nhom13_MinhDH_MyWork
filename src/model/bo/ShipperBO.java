package model.bo;

import model.bean.Shipper;
import model.dao.ShipperDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShipperBO {
    private ShipperDAO shipperDAO = new ShipperDAO();
    //lấy danh sách tất cả Shipper
    public ArrayList<Shipper> getAllShippers() throws SQLException {
        try {
            return shipperDAO.getAllShippers();
        } catch (SQLException e) {
            // Xử lý lỗi tại đây nếu cần thiết
            throw e;  // Ném lại SQLException để có thể xử lý ở nơi gọi
        }
    }
    // Thêm shipper mới
    public boolean addShipper(Shipper shipper) {
        try {
            shipperDAO.addShipper(shipper);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin shipper
    public boolean updateShipper(Shipper shipper) {
        try {
            shipperDAO.updateShipper(shipper);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa shipper
    public boolean deleteShipper(String employeeID) {
        try {
            shipperDAO.deleteShipper(employeeID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
