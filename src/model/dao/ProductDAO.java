package model.dao;

import model.bean.Product;
import java.sql.*;
import java.util.ArrayList;

public class ProductDAO extends DBconnect {
        private DBconnect dbConnect = new DBconnect();

        // Lấy danh sách các sản phẩm theo UserID (GiverID hoặc ReceiverID)
        public ArrayList<Product> getProductsByUserID(String userID) {
            ArrayList<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM Product WHERE OrderID IN (" +
                    "SELECT OrderID FROM OrderCreate WHERE GiverID = ? OR ReciverID = ?)";

            try {
                dbConnect.connect();
                Connection connection = dbConnect.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, userID);
                    stmt.setString(2, userID);

                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            Product product = new Product();
                            product.setOrderID(rs.getString("OrderID"));
                            product.setPayer(rs.getString("Payer"));
                            product.setPickupCity(rs.getString("PickupCity"));
                            product.setPickupDistrict(rs.getString("PickupDistrict"));
                            product.setPickupWard(rs.getString("PickupWard"));
                            product.setPickupAddress(rs.getString("PickupAddress"));
                            product.setRecipientName(rs.getString("RecipientName"));
                            product.setPhoneRecipient(rs.getString("PhoneRecipient"));
                            product.setDeliveryCity(rs.getString("DeliveryCity"));
                            product.setDeliveryDistrict(rs.getString("DeliveryDistrict"));
                            product.setDeliveryWard(rs.getString("DeliveryWard"));
                            product.setDeliveryAddress(rs.getString("DeliveryAddress"));
                            product.setServiceID(rs.getString("ServiceID"));
                            products.add(product);
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi truy vấn sản phẩm theo UserID: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (dbConnect.getConnection() != null) {
                        dbConnect.getConnection().close();
                    }
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                }
            }
            return products;
        }

    // Lấy danh sách tất cả các sản phẩm (đơn hàng)
    public ArrayList<Product> getAllProducts() throws SQLException {
        connect();

        String sql = "SELECT * FROM Product";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setOrderID(rs.getString("OrderID"));
            product.setPayer(rs.getString("Payer"));
            product.setPickupCity(rs.getString("PickupCity"));
            product.setPickupDistrict(rs.getString("PickupDistrict"));
            product.setPickupWard(rs.getString("PickupWard"));
            product.setPickupAddress(rs.getString("PickupAddress"));
            product.setRecipientName(rs.getString("RecipientName"));
            product.setPhoneRecipient(rs.getString("PhoneRecipient"));
            product.setDeliveryCity(rs.getString("DeliveryCity"));
            product.setDeliveryDistrict(rs.getString("DeliveryDistrict"));
            product.setDeliveryWard(rs.getString("DeliveryWard"));
            product.setDeliveryAddress(rs.getString("DeliveryAddress"));
            product.setServiceID(rs.getString("ServiceID"));
            products.add(product);
        }
        return products;
    }

    // Thêm chi tiet don hàng mới
    public void addProduct(Product product) throws SQLException {
        connect();

        String sql = "INSERT INTO Product (OrderID, Payer, PickupCity, PickupDistrict, PickupWard, PickupAddress, " +
                "RecipientName, PhoneRecipient, DeliveryCity, DeliveryDistrict, DeliveryWard, DeliveryAddress, ServiceID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, product.getOrderID());
        stmt.setString(2, product.getPayer());
        stmt.setString(3, product.getPickupCity());
        stmt.setString(4, product.getPickupDistrict());
        stmt.setString(5, product.getPickupWard());
        stmt.setString(6, product.getPickupAddress());
        stmt.setString(7, product.getRecipientName());
        stmt.setString(8, product.getPhoneRecipient());
        stmt.setString(9, product.getDeliveryCity());
        stmt.setString(10, product.getDeliveryDistrict());
        stmt.setString(11, product.getDeliveryWard());
        stmt.setString(12, product.getDeliveryAddress());
        stmt.setString(13, product.getServiceID());

        stmt.executeUpdate();
    }
}

