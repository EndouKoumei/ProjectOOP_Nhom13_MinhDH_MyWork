package model.bo;

import model.bean.ImportExport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ImportExportBO {
    // Dữ liệu tĩnh giả lập thay vì truy cập cơ sở dữ liệu
    private ArrayList<ImportExport> importExports = new ArrayList<>();
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
    public ImportExportBO() {
            importExports.add(new ImportExport("P001", "W007", parseDate("2021-03-19"), parseDate("2021-03-19")));
            importExports.add(new ImportExport("P001", "W028", parseDate("2024-03-19"), parseDate("2024-03-19")));
            importExports.add(new ImportExport("P002", "W029", parseDate("2024-05-28"), parseDate("2024-05-29")));
            importExports.add(new ImportExport("P002", "W032", parseDate("2024-05-29"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P003", "W042", parseDate("2024-05-31"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P003", "W046", parseDate("2024-05-29"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P004", "W013", parseDate("2024-02-14"), parseDate("2024-02-14")));
            importExports.add(new ImportExport("P004", "W018", parseDate("2024-02-14"), parseDate("2024-02-14")));
            importExports.add(new ImportExport("P006", "W030", parseDate("2023-11-05"), parseDate("2023-11-06")));
            importExports.add(new ImportExport("P006", "W062", parseDate("2023-11-07"), parseDate("2023-11-08")));
            importExports.add(new ImportExport("P008", "W007", parseDate("2024-05-26"), parseDate("2024-05-28")));
            importExports.add(new ImportExport("P008", "W048", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P009", "W030", parseDate("2024-05-27"), parseDate("2024-05-28")));
            importExports.add(new ImportExport("P009", "W062", parseDate("2024-05-29"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P010", "W023", parseDate("2021-09-19"), parseDate("2021-09-20")));
            importExports.add(new ImportExport("P010", "W028", parseDate("2021-09-21"), parseDate("2021-09-23")));
            importExports.add(new ImportExport("P011", "W029", parseDate("2024-05-11"), parseDate("2024-05-13")));
            importExports.add(new ImportExport("P011", "W057", parseDate("2024-05-10"), parseDate("2024-05-11")));
            importExports.add(new ImportExport("P012", "W028", parseDate("2022-06-01"), parseDate("2022-06-01")));
            importExports.add(new ImportExport("P012", "W048", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P013", "W007", parseDate("2021-06-02"), parseDate("2021-06-03")));
            importExports.add(new ImportExport("P013", "W019", parseDate("2021-06-03"), parseDate("2021-06-04")));
            importExports.add(new ImportExport("P015", "W024", parseDate("2024-06-01"), null));
            importExports.add(new ImportExport("P016", "W017", parseDate("2023-02-15"), parseDate("2023-02-16")));
            importExports.add(new ImportExport("P016", "W062", parseDate("2023-02-23"), parseDate("2023-02-24")));
            importExports.add(new ImportExport("P017", "W007", parseDate("2024-05-23"), parseDate("2024-05-24")));
            importExports.add(new ImportExport("P017", "W020", parseDate("2024-05-24"), parseDate("2024-05-25")));
            importExports.add(new ImportExport("P019", "W017", parseDate("2024-05-29"), parseDate("2024-05-29")));
            importExports.add(new ImportExport("P019", "W062", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P020", "W018", parseDate("2024-05-29"), parseDate("2024-05-30")));
            importExports.add(new ImportExport("P020", "W035", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P021", "W013", parseDate("2022-09-10"), parseDate("2022-09-11")));
            importExports.add(new ImportExport("P021", "W029", parseDate("2022-09-09"), parseDate("2022-09-09")));
            importExports.add(new ImportExport("P022", "W042", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P024", "W017", parseDate("2023-07-31"), parseDate("2023-08-01")));
            importExports.add(new ImportExport("P024", "W062", parseDate("2023-08-01"), parseDate("2023-08-02")));
            importExports.add(new ImportExport("P026", "W024", parseDate("2024-05-31"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P026", "W048", parseDate("2024-05-30"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P027", "W023", parseDate("2024-05-30"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P027", "W025", parseDate("2024-05-31"), null));
            importExports.add(new ImportExport("P028", "W013", parseDate("2022-06-01"), parseDate("2022-06-01")));
            importExports.add(new ImportExport("P028", "W020", parseDate("2022-06-02"), parseDate("2022-06-02")));
            importExports.add(new ImportExport("P029", "W023", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P029", "W062", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P030", "W007", parseDate("2024-01-14"), parseDate("2024-01-15")));
            importExports.add(new ImportExport("P030", "W025", parseDate("2024-01-13"), parseDate("2024-01-14")));
            importExports.add(new ImportExport("P032", "W002", parseDate("2022-12-25"), parseDate("2022-12-25")));
            importExports.add(new ImportExport("P032", "W018", parseDate("2022-12-25"), parseDate("2022-12-26")));
            importExports.add(new ImportExport("P034", "W024", parseDate("2024-05-30"), parseDate("2024-05-30")));
            importExports.add(new ImportExport("P034", "W031", parseDate("2024-05-31"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P035", "W029", parseDate("2024-05-31"), null));
            importExports.add(new ImportExport("P035", "W028", parseDate("2024-05-29"), parseDate("2024-05-30")));
            importExports.add(new ImportExport("P036", "W029", parseDate("2022-08-31"), parseDate("2022-08-31")));
            importExports.add(new ImportExport("P036", "W057", parseDate("2022-09-02"), parseDate("2022-09-03")));
            importExports.add(new ImportExport("P037", "W013", parseDate("2022-01-02"), parseDate("2022-01-02")));
            importExports.add(new ImportExport("P037", "W017", parseDate("2021-12-31"), parseDate("2021-12-31")));
            importExports.add(new ImportExport("P038", "W029", parseDate("2024-05-31"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P038", "W041", parseDate("2024-05-27"), parseDate("2024-05-28")));
            importExports.add(new ImportExport("P039", "W017", parseDate("2022-05-31"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P039", "W025", parseDate("2024-05-31"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P040", "W023", parseDate("2022-06-07"), parseDate("2022-06-07")));
            importExports.add(new ImportExport("P040", "W062", parseDate("2022-06-04"), parseDate("2024-06-04")));
            importExports.add(new ImportExport("P041", "W007", parseDate("2024-05-31"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P041", "W021", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P043", "W048", parseDate("2024-05-28"), parseDate("2024-05-29")));
            importExports.add(new ImportExport("P043", "W054", parseDate("2024-05-29"), parseDate("2024-05-29")));
            importExports.add(new ImportExport("P044", "W057", parseDate("2024-05-31"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P044", "W060", parseDate("2024-05-29"), parseDate("2024-05-29")));
            importExports.add(new ImportExport("P045", "W018", parseDate("2024-05-28"), parseDate("2024-05-28")));
            importExports.add(new ImportExport("P045", "W062", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P046", "W023", parseDate("2024-06-01"), null));
            importExports.add(new ImportExport("P046", "W062", parseDate("2024-06-01"), parseDate("2024-06-01")));
            importExports.add(new ImportExport("P048", "W048", parseDate("2023-04-07"), parseDate("2023-04-07")));
            importExports.add(new ImportExport("P048", "W063", parseDate("2023-04-11"), parseDate("2023-04-11")));
            importExports.add(new ImportExport("P049", "W018", parseDate("2024-02-14"), parseDate("2024-02-14")));
            importExports.add(new ImportExport("P049", "W057", parseDate("2024-02-19"), parseDate("2024-02-20")));
            importExports.add(new ImportExport("P050", "W020", parseDate("2024-05-30"), parseDate("2024-05-31")));
            importExports.add(new ImportExport("P050", "W022", parseDate("2024-05-29"), parseDate("2024-05-29")));
    }

    // Lấy danh sách tất cả các bản ghi nhập/xuất kho
    public ArrayList<ImportExport> getAllImportExports() {
        return importExports;
    }

    // Thêm bản ghi nhập kho
    public boolean addImport(ImportExport importExport) {
        return importExports.add(importExport);
    }

    // Cập nhật thông tin nhập kho hoặc xuất kho
    public boolean updateExport(ImportExport updatedImportExport) {
        for (ImportExport ie : importExports) {
            if (ie.getOrderID().equals(updatedImportExport.getOrderID()) &&
                ie.getWarehouseID().equals(updatedImportExport.getWarehouseID())) {

                ie.setInboundDate(updatedImportExport.getInboundDate());
                ie.setOutboundDate(updatedImportExport.getOutboundDate());
                return true;
            }
        }
        return false;
    }

    // Xóa bản ghi nhập/xuất kho theo OrderID và WarehouseID
    public boolean deleteImportExport(String orderID, String warehouseID) {
        return importExports.removeIf(ie -> ie.getOrderID().equals(orderID) && ie.getWarehouseID().equals(warehouseID));
    }

    // Lấy bản ghi nhập/xuất kho theo OrderID
    public ArrayList<ImportExport> getImportExportsByOrderID(String orderID) {
        ArrayList<ImportExport> result = new ArrayList<>();
        for (ImportExport ie : importExports) {
            if (ie.getOrderID().equals(orderID)) {
                result.add(ie);
            }
        }
        return result;
    }

    // Lấy bản ghi nhập/xuất kho theo WarehouseID
    public ArrayList<ImportExport> getImportExportsByWarehouseID(String warehouseID) {
        ArrayList<ImportExport> result = new ArrayList<>();
        for (ImportExport ie : importExports) {
            if (ie.getWarehouseID().equals(warehouseID)) {
                result.add(ie);
            }
        }
        return result;
    }
}

/*public class ImportExportBO {
    private ImportExportDAO importExportDAO = new ImportExportDAO();

    // Lấy danh sách tất cả các bản ghi nhập/xuất kho
    public ArrayList<ImportExport> getAllImportExports() {
        try {
            return importExportDAO.getAllImportExports();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm bản ghi nhập kho
    public boolean addImport(ImportExport importExport) {
        try {
            importExportDAO.addImport(importExport);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin xuất kho
    public boolean updateExport(ImportExport importExport) {
        try {
            importExportDAO.updateExport(importExport);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa bản ghi nhập xuất kho
    public boolean deleteImportExport(String orderID, String warehouseID) {
        try {
            importExportDAO.deleteImportExport(orderID, warehouseID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
*/