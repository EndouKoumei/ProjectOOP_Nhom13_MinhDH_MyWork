package model.bo;

import model.bean.Statusofproduct;
//import model.dao.StatusofproductDAO;

//import java.sql.SQLException;
import java.util.ArrayList;

public class StatusofproductBO {
    private ArrayList<Statusofproduct> statuses;

    public StatusofproductBO() {
        // Initialize with static test data
        statuses = new ArrayList<>();
        
        // Add sample status data with different states
        statuses.add(new Statusofproduct("P001", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P002", "W064", "DELIVERING"));
        statuses.add(new Statusofproduct("P003", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P004", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P005", "0000", "PENDING"));
        statuses.add(new Statusofproduct("P006", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P007", "0000", "CANCELLED"));
        statuses.add(new Statusofproduct("P008", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P009", "W058", "DELIVERING"));
        statuses.add(new Statusofproduct("P010", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P011", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P012", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P013", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P014", "0000", "PENDING"));
        statuses.add(new Statusofproduct("P015", "W024", "DELIVERING"));
        statuses.add(new Statusofproduct("P016", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P017", "Done", "CANCELLED"));
        statuses.add(new Statusofproduct("P018", "0000", "CANCELLED"));
        statuses.add(new Statusofproduct("P019", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P020", "W035", "DELIVERING"));
        statuses.add(new Statusofproduct("P021", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P022", "W042", "DELIVERING"));
        statuses.add(new Statusofproduct("P023", "0000", "PENDING"));
        statuses.add(new Statusofproduct("P024", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P025", "0000", "CANCELLED"));
        statuses.add(new Statusofproduct("P026", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P027", "W025", "DELIVERING"));
        statuses.add(new Statusofproduct("P028", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P029", "W025", "DELIVERING"));
        statuses.add(new Statusofproduct("P030", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P031", "0000", "PENDING"));
        statuses.add(new Statusofproduct("P032", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P033", "0000", "CANCELLED"));
        statuses.add(new Statusofproduct("P034", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P035", "W028", "DELIVERING"));
        statuses.add(new Statusofproduct("P036", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P037", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P038", "W029", "DELIVERING"));
        statuses.add(new Statusofproduct("P039", "Done", "CANCELLED"));
        statuses.add(new Statusofproduct("P040", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P041", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P042", "0000", "CANCELLED"));
        statuses.add(new Statusofproduct("P043", "W042", "DELIVERING"));
        statuses.add(new Statusofproduct("P044", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P045", "Done", "DELIVERING"));
        statuses.add(new Statusofproduct("P046", "W023", "DELIVERING"));
        statuses.add(new Statusofproduct("P047", "0000", "PENDING"));
        statuses.add(new Statusofproduct("P048", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P049", "Done", "DELIVERED"));
        statuses.add(new Statusofproduct("P050", "W020", "DELIVERING"));
    }

    public ArrayList<Statusofproduct> getAllStatusofproduct() {
        return statuses;
    }

    public boolean addStatus(Statusofproduct status) {
        return statuses.add(status);
    }

    public boolean updateStatus(Statusofproduct status) {
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).getOrderID().equals(status.getOrderID())) {
                statuses.set(i, status);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStatus(String orderID) {
        return statuses.removeIf(status -> status.getOrderID().equals(orderID));
    }
}
/*
public class StatusofproductBO {
    private StatusofproductDAO statusofproductDAO = new StatusofproductDAO();

    // Lấy tất cả trạng thái của đơn hàng
    public ArrayList<Statusofproduct> getAllStatusofproduct() {
        try {
            return statusofproductDAO.getAllStatusofproduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm trạng thái đơn hàng mới
    public boolean addStatus(Statusofproduct status) {
        try {
            statusofproductDAO.addStatus(status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật trạng thái đơn hàng
    public boolean updateStatus(Statusofproduct status) {
        try {
            statusofproductDAO.updateStatus(status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa trạng thái đơn hàng
    public boolean deleteStatus(String orderID) {
        try {
            statusofproductDAO.deleteStatus(orderID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
*/
