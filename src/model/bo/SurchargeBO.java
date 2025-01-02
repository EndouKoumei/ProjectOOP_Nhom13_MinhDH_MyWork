package model.bo;

import model.bean.Surcharge;
import model.dao.SurchargeDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SurchargeBO {
    private SurchargeDAO surchargeDAO = new SurchargeDAO();

    // Lấy danh sách phụ phí
    public ArrayList<Surcharge> getAllSurcharges() throws SQLException {
        try {
            return surchargeDAO.getAllSurcharges(); // Gọi phương thức từ ServiceDAO
        } catch (SQLException e) {
            // Xử lý lỗi tại đây nếu cần thiết, sau đó ném lại ngoại lệ
            throw e; // Ném lại SQLException để có thể xử lý ở nơi gọi
        }
    }

    // Thêm phụ phí mới
    public boolean addSurcharge(Surcharge surcharge) {
        try {
            surchargeDAO.addSurcharge(surcharge);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật phụ phí
    public boolean updateSurcharge(Surcharge surcharge) {
        try {
            surchargeDAO.updateSurcharge(surcharge);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa phụ phí
    public boolean deleteSurcharge(String surchargeID) {
        try {
            surchargeDAO.deleteSurcharge(surchargeID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
