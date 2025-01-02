// MainController.java
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import model.bean.*;
import model.bo.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class MainController {
    private JFrame mainFrame;
    private JTabbedPane tabbedPane;
    private OrderCreateBO orderCreateBO;
    private OrderDetailsBO orderDetailsBO;
    private ProductBO productBO;
    private StatusofproductBO statusBO;
    private ImportExportBO importExportBO; 
    private SendBO sendBO;
    private String userID;
        public OrderDetailsBO getOrderDetailsBO() {
        return orderDetailsBO;
    }

    public ProductBO getProductBO() {
        return productBO;
    }

    public MainController() {
        // Initialize business objects
        orderCreateBO = new OrderCreateBO();
        orderDetailsBO = new OrderDetailsBO();
        productBO = new ProductBO();
        statusBO = new StatusofproductBO();
        importExportBO = new ImportExportBO();
        sendBO = new SendBO();
        // Show initial input dialog to get userID
        requestUserID();
    }

    private void requestUserID() {
        while (true) {
            userID = JOptionPane.showInputDialog(null, "Nhập User ID của bạn:", 
                        "Xác nhận User ID", JOptionPane.QUESTION_MESSAGE);
            if (userID == null || userID.trim().isEmpty()) {
                int choice = JOptionPane.showConfirmDialog(null, 
                    "Bạn chưa nhập User ID. Bạn có muốn thử lại không?",
                    "Lỗi nhập User ID", 
                    JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } else {
                ArrayList<OrderCreate> userOrders = getUserOrders(userID);
                if (userOrders.isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(null,
                        "Không tìm thấy đơn hàng nào cho User ID này. Bạn có muốn thử lại với ID khác không?",
                        "Không tìm thấy đơn hàng",
                        JOptionPane.YES_NO_OPTION);
                    if (choice != JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else {
                    initializeGUI();
                    break;
                }
            }
        }
    }

    private ArrayList<OrderCreate> getUserOrders(String userId) {
        ArrayList<OrderCreate> allOrders = orderCreateBO.getAllOrders();
        ArrayList<OrderCreate> userOrders = new ArrayList<>();
        
        for (OrderCreate order : allOrders) {
            if ((userId.equals(order.getGiverID()) && order.getGiverID() != null) || 
                (userId.equals(order.getReceiverID()) && order.getReceiverID() != null)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    private void initializeGUI() {
        mainFrame = new JFrame("Quản lý đơn hàng - User ID: " + userID);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create panels for each status
        createStatusPanel("Chờ xác nhận", "PENDING");
        createStatusPanel("Đang giao", "DELIVERING");
        createStatusPanel("Đã giao", "DELIVERED");
        createStatusPanel("Hủy", "CANCELLED");
        
        mainFrame.add(tabbedPane);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void createStatusPanel(String tabTitle, String status) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table model and table for orders
        OrderTableModel tableModel = new OrderTableModel();
        JTable orderTable = new JTable(tableModel);
        
        // Add button columns
        TableColumn detailsColumn = orderTable.getColumnModel().getColumn(5);  // Column index for first button
        detailsColumn.setCellRenderer(new ButtonRenderer("Xem sản phẩm trong đơn"));
        detailsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), this, "products"));

        TableColumn productsColumn = orderTable.getColumnModel().getColumn(6);  // Column index for second button
        productsColumn.setCellRenderer(new ButtonRenderer("Xem chi tiết đơn hàng"));
        productsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), this, "details"));
        
        TableColumn trackingColumn = orderTable.getColumnModel().getColumn(7);
        trackingColumn.setCellRenderer(new ButtonRenderer("Xem thông tin vận chuyển"));
        trackingColumn.setCellEditor(new ButtonEditor(new JCheckBox(), this, "tracking"));
        
        // Add components to panel
        JScrollPane scrollPane = new JScrollPane(orderTable);
        panel.add(scrollPane, BorderLayout.CENTER);
      
        // Add to tabbed pane
        tabbedPane.addTab(tabTitle, panel);
        
        // Load initial data
        updateOrderList(status, tableModel);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String orderId;
        private MainController controller;
        private String actionType; // "products", "details", or "tracking"
    
        public ButtonEditor(JCheckBox checkBox, MainController controller, String actionType) {
            super(checkBox);
            this.controller = controller;
            this.actionType = actionType;
            this.button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            orderId = (String)table.getValueAt(row, 0); // Get OrderID from first column
            switch (actionType) {
                case "products":
                    button.setText("Xem sản phẩm trong đơn");
                    break;
                case "details":
                    button.setText("Xem chi tiết đơn hàng");
                    break;
                case "tracking":
                    button.setText("Xem thông tin vận chuyển");
                    break;
            }
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            switch (actionType) {
                case "products":
                    ArrayList<OrderDetails> details = controller.getOrderDetailsBO().getOrderDetailsByOrderID(orderId);
                    if (details != null && !details.isEmpty()) {
                        controller.showDetailsDialog(details);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm trong đơn hàng!");
                    }
                    break;
                case "details":
                    ArrayList<Product> products = controller.getProductBO().getProductsByUserID(orderId);
                    if (products != null && !products.isEmpty()) {
                        controller.showProductsDialog(products);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết đơn hàng!");
                    }
                    break;
                case "tracking":
                    controller.showTrackingDialog(orderId);
                    break;
            }
            return null;
        }
    }
    
    private void showTrackingDialog(String orderID) {
        // Get order status
        String orderStatus = getOrderStatus(orderID);
        if (orderStatus == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin đơn hàng!");
            return;
        }
        switch (orderStatus) {
            case "PENDING":
                JOptionPane.showMessageDialog(null, "Đơn hàng đang chờ xác nhận!");
                break;
            case "CANCELLED":
                JOptionPane.showMessageDialog(null, "Đơn hàng đã bị hủy!");
                break;
            case "DELIVERING":
            case "DELIVERED":
                showWarehouseTrackingDialog(orderID, orderStatus);
                break;
        }        
    }


    private String getOrderStatus(String orderId) {
        ArrayList<Statusofproduct> statuses = statusBO.getAllStatusofproduct();
        for (Statusofproduct status : statuses) {
            if (status.getOrderID().equals(orderId)) {
                return status.getOrderStatus();
            }
        }
        return null;
    }

    private void showWarehouseTrackingDialog(String orderId, String orderStatus) {
        JDialog dialog = new JDialog(mainFrame, "Theo dõi vận chuyển đơn hàng: " + orderId, true);
        dialog.setLayout(new BorderLayout());
        
        // Get warehouse tracking information
        ArrayList<ImportExport> trackingInfo = importExportBO.getImportExportsByOrderID(orderId);
        trackingInfo.sort(Comparator.comparing(ImportExport::getInboundDate));

        // Create table model for warehouse tracking
        String[] columnNames = {"Kho hàng", "Ngày vào kho", "Ngày xuất kho"};
        Object[][] data = new Object[trackingInfo.size()][3];
        
        for (int i = 0; i < trackingInfo.size(); i++) {
            ImportExport tracking = trackingInfo.get(i);
            data[i][0] = tracking.getWarehouseID();
            data[i][1] = tracking.getInboundDate();
            data[i][2] = tracking.getOutboundDate();
        }

        JTable trackingTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(trackingTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // For delivered orders, add shipping information
        if (orderStatus.equals("DELIVERED")) {
            ArrayList<Send> sendInfo = sendBO.getSendByOrderID(orderId);
            if (!sendInfo.isEmpty()) {
                Send shipment = sendInfo.get(0);
                
                JPanel shipmentPanel = new JPanel();
                shipmentPanel.setLayout(new BoxLayout(shipmentPanel, BoxLayout.Y_AXIS));
                shipmentPanel.setBorder(BorderFactory.createTitledBorder("Thông tin giao hàng"));
                
                JLabel shipperLabel = new JLabel("Mã shipper: " + shipment.getEmployeeID());
                JLabel receiptLabel = new JLabel("Ngày nhận hàng: " + shipment.getReceiptDate());
                JLabel estimatedLabel = new JLabel("Ngày dự kiến giao: " + shipment.getEstimatedDate());
                JLabel actualLabel = new JLabel("Ngày thực giao: " + shipment.getActualDate());
                JLabel statusLabel = new JLabel("Trạng thái: " + 
                    (shipment.getSendStatus() == null ? "Hủy đơn" : 
                     shipment.getSendStatus().equals("DELIVERED") ? "Thành công" : "Hoàn trả"));

                shipmentPanel.add(shipperLabel);
                shipmentPanel.add(receiptLabel);
                shipmentPanel.add(estimatedLabel);
                shipmentPanel.add(actualLabel);
                shipmentPanel.add(statusLabel);

                dialog.add(shipmentPanel, BorderLayout.SOUTH);
            }
        }

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

    private void showDetailsDialog(ArrayList<OrderDetails> details) {
        JDialog dialog = new JDialog(mainFrame, "Sản phẩm trong đơn", true);
        dialog.setLayout(new BorderLayout());
        
        // Create table model and table for details
        OrderDetailsTableModel tableModel = new OrderDetailsTableModel(details);
        JTable detailsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(detailsTable);
        
        dialog.add(scrollPane);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
    private void showProductsDialog(ArrayList<Product> products) {
        JDialog dialog = new JDialog(mainFrame, "Chi tiết đơn hàng", true);
        dialog.setLayout(new BorderLayout());
        
        // Tạo danh sách các panel chứa dữ liệu sản phẩm
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Xếp chồng các sản phẩm theo chiều dọc
        
        for (Product product : products) {
            JTextArea productInfoArea = new JTextArea();
            productInfoArea.setText(String.format(
                "ID đơn hàng: %s\nBên thanh toán: %s\nĐịa chỉ giao: %s, %s, %s, %s\n" +
                "Địa chỉ nhận: %s, %s, %s, %s\nTên người nhận: %s\nSĐT người nhận: %s\nDịch vụ giao hàng: %s\n",
                product.getOrderID(), 
                product.getPayer(),
                product.getPickupAddress(), product.getPickupWard(), product.getPickupDistrict(), product.getPickupCity(),
                product.getDeliveryAddress(), product.getDeliveryWard(), product.getDeliveryDistrict(), product.getDeliveryCity(),
                product.getRecipientName(), 
                product.getPhoneRecipient(), 
                product.getServiceID()
            ));
            productInfoArea.setEditable(false); // Không cho phép chỉnh sửa
            productInfoArea.setWrapStyleWord(true);
            productInfoArea.setLineWrap(true);
            productInfoArea.setFont(new Font("Arial", Font.BOLD, 14));
            productInfoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Đặt JTextArea trong một JPanel với viền
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            productPanel.add(productInfoArea, BorderLayout.CENTER);
            
            listPanel.add(productPanel);
        }
        
        // Bọc danh sách panel trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(listPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
    
    private void updateOrderList(String status, OrderTableModel tableModel) {
        ArrayList<OrderCreate> userOrders = getUserOrders(userID);
        ArrayList<Statusofproduct> statuses = statusBO.getAllStatusofproduct();
        ArrayList<OrderCreate> filteredOrders = new ArrayList<>();
        
        for (OrderCreate order : userOrders) {
            for (Statusofproduct statusObj : statuses) {
                if (statusObj.getOrderID().equals(order.getOrderID()) && 
                    statusObj.getOrderStatus().equals(status)) {
                    filteredOrders.add(order);
                    break;
                }
            }
        }
        
        tableModel.setOrders(filteredOrders);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
    }
}

class OrderTableModel extends AbstractTableModel {
    private ArrayList<OrderCreate> orders = new ArrayList<>();
    private final String[] columnNames = {"ID đơn hàng", "ID người gửi", "ID người nhận", "Ngày đặt", "Giá trị đơn hàng", "Sản phẩm", "Chi tiết", "Vận chuyển"};
    private OrderDetailsBO orderDetailsBO = new OrderDetailsBO(); // Để tính giá trị đơn hàng
    private DecimalFormat decimalFormat = new DecimalFormat("#0.000"); // Định dạng số

    public void setOrders(ArrayList<OrderCreate> orders) {
        this.orders = orders;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return orders.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderCreate order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0: return order.getOrderID();
            case 1: return order.getGiverID();
            case 2: return order.getReceiverID();
            case 3: return order.getOrderDate();
            case 4: 
                // Tính tổng giá trị đơn hàng
                ArrayList<OrderDetails> details = orderDetailsBO.getOrderDetailsByOrderID(order.getOrderID());
                if (details != null) {
                    double totalValue = details.stream()
                                               .mapToDouble(OrderDetails::getItemPrice)
                                               .sum();
                    return decimalFormat.format(totalValue); // Định dạng số trước khi trả về
                } else {
                    return decimalFormat.format(0.0); // Trường hợp không có sản phẩm
                }
            case 5:
            case 6: return "";
            default: return null;
        }
    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return column >= 5 && column <= 7; // Make button columns editable
    }
}

// OrderDetailsTableModel.java
class OrderDetailsTableModel extends AbstractTableModel {
    private ArrayList<OrderDetails> details;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.000"); // Định dạng số

    private final String[] columnNames = {"ID Sản phẩm", "ID đơn hàng", "Tên sản phẩm", 
                                        "ID phụ phí", "Cân nặng", "Giá"};
    
    public OrderDetailsTableModel(ArrayList<OrderDetails> details) {
        this.details = details;
    }
    
    @Override
    public int getRowCount() {
        return details.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderDetails detail = details.get(rowIndex);
        switch (columnIndex) {
            case 0: return detail.getItemID();
            case 1: return detail.getOrderID();
            case 2: return detail.getItemName();
            case 3: return detail.getSurchargeID();
            case 4: return detail.getWeight();
            case 5: return decimalFormat.format(detail.getItemPrice());
            default: return null;
        }
    }
}
