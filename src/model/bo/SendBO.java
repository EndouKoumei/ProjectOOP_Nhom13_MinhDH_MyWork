package model.bo;

import model.bean.Send;
//import model.dao.SendDAO;

//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SendBO {
    // Dữ liệu tĩnh giả lập thay vì truy cập cơ sở dữ liệu
    private ArrayList<Send> sends = new ArrayList<>();
    public Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi khi parse
        }
    }
    // Constructor để khởi tạo dữ liệu mẫu
    public SendBO() {
        sends.add(new Send("SP001", "P001", parseDate("2021-03-19"), parseDate("2021-03-19"), parseDate("2021-03-19"), "DELIVERED"));
        sends.add(new Send("SP005", "P003", parseDate("2024-06-01"), parseDate("2024-06-05"), null, null));
        sends.add(new Send("SP002", "P004", parseDate("2024-02-14"), parseDate("2024-02-14"), parseDate("2024-02-14"), "DELIVERED"));
        sends.add(new Send("SP001", "P006", parseDate("2023-11-08"), parseDate("2023-11-10"), parseDate("2023-11-09"), "DELIVERED"));
        sends.add(new Send("SP004", "P008", parseDate("2024-06-01"), parseDate("2024-06-06"), null, null));
        sends.add(new Send("SP010", "P010", parseDate("2021-09-23"), parseDate("2021-09-25"), parseDate("2021-09-24"), "DELIVERED"));
        sends.add(new Send("SP009", "P011", parseDate("2024-05-14"), parseDate("2024-05-15"), parseDate("2024-05-15"), "DELIVERED"));
        sends.add(new Send("SP001", "P012", parseDate("2024-06-01"), parseDate("2024-06-02"), null, null));
        sends.add(new Send("SP008", "P013", parseDate("2021-06-04"), parseDate("2021-06-05"), parseDate("2021-06-04"), "DELIVERED"));
        sends.add(new Send("SP001", "P016", parseDate("2023-02-24"), parseDate("2023-02-26"), parseDate("2023-02-28"), "DELIVERED"));
        sends.add(new Send("SP002", "P017", parseDate("2024-05-26"), parseDate("2024-05-27"), null, "RETURNED"));
        sends.add(new Send("SP001", "P019", parseDate("2024-06-01"), parseDate("2024-06-04"), null, null));
        sends.add(new Send("SP006", "P021", parseDate("2022-09-12"), parseDate("2022-09-15"), parseDate("2022-09-14"), "DELIVERED"));
        sends.add(new Send("SP001", "P024", parseDate("2023-08-02"), parseDate("2023-08-03"), parseDate("2023-08-03"), "DELIVERED"));
        sends.add(new Send("SP005", "P026", parseDate("2024-06-01"), parseDate("2024-06-03"), null, null));
        sends.add(new Send("SP012", "P028", parseDate("2022-06-02"), parseDate("2022-06-02"), parseDate("2022-06-03"), "DELIVERED"));
        sends.add(new Send("SP014", "P030", parseDate("2024-01-15"), parseDate("2024-01-16"), parseDate("2024-01-20"), "DELIVERED"));
        sends.add(new Send("SP006", "P032", parseDate("2022-12-26"), parseDate("2022-12-27"), parseDate("2022-12-30"), "DELIVERED"));
        sends.add(new Send("SP011", "P034", parseDate("2024-06-01"), parseDate("2024-06-02"), null, null));
        sends.add(new Send("SP005", "P036", parseDate("2022-09-05"), parseDate("2022-09-06"), parseDate("2022-09-10"), "DELIVERED"));
        sends.add(new Send("SP002", "P037", parseDate("2022-01-02"), parseDate("2022-01-03"), parseDate("2022-01-02"), "DELIVERED"));
        sends.add(new Send("SP009", "P039", parseDate("2024-06-01"), parseDate("2024-06-01"), null, "RETURNED"));
        sends.add(new Send("SP007", "P040", parseDate("2022-06-07"), parseDate("2022-06-09"), parseDate("2022-06-15"), "DELIVERED"));
        sends.add(new Send("SP015", "P041", parseDate("2024-06-01"), parseDate("2024-06-05"), null, null));
        sends.add(new Send("SP013", "P044", parseDate("2024-05-31"), parseDate("2024-05-31"), parseDate("2024-06-01"), "DELIVERED"));
        sends.add(new Send("SP019", "P045", parseDate("2024-06-01"), parseDate("2024-06-03"), null, null));
        sends.add(new Send("SP016", "P048", parseDate("2023-04-11"), parseDate("2023-04-12"), parseDate("2023-04-13"), "DELIVERED"));
        sends.add(new Send("SP020", "P049", parseDate("2024-02-20"), parseDate("2024-02-22"), parseDate("2024-02-23"), "DELIVERED"));
    }

    // Lấy tất cả các bản ghi giao hàng
    public ArrayList<Send> getAllSends() {
        return sends;
    }

    // Thêm giao hàng mới
    public boolean addSend(Send send) {
        return sends.add(send);
    }

    // Cập nhật trạng thái giao hàng
    public boolean updateSend(Send send) {
        for (Send s : sends) {
            if (s.getOrderID().equals(send.getOrderID()) && s.getEmployeeID().equals(send.getEmployeeID())) {
                s.setSendStatus(send.getSendStatus());
                return true;
            }
        }
        return false;
    }

    // Xóa giao hàng
    public boolean deleteSend(String employeeID, String orderID) {
        return sends.removeIf(s -> s.getEmployeeID().equals(employeeID) && s.getOrderID().equals(orderID));
    }

    // Lấy thông tin giao hàng theo OrderID
    public ArrayList<Send> getSendByOrderID(String orderID) {
        ArrayList<Send> result = new ArrayList<>();
        for (Send s : sends) {
            if (s.getOrderID().equals(orderID)) {
                result.add(s);
            }
        }
        return result;
    }
}

/*
public class SendBO {
    private SendDAO sendDAO = new SendDAO();

    // Lấy tất cả các bản ghi giao hàng
    public ArrayList<Send> getAllSends() {
        try {
            return sendDAO.getAllSends();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm giao hàng mới
    public boolean addSend(Send send) {
        try {
            sendDAO.addSend(send);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật trạng thái giao hàng
    public boolean updateSend(Send send) {
        try {
            sendDAO.updateSend(send);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa giao hàng
    public boolean deleteSend(String employeeID, String orderID) {
        try {
            sendDAO.deleteSend(employeeID, orderID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Lấy thông tin giao hàng theo OrderID
    public ArrayList<Send> getSendByOrderID(String orderID) {
        try {
            return sendDAO.getSendByOrderID(orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
*/