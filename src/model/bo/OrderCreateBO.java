package model.bo;

import model.bean.OrderCreate;
//import model.bean.OrderDetails;
//import model.dao.OrderCreateDAO;

//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderCreateBO {
    private ArrayList<OrderCreate> orders;
    public Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi khi parse
        }
    }
    public OrderCreateBO() {
        // Initialize with static test data
        orders = new ArrayList<>();
        
        // Create sample orders with different dates
        orders.add(new OrderCreate("P001", "U025", "U016", parseDate("2021-03-19")));
        orders.add(new OrderCreate("P002", "U028", null, parseDate("2024-05-28")));
        orders.add(new OrderCreate("P003", "U031", null, parseDate("2024-05-29")));
        orders.add(new OrderCreate("P004", "U007", "U019", parseDate("2024-05-14")));
        orders.add(new OrderCreate("P005", "U020", "U034", parseDate("2024-05-30")));
        orders.add(new OrderCreate("P006", "U019", "U005", parseDate("2023-11-05")));
        orders.add(new OrderCreate("P007", "U018", "U025", parseDate("2024-05-26")));
        orders.add(new OrderCreate("P008", "U033", null, parseDate("2024-05-25")));
        orders.add(new OrderCreate("P009", "U002", "U010", parseDate("2024-05-27")));
        orders.add(new OrderCreate("P010", "U011", null, parseDate("2021-09-18")));
        orders.add(new OrderCreate("P011", "U010", "U027", parseDate("2024-05-09")));
        orders.add(new OrderCreate("P012", "U012", null, parseDate("2024-06-01")));
        orders.add(new OrderCreate("P013", "U025", "U004", parseDate("2024-06-02")));
        orders.add(new OrderCreate("P014", "U029", "U018", parseDate("2024-05-31")));
        orders.add(new OrderCreate("P015", "U020", null, parseDate("2024-06-01")));
        orders.add(new OrderCreate("P016", "U015", null, parseDate("2023-02-14")));
        orders.add(new OrderCreate("P017", "U033", "U022", parseDate("2024-05-23")));
        orders.add(new OrderCreate("P018", "U003", "U019", parseDate("2024-05-25")));
        orders.add(new OrderCreate("P019", "U003", "U019", parseDate("2024-05-28")));
        orders.add(new OrderCreate("P020", "U015", null, parseDate("2024-05-29")));
        orders.add(new OrderCreate("P021", "U028", "U033", parseDate("2022-09-09")));
        orders.add(new OrderCreate("P022", "U010", "U004", parseDate("2024-06-01")));
        orders.add(new OrderCreate("P023", "U006", "U030", parseDate("2024-05-31")));
        orders.add(new OrderCreate("P024", "U029", null, parseDate("2024-07-30")));
        orders.add(new OrderCreate("P025", "U026", "U013", parseDate("2024-06-01")));
        orders.add(new OrderCreate("P026", "U022", null, parseDate("2024-05-30")));
        orders.add(new OrderCreate("P027", "U026","U013", parseDate("2024-05-28")));
        orders.add(new OrderCreate("P028", "U026", null, parseDate("2024-06-01")));
        orders.add(new OrderCreate("P029", "U019", "U027", parseDate("2024-06-01")));
        orders.add(new OrderCreate("P030", "U024", "U007", parseDate("2024-01-13")));
        orders.add(new OrderCreate("P031", "U002", null, parseDate("2024-05-30")));
        orders.add(new OrderCreate("P032", "U021", "U005", parseDate("2022-12-22")));
        orders.add(new OrderCreate("P033", "U024", "U029", parseDate("2024-04-30")));
        orders.add(new OrderCreate("P034", "U024", "U029", parseDate("2024-05-30")));
        orders.add(new OrderCreate("P035", "U019", "U029", parseDate("2024-05-29")));
        orders.add(new OrderCreate("P036", "U028", null, parseDate("2024-08-31")));
        orders.add(new OrderCreate("P037", "U030", null, parseDate("2024-12-31")));
        orders.add(new OrderCreate("P038", "U009", null, parseDate("2024-05-27")));
        orders.add(new OrderCreate("P039", "U015", null, parseDate("2024-05-31")));
        orders.add(new OrderCreate("P040", "U009", null, parseDate("2024-06-01")));
        orders.add(new OrderCreate("P041", "U025", null, parseDate("2024-05-31")));
        orders.add(new OrderCreate("P042", "U028", "U025", parseDate("2024-05-28")));
        orders.add(new OrderCreate("P043", "U028", "U025", parseDate("2024-05-28")));
        orders.add(new OrderCreate("P044", "U007", null, parseDate("2024-05-28")));
        orders.add(new OrderCreate("P045", "U003", "U019", parseDate("2024-05-28")));
        orders.add(new OrderCreate("P046", "U019", null, parseDate("2024-06-01")));
        orders.add(new OrderCreate("P047", "U005", "U013", parseDate("2024-05-31")));
        orders.add(new OrderCreate("P048", "U008", "U001", parseDate("2023-04-06")));
        orders.add(new OrderCreate("P049", "U015", null, parseDate("2024-02-14")));
        orders.add(new OrderCreate("P050", "U009", "U030", parseDate("2024-05-29")));
    }

    public ArrayList<OrderCreate> getAllOrders() {
        return orders;
    }

    public ArrayList<OrderCreate> getOrdersByUserID(String userID) {
        ArrayList<OrderCreate> userOrders = new ArrayList<>();
        for (OrderCreate order : orders) {
            if (order.getGiverID().equals(userID) || order.getReceiverID().equals(userID)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    // Other methods remain the same but work with the static list
    public boolean addOrder(OrderCreate order) {
        return orders.add(order);
    }

    public boolean updateOrder(OrderCreate order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderID().equals(order.getOrderID())) {
                orders.set(i, order);
                return true;
            }
        }
        return false;
    }

    public boolean deleteOrder(String orderID) {
        return orders.removeIf(order -> order.getOrderID().equals(orderID));
    }
}
/*
public class OrderCreateBO {
    private OrderCreateDAO orderCreateDAO = new OrderCreateDAO();

    // Lấy danh sách tất cả đơn hàng
    public ArrayList<OrderCreate> getAllOrders() {
        try {
            return orderCreateDAO.getAllOrders(); // Gọi phương thức từ DAO
        } catch (SQLException e) {
            // Xử lý lỗi tại đây, ghi log hoặc thông báo lỗi
            System.err.println("Error while fetching orders: " + e.getMessage());
            // Trả về danh sách rỗng nếu xảy ra lỗi
            return new ArrayList<>();
        }
    }

   // Thêm đơn hàng mới
    public boolean addOrder(OrderCreate order) {
        try {
            orderCreateDAO.addOrder(order);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Nguoi gui khong the tu dat don hang cua minh!")) {
                System.out.println("Loi: Nguoi gui khong the la nguoi nhan");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Cập nhật đơn hàng
    public boolean updateOrder(OrderCreate order) {
        try {
            orderCreateDAO.updateOrder(order);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Nguoi gui khong the tu dat don hang cua minh!")) {
                System.out.println("Loi: Nguoi gui khong the la nguoi nhan");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Xóa đơn hàng
    public boolean deleteOrder(String orderID) {
        try {
            orderCreateDAO.deleteOrder(orderID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Lấy danh sách đơn hàng theo UserID
    public ArrayList<OrderCreate> getOrdersByUserID(String userID) {
        try {
            return orderCreateDAO.getOrdersByUserID(userID);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách đơn hàng trong BO: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    */

    /*
   // Lấy danh sách đơn hàng theo UserID (gửi hoặc nhận)
   public ArrayList<OrderCreate> getOrdersByUserID(String userID) {
       ArrayList<OrderCreate> orders = new ArrayList<>();
       try {
           orders = orderCreateDAO.getOrdersByUserID(userID);
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Lỗi khi lấy danh sách đơn hàng: " + e.getMessage());
       }
       return orders;
   }
    
}
*/
