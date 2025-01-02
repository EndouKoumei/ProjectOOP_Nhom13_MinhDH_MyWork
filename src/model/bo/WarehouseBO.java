package model.bo;

import model.bean.Warehouse;
import model.dao.WarehouseDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class WarehouseBO {
    private WarehouseDAO warehouseDAO = new WarehouseDAO();

    public ArrayList<Warehouse> getAllWarehouses() throws SQLException {
        return warehouseDAO.getAllWarehouses(); // Ném SQLException để phương thức gọi xử lý
    }
    // Cập nhật thông tin Warehouse
    public void updateWarehouse(Warehouse warehouse) {
        try {
            warehouseDAO.updateWarehouse(warehouse);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy thông tin Warehouse theo WarehouseID
    public Warehouse getWarehouseByID(String warehouseID) {
        try {
            return warehouseDAO.getWarehouseByID(warehouseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra nhật ký thay đổi kho
    public void checkWarehouseLogs() throws SQLException {
        warehouseDAO.getWarehouseLogs();  // Gọi phương thức trong WarehouseDAO và ném ngoại lệ nếu có lỗi
    }
}
