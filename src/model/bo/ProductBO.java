package model.bo;

import model.bean.Product;
//import model.dao.ProductDAO;

//import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBO {
    private ArrayList<Product> products;

    public ProductBO() {
        // Initialize with static test data
        products = new ArrayList<>();
        
        // Add sample products with detailed delivery information

        products.add(new Product("P001", "Nguoi nhan", "Bac Giang", "Yen The", "Xa Yen The", "99 Duong Dong Lac", "Nguyen Thi Lan", "0906946044", "Da Nang", "Hai Chau", "Thuan Phuoc", "So 45 Thuan Phuoc","S302"));
        products.add(new Product("P002", "Nguoi gui", "Quang Binh", "Dong Hoi", "Hai Dinh", "117 Duong Hai Dinh", "Pham Hai Anh", "0987654321", "TP. Ho Chi Minh", "Quan 1", "Ben Nghe", "So 49 Ben Nghe", "S207"));
        products.add(new Product("P003", "Nguoi nhan", "Can Tho", "Ninh Kieu", "Cai Khe", "So 84B Cai Khe", "Pham Van Dung", "0920475186", "TP. Ho Chi Minh", "Binh Thanh", "Phuong 11", "123 Duong D2", "S203"));
        products.add(new Product("P004", "Nguoi gui", "Thai Nguyen", "Thanh pho Thai Nguyen", "Phuong Quang Vinh", "449 Duong Quang Trung", "Le Van Hai", "0942326688", "Quang Ninh", "Ha Long", "Bai Chay", "So 1 Bai Chay", "S303"));
        products.add(new Product("P005", "Nguoi nhan", "Ha Noi", "Ba Dinh", "Ngoc Ha", "035 Duong Hoang Hoa Tham", "Tran Van Kiet", "0974627239", "Ha Noi", "Ba Dinh", "Ngoc Ha", "So 10 Ngoc Ha", "S101"));
        products.add(new Product("P006", "Nguoi gui", "Quang Tri", "Dong Ha", "Hai Ba Trung", "So 112 Hai Ba Trung", "Bui Van Minh", "0931867249", "Da Nang", "Son Tra", "Phuoc My", "So 07A Phuoc My", "S202"));
        products.add(new Product("P008", "Nguoi nhan", "Bac Giang", "Hiep Hoa", "Thang", "505 Duong Thang", "Vo Thi Em", "0932548791", "Can Tho", "Cai Rang", "An Khanh", "So 14 An Khanh", "S107"));
        products.add(new Product("P009", "Nguoi nhan", "Quang Tri", "Hai Lang", "Thi tran Lang Co", "723 Duong Truong Son", "Hoang Van Phong", "0947162930", "TP. Ho Chi Minh", "Go Vap", "Phuong Phu Nhuan", "185 Hung Vuong", "S107"));
        products.add(new Product("P010", "Nguoi gui", "Ha Noi", "Thanh Xuan", "Khuong Trung", "So 123 Khuong Trung", "Tran Thi Thanh Thao", "0912548796", "Nghe An", "Vinh", "Hung Dung", "So 20 Hung Dung", "S205"));
        products.add(new Product("P011", "Nguoi gui", "TP. Ho Chi Minh", "Go Vap", "Phuong Phu Nhuan", "185 Hung Vuong", "Bui Thi Hanh", "0901064092", "Thai Binh", "Kien Xuong", "Minh Quang", "789 Duong Minh Quang", "S207"));
        products.add(new Product("P012", "Nguoi nhan", "Can Tho", "Cai Rang", "An Khanh", "So 14 An Khanh", "Hoang Anh Xuan", "0953124789", "Da Nang", "Ngu Hanh Son", "My An", "So 94 My An", "S307"));
        products.add(new Product("P013", "Nguoi gui", "Bac Giang", "Yen The", "Xa Yen The", "99 Duong Dong Lac", "Pham Van Dung", "0920475186", "Ha Noi", "Cau Giay", "Quan Hoa", "123 Duong Quan Hoa", "S102"));
        products.add(new Product("P014", "Nguoi nhan", "Thai Nguyen", "Ninh Binh", "Ninh Hoa", "19 Duong Hoa Lu", "Pham Thi Hue", "0987396094", "Vinh Phuc", "Phuc Yen", "Xuan Hoa", "So 122 Xuan Hoa", "S202"));
        products.add(new Product("P015", "Nguoi gui", "Ha Noi", "Ba Dinh", "Ngoc Ha", "035 Duong Hoang Hoa Tham", "Pham Thi Duong", "0924587123", "Lang Son", "Lang Son", "Hoang Van Thu", "So 51 Hoang Van Thu", "S303"));
        products.add(new Product("P016", "Nguoi gui", "Ha Noi", "Long Bien", "Phuong Ngoc Lam", "24 Ngo Tram", "Tran Thi Chinh", "0932478591", "Da Nang", "Hai Chau", "Thuan Phuoc", "So 011 Thuan Phuoc", "S106"));
        products.add(new Product("P017", "Nguoi nhan", "Bac Giang", "Hiep Hoa", "Thang", "505 Duong Thang", "Vu Thi Thu", "0994725645", "Quang Ninh", "Ha Long", "Bai Chay", "313 Duong Ha Long", "S103"));
        products.add(new Product("P019", "Nguoi nhan", "Ha Noi", "Hai Ba Trung", "Thanh Nhan", "456 Duong Le Dai Hanh", "Le Van Hai", "0942326688", "Da Nang", "Lien Chieu", "Hoa Khanh", "886 Duong Hoang Van Thai", "S206"));
        products.add(new Product("P020", "Nguoi gui", "Ha Noi", "Long Bien", "Phuong Ngoc Lam", "24 Ngo Tram", "Tran Hoang Hien", "0925781234", "Gia Lai", "Pleiku", "Chu Se", "So 188 Chu Se", "S207"));
        products.add(new Product("P021", "Nguoi gui", "Quang Binh", "Dong Hoi", "Hai Dinh", "117 Duong Hai Dinh", "Nguyen Thi Lan", "0981902291", "Bac Giang", "Hiep Hoa", "Thang", "505 Duong Thang", "S106"));
        products.add(new Product("P022", "Nguoi nhan", "TP. Ho Chi Minh", "Go Vap", "Phuong Phu Nhuan", "185 Hung Vuong", "Pham Van Dung", "0920475186", "Ha Noi", "Cau Giay", "Quan Hoa", "123 Duong Quan Hoa", "S307"));
        products.add(new Product("P023", "Nguoi gui", "Ha Noi", "Hoan Kiem", "Phuong Trang Tien", "723 Pho Hue", "Hoang Van Khanh", "0987232244", "Ha Nam", "Phu Ly", "Trieu Duong", "26 Duong Trieu Duong", "S102"));
        products.add(new Product("P024", "Nguoi nhan", "Ninh Binh", "Ninh Hoa", "Hoa Lu", "19 Duong Hoa Lu", "Pham Thi Duong", "0945678901", "Da Nang", "Son Tra", "An Hai", "456 Duong An Hai", "S206"));
        products.add(new Product("P026", "Nguoi nhan", "Can Tho", "Binh Thuy", "Long Hoa", "456 Duong Long Hoa", "Tran Thi Phuong", "0967890123", "TP. Ho Chi Minh", "Binh Thanh", "Phuong 26", "789 Duong Phuong 26", "S203"));
        products.add(new Product("P027", "Nguoi gui", "Thanh Hoa", "Hoang Hoa", "Hoang Duc", "89 Duong Hoang Duc", "Duong Thi Lan", "0957681029", "Hai Phong", "Ngo Quyen", "Le Chan", "So 23A Le Chan", "S204"));
        products.add(new Product("P028", "Nguoi nhan", "Quang Ninh", "Cam Pha", "Cam Trung", "789 Duong Cam Trung", "Pham Thi Huong", "0989012345", "Hai Duong", "Cam Giang", "Cam Phuc", "123 Duong Cam Phuc", "S202"));
        products.add(new Product("P029", "Nguoi gui", "Da Nang", "Lien Chieu", "Hoa Khanh", "886 Duong Hoang Van Thai", "Bui Thi Hanh", "0901064092", "Thai Binh", "Kien Xuong", "Minh Quang", "789 Duong Minh Quang", "S306"));
        products.add(new Product("P030", "Nguoi nhan", "Thai Binh", "Dong Hung", "Dong Kinh", "123 Duong Dong Kinh", "Vo Thi Mai", "0986432079", "Thai Nguyen", "Thanh pho Thai Nguyen", "Phuong Quang Vinh", "449 Duong Quang Trung", "S203"));
        products.add(new Product("P031", "Nguoi gui", "Quang Tri", "Hai Lang", "Thi tran Lang Co", "723 Duong Truong Son", "Hoang Van Khanh", "0912345679", "Hai Phong", "Hong Bang", "Thuong Ly", "456 Duong Thuong Ly", "S206"));
        products.add(new Product("P032", "Nguoi nhan", "Hue", "Phu Vang", "Phu An", "632 Duong Phu An", "Bui Van Minh", "0931867249", "Bac Giang", "Yen The", "Xa Yen The", "45 Duong Dong Lac", "S201"));
        products.add(new Product("P034", "Nguoi nhan", "Hue", "An Cuu", "Thuy Bieu", "123 Duong Thuy Bieu", "Le Thi Dao", "0923333218", "Ninh Binh", "Ninh Hoa", "Hoa Lu", "19 Duong Hoa Lu", "S106"));
        products.add(new Product("P035", "Nguoi gui", "Quang Tri", "Dong Ha", "Hai Ba Trung", "So 112 Hai Ba Trung", "Le Thi Dao", "0923333218", "Ninh Binh", "Ninh Hoa", "Hoa Lu", "19 Duong Hoa Lu", "S103"));
        products.add(new Product("P036", "Nguoi nhan", "Quang Binh", "Dong Hoi", "Hai Dinh", "117 Duong Hai Dinh", "Tran Van Phuc", "0967890124", "TP. Ho Chi Minh", "Go Vap", "Phuong 17", "789 Duong Phuong 17", "S107"));
        products.add(new Product("P037", "Nguoi gui", "Ha Nam", "Phu Ly", "Trieu Duong", "26 Duong Trieu Duong", "Le Thi Quyen", "0978901235", "Quang Ninh", "Uong Bi", "Phuong Bac Son", "456 Duong Bac Son", "S104"));
        products.add(new Product("P038", "Nguoi nhan", "TP. Ho Chi Minh", "Quan 1", "Phuong 9", "789 Duong Nguyen Van Nghi", "Nguyen Van An", "0952938416", "Ha Noi", "Hoan Kiem", "Phuong Trang Tien", "723 Pho Hue", "S207"));
        products.add(new Product("P039", "Nguoi gui", "Ha Noi", "Long Bien", "Phuong Ngoc Lam", "24 Ngo Tram", "Nguyen Thi Suong", "0990123457", "Thai Binh", "Thai Thuy", "Thuy Ha", "789 Duong Thuy Ha", "S303"));
        products.add(new Product("P040", "Nguoi nhan", "TP. Ho Chi Minh", "Quan 1", "Phuong 9", "789 Duong Nguyen Van Nghi", "Tran Van The", "0901234568", "Nam Dinh", "Y Yen", "Y Hoa", "456 Duong Y Hoa", "S207"));
        products.add(new Product("P041", "Nguoi nhan", "Bac Giang", "Yen The", "Xa Yen The", "99 Duong Dong Lac", "Le Thi Hoa", "0987654321", "Hai Phong", "Ngo Quyen", "May To", "22 Hoang Van Thu", "S202"));
        products.add(new Product("P043", "Nguoi gui", "Can Tho", "Ninh Kieu", "An Hoa", "45 Mau Than", "Bui Van Hung", "0917414710", "TP. Ho Chi Minh", "Quan 3", "Phuong 7", "80 Vo Thi Sau", "S103"));
        products.add(new Product("P044", "Nguoi nhan", "Nha Trang", "Loc Tho", "Tan Lap", "30 Le Thanh Ton", "Hoang Van Ai", "0978123456", "Quy Nhon", "Tran Phu", "Hai Cang", "12 Nguyen Hue", "S202"));
        products.add(new Product("P045", "Nguoi gui", "Ha Noi", "Hai Ba Trung", "Thanh Nhan", "456 Duong Le Dai Hanh", "Le Van Hai", "0942326688", "Da Nang", "Lien Chieu", "Hoa Khanh", "886 Duong Hoang Van Thai", "S206"));
        products.add(new Product("P046", "Nguoi gui", "Da Nang", "Lien Chieu", "Hoa Khanh", "886 Duong Hoang Van Thai", "Nguyen Phuc Khang", "0945678901", "Hai Phong", "Le Chan", "Nghia Xa", "50 To Hieu", "S307"));
        products.add(new Product("P047", "Nguoi gui", "Bac Giang", "Yen The", "Xa Yen The", "45 Duong Dong Lac", "Duong Thi Lan", "0957681029", "Hai Phong", "Ngo Quyen", "Le Chan", "So 23A Le Chan", "S203"));
        products.add(new Product("P048", "Nguoi gui", "Can Tho", "Cai Rang", "Ba Lang", "150 Cach Mang Thang Tam", "Le Thi Cuc", "0948719302", "Hue", "Phu Hoi", "Phu Nhuan", "So 6 Phu Nhuan", "S107"));
        products.add(new Product("P049", "Nguoi gui", "Ha Noi", "Long Bien", "Phuong Ngoc Lam", "24 Ngo Tram", "Pham Thi Rong", "0956789012", "Nha Trang", "Van Ninh", "Van Gia", "25 Le Hong Phong", "S207"));
        products.add(new Product("P050", "Nguoi gui", "Hai Phong", "Kien An", "Bac Son", "10 Pham Van Dong", "Hoang Van Khanh", "0987232244", "Ha Nam", "Phu Ly", "Trieu Duong", "26 Duong Trieu Duong", "S203"));         
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public ArrayList<Product> getProductsByUserID(String orderID) {
        ArrayList<Product> orderProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getOrderID().equals(orderID)) {
                orderProducts.add(product);
            }
        }
        return orderProducts;
    }

    public boolean addProduct(Product product) {
        return products.add(product);
    }
}

/*public class ProductBO {
    private ProductDAO productDAO = new ProductDAO();

   // Lấy danh sách các sản phẩm theo UserID
    public ArrayList<Product> getProductsByUserID(String userID) throws SQLException {
        try {
            return productDAO.getProductsByUserID(userID); // Gọi phương thức từ ProductDAO
        } catch (SQLException e) {
            // Xử lý lỗi nếu cần thiết
            System.err.println("Error while fetching products for UserID " + userID + ": " + e.getMessage());
            // Ném lại ngoại lệ để có thể xử lý tại nơi gọi
            throw e;
        }
    }
   // Lấy danh sách các sản phẩm theo UserID
   public ArrayList<Product> getProductsByUserID(String userID) {
       try {
           return productDAO.getProductsByUserID(userID);
       } catch (Exception e) {
           System.err.println("Lỗi trong BO khi lấy danh sách sản phẩm theo UserID: " + e.getMessage());
           e.printStackTrace();
           return new ArrayList<>(); // Trả về danh sách rỗng nếu xảy ra lỗi
       }
   }

   // Lấy danh sách tất cả chi tiết đơn hàng
    public ArrayList<Product> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm chi tiết đơn hàng mới
    public boolean addProduct(Product product) {
        try {
            productDAO.addProduct(product);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
*/
