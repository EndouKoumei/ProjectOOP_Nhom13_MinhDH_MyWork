package projectUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.bean.OrderCreate;
import model.bean.Product;
import model.bo.OrderCreateBO;
import model.bo.OrderDetailsBO;
import model.bo.ProductBO;

import java.util.List;

public class ViewOrderUI extends Application {
    private OrderCreateBO orderCreateBO = new OrderCreateBO();
    private OrderDetailsBO orderDetailsBO = new OrderDetailsBO();
    private ProductBO productBO = new ProductBO();

    private TextArea outputArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quản lý đơn hàng");

        // Tạo menu
        Button btnViewOrders = new Button("Xem tất cả đơn hàng");
        Button btnViewProducts = new Button("Xem tất cả sản phẩm");
        Button btnViewOrderDetails = new Button("Xem chi tiết đơn hàng");
        Button btnSearchByUserID = new Button("Tìm kiếm theo UserID");
        Button btnExit = new Button("Thoát");
        btnViewOrders.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnViewProducts.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnViewOrderDetails.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnSearchByUserID.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnExit.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        btnViewOrders.setPrefWidth(200); btnViewOrders.setPrefHeight(40);
        btnViewProducts.setPrefWidth(200); btnViewProducts.setPrefHeight(40);
        btnViewOrderDetails.setPrefWidth(200); btnViewOrderDetails.setPrefHeight(40);
        btnSearchByUserID.setPrefWidth(200); btnSearchByUserID.setPrefHeight(40);
        btnExit.setPrefWidth(200); btnExit.setPrefHeight(40);
        // Tạo layout cho menu

        VBox menuBox = new VBox(10, btnViewOrders, btnViewProducts, btnViewOrderDetails, btnSearchByUserID, btnExit);
        menuBox.setStyle("-fx-background-color: #808000; -fx-padding: 30px;");
        // Tạo khu vực hiển thị kết quả
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(400);


        // Tạo layout chính
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(menuBox);
        mainLayout.setCenter(outputArea);
        mainLayout.setPadding(new Insets(10));

        // Đăng ký sự kiện cho các nút
        btnViewOrders.setOnAction(e -> displayAllOrders());
        btnViewProducts.setOnAction(e -> displayAllProducts());
        btnViewOrderDetails.setOnAction(e -> displayOrderDetails());
        btnSearchByUserID.setOnAction(e -> searchOrdersByUserID(primaryStage));
        btnExit.setOnAction(e -> primaryStage.close());

        // Hiển thị cửa sổ
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Hiển thị tất cả đơn hàng
    private void displayAllOrders() {
        List<OrderCreate> orders = orderCreateBO.getAllOrders();
        if (orders.isEmpty()) {
            outputArea.setText("Không có đơn hàng nào trong cơ sở dữ liệu.");
        
        } else {
            StringBuilder result = new StringBuilder("Danh sách tất cả đơn hàng:\n");
            for (OrderCreate order : orders) {
                result.append(order).append("\n");
            }
            outputArea.setText(result.toString());
        }
    }

    // Hiển thị tất cả sản phẩm
    private void displayAllProducts() {
        List<Product> products = productBO.getAllProducts();
        if (products.isEmpty()) {
            outputArea.setText("Không có sản phẩm nào trong cơ sở dữ liệu.");
        } else {
            StringBuilder result = new StringBuilder("Danh sách tất cả sản phẩm:\n");
            for (Product product : products) {
                result.append(product).append("\n");
            }
            outputArea.setText(result.toString());
        }
    }

    // Hiển thị danh sách chi tiết đơn hàng
    private void displayOrderDetails() {
        var details = orderDetailsBO.getListOrderDetails();
        if (details.isEmpty()) {
            outputArea.setText("Không có thông tin chi tiết đơn hàng nào.");
        } else {
            StringBuilder result = new StringBuilder("Danh sách chi tiết đơn hàng:\n");
            for (var detail : details) {
                result.append(detail).append("\n");
            }
            outputArea.setText(result.toString());
        }
    }

    // Tìm kiếm đơn hàng theo UserID
    private void searchOrdersByUserID(Stage primaryStage) {
        // Tạo cửa sổ con nhập UserID
        Stage dialog = new Stage();
        dialog.setTitle("Tìm kiếm theo UserID");

        Label label = new Label("Nhập UserID:");
        TextField userIDField = new TextField();
        Button btnSearch = new Button("Tìm kiếm");
        Button btnCancel = new Button("Hủy");

        HBox inputLayout = new HBox(10, label, userIDField);
        HBox buttonLayout = new HBox(10, btnSearch, btnCancel);
        VBox dialogLayout = new VBox(10, inputLayout, buttonLayout);
        dialogLayout.setPadding(new Insets(10));

        // Đăng ký sự kiện
        btnSearch.setOnAction(e -> {
            String userID = userIDField.getText().trim();
            if (userID.isEmpty()) {
                outputArea.setText("Vui lòng nhập UserID.");
            } else {
                List<OrderCreate> orders = orderCreateBO.getOrdersByUserID(userID);
                if (orders.isEmpty()) {
                    outputArea.setText("Không có đơn hàng nào cho UserID: " + userID);
                } else {
                    StringBuilder result = new StringBuilder("Danh sách đơn hàng của UserID " + userID + ":\n");
                    for (OrderCreate order : orders) {
                        result.append(order).append("\n");
                    }
                    outputArea.setText(result.toString());
                }
                dialog.close();
            }
        });

        btnCancel.setOnAction(e -> dialog.close());

        // Hiển thị cửa sổ
        Scene dialogScene = new Scene(dialogLayout, 400, 200);
        dialog.setScene(dialogScene);
        dialog.initOwner(primaryStage);
        dialog.show();
    }
}
