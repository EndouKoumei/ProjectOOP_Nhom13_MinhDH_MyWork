package model.bo;

import model.bean.OrderDetails;
//import model.dao.OrderDetailsDAO;

//import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBO {
    private ArrayList<OrderDetails> orderDetails;

    public OrderDetailsBO() {
        // Initialize with static test data
        orderDetails = new ArrayList<>();
        
        // Add sample order details
        orderDetails.add(new OrderDetails("IID001", "P002", "Laptop Gamming Lenovo", "SU007", 2.50, 25000000.000));
        orderDetails.add(new OrderDetails("IID002", "P002", "Mouse", "SU007", 0.10, 100000.000));
        orderDetails.add(new OrderDetails("IID003", "P003", "Keyboard", "SU007", 0.70, 1000000.000));
        orderDetails.add(new OrderDetails("IID004", "P004", "Monitor", "SU007", 5.00, 4000000.000));
        orderDetails.add(new OrderDetails("IID005", "P005", "Printer", "SU007", 8.00, 10000000.000));
        orderDetails.add(new OrderDetails("IID006", "P006", "Scanner", "SU007", 3.20, 5000000.000));
        orderDetails.add(new OrderDetails("IID008", "P008", "Charger", "SU007", 0.20, 50000.000));
        orderDetails.add(new OrderDetails("IID009", "P008", "Iphone 14 promax", "SU007", 0.40, 25000000.000));
        orderDetails.add(new OrderDetails("IID010", "P010", "Earphones", "SU007", 0.05, 5000000.000));
        orderDetails.add(new OrderDetails("IID011", "P011", "But bi", "SU001", 0.02, 10000.000));
        orderDetails.add(new OrderDetails("IID012", "P012", "Vo ghi chep", "SU001", 0.50, 100000.000));
        orderDetails.add(new OrderDetails("IID013", "P013", "Cap sach", "SU001", 0.70, 400000.000));
        orderDetails.add(new OrderDetails("IID014", "P014", "Hop but", "SU001", 0.10, 200000.000));
        orderDetails.add(new OrderDetails("IID015", "P015", "Bang trang", "SU001", 0.10, 50000.000));
        orderDetails.add(new OrderDetails("IID016", "P016", "But long", "SU001", 0.03, 100000.000));
        orderDetails.add(new OrderDetails("IID017", "P017", "But chi mau", "SU001", 0.20, 60000.000));
        orderDetails.add(new OrderDetails("IID019", "P019", "Keo", "SU001", 0.15, 100000.000));
        orderDetails.add(new OrderDetails("IID020", "P020", "Thuoc ke", "SU001", 0.10, 50000.000));
        orderDetails.add(new OrderDetails("IID021", "P021", "Giay A4", "SU001", 0.50, 100000.000));
        orderDetails.add(new OrderDetails("IID022", "P022", "Giay in mau", "SU001", 1.00, 90000.000));
        orderDetails.add(new OrderDetails("IID023", "P023", "So tay", "SU001", 0.30, 30000.000));
        orderDetails.add(new OrderDetails("IID024", "P024", "Ghim bam", "SU001", 0.02, 100000.000));
        orderDetails.add(new OrderDetails("IID026", "P026", "Kep giay", "SU001", 0.02, 70000.000));
        orderDetails.add(new OrderDetails("IID027", "P027", "Dao roc giay", "SU001", 0.05, 200000.000));
        orderDetails.add(new OrderDetails("IID028", "P028", "Bia cung", "SU001", 0.30, 100000.000));
        orderDetails.add(new OrderDetails("IID029", "P029", "Bang dinh", "SU001", 0.08, 50000.000));
        orderDetails.add(new OrderDetails("IID030", "P030", "Cuc tay", "SU001", 0.02, 70000.000));
        orderDetails.add(new OrderDetails("IID031", "P031", "Giay note", "SU001", 0.10, 100000.000));
        orderDetails.add(new OrderDetails("IID032", "P032", "Giay nhan", "SU001", 0.05, 10000.000));
        orderDetails.add(new OrderDetails("IID034", "P034", "But muc", "SU001", 0.02, 60000.000));
        orderDetails.add(new OrderDetails("IID035", "P035", "Giay bia", "SU001", 0.50, 90000.000));
        orderDetails.add(new OrderDetails("IID036", "P036", "Kep buom", "SU001", 0.03, 50000.000));
        orderDetails.add(new OrderDetails("IID037", "P037", "Giay decal", "SU001", 0.20, 20000.000));
        orderDetails.add(new OrderDetails("IID038", "P038", "But nho", "SU001", 0.03, 5.500));
        orderDetails.add(new OrderDetails("IID039", "P039", "Giay than", "SU001", 0.20, 4.500));
        orderDetails.add(new OrderDetails("IID040", "P040", "Ghim kep", "SU001", 0.01, 200000.000));
        orderDetails.add(new OrderDetails("IID041", "P041", "So ke hoach", "SU001", 0.30, 50000.000));
        orderDetails.add(new OrderDetails("IID043", "P043", "Thit bo", "SU006", 1.50, 90000.000));
        orderDetails.add(new OrderDetails("IID044", "P044", "Ca hoi", "SU006", 1.20, 70000.000));
        orderDetails.add(new OrderDetails("IID046", "P046", "Tom su", "SU006", 1.00, 0.000));
        orderDetails.add(new OrderDetails("IID047", "P047", "Hai san dong lanh", "SU006", 2.50, 0.000));
        orderDetails.add(new OrderDetails("IID048", "P048", "Nuoc rua chen", "SU005", 1.00, 0.000));
        orderDetails.add(new OrderDetails("IID049", "P049", "Dau goi dau", "SU005", 0.50, 0.000));
        orderDetails.add(new OrderDetails("IID050", "P050", "Sua tam", "SU005", 0.60, 0.000));
        orderDetails.add(new OrderDetails("IID051", "P003", "Nuoc xa vai", "SU005", 0.80, 0.000));
        orderDetails.add(new OrderDetails("IID052", "P002", "Nuoc lau san", "SU005", 1.20, 90000.000));
        orderDetails.add(new OrderDetails("IID053", "P003", "Binh Hoa", "SU003", 0.80, 70000.000));
        orderDetails.add(new OrderDetails("IID054", "P004", "Chen Su", "SU003", 0.30, 0.000));
        orderDetails.add(new OrderDetails("IID055", "P005", "Ly Thuy Tinh", "SU003", 0.40, 0.000));
        orderDetails.add(new OrderDetails("IID056", "P001", "Tu Quan Ao", "SU004", 50.00, 3000000.000));
        orderDetails.add(new OrderDetails("IID057", "P001", "Giuong Ngu", "SU004", 70.00, 5000000.000));
        orderDetails.add(new OrderDetails("IID058", "P001", "Ban Lam Viec", "SU004", 25.00, 100000.000));
        orderDetails.add(new OrderDetails("IID059", "P009", "Dong ho Rolex", "SU002", 0.10, 100000000.000));
        orderDetails.add(new OrderDetails("IID060", "P009", "Lac tay bac", "SU002", 2.50, 50000000.000));
        orderDetails.add(new OrderDetails("IID061", "P009", "Vang nguyen khoi", "SU002", 1.00, 70000000.000));
    }

    public ArrayList<OrderDetails> getOrderDetailsByOrderID(String orderID) {
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (OrderDetails detail : orderDetails) {
            if (detail.getOrderID().equals(orderID)) {
                details.add(detail);
            }
        }
        return details;
    }

    public ArrayList<OrderDetails> getListOrderDetails() {
        return orderDetails;
    }

    public void addOrderDetails(OrderDetails detail) {
        orderDetails.add(detail);
    }

    public void updateOrderDetails(OrderDetails detail) {
        for (int i = 0; i < orderDetails.size(); i++) {
            if (orderDetails.get(i).getItemID().equals(detail.getItemID())) {
                orderDetails.set(i, detail);
                return;
            }
        }
    }

    public void deleteOrderDetails(String itemID) {
        orderDetails.removeIf(detail -> detail.getItemID().equals(itemID));
    }
}
/*
public class OrderDetailsBO {
    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

    // Lấy danh sách chi tiết đơn hàng theo OrderID
    public ArrayList<OrderDetails> getOrderDetailsByOrderID(String orderID) {
        try {
            return orderDetailsDAO.getOrderDetailsByOrderID(orderID);
        } catch (Exception e) {
            System.err.println("Lỗi trong BO khi lấy chi tiết đơn hàng: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Trả về danh sách rỗng nếu xảy ra lỗi
        }
    }

    // Thêm OrderDetails
    public void addOrderDetails(OrderDetails orderDetails) {
        try {
            orderDetailsDAO.addOrderDetails(orderDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách OrderDetails
    public ArrayList<OrderDetails> getListOrderDetails() {
        try {
            return orderDetailsDAO.getListOrderDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật OrderDetails
    public void updateOrderDetails(OrderDetails orderDetails) {
        try {
            orderDetailsDAO.updateOrderDetails(orderDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa OrderDetails
    public void deleteOrderDetails(String itemID) {
        try {
            orderDetailsDAO.deleteOrderDetails(itemID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
*/
