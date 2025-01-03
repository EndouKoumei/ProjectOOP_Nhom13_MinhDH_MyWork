create database gvproject;
use gvproject;
#drop database qlgv;
# tao bang kho
CREATE TABLE Warehouse (
    WarehouseID CHAR(4) PRIMARY KEY,
    WareName VARCHAR(30) NOT NULL,
    City VARCHAR(40) NOT NULL,
    District VARCHAR(30),
    Ward VARCHAR(30),
    Address VARCHAR(30)
);

#tao bang dich vu
CREATE TABLE Service
(
    ServiceID   CHAR(5) PRIMARY KEY,
    ServiceName VARCHAR(20),
    Price       DECIMAL(12, 3) not null,
    MaxDistance SMALLINT
);
alter table Service
    modify MaxDistance char(6);

# tao bang don hang
CREATE TABLE Product
(
    OrderID          CHAR(5) PRIMARY KEY,
    #   Total DECIMAL(12,3) not null,
    Payer            VARCHAR(15) not null,
    PickupCity       VARCHAR(50) not null,
    PickupDistrict   VARCHAR(30),
    PickupWard       VARCHAR(30),
    PickupAddress    VARCHAR(45) not null,
    RecipientName    VARCHAR(30) not null,
    PhoneRecipient   VARCHAR(15) not null,
    DeliveryCity     VARCHAR(50),
    DeliveryDistrict VARCHAR(45),
    DeliveryWard     VARCHAR(45),
    DeliveryAddress  VARCHAR(45) not null,
    #    CurrentWarehouseID CHAR(5) default 'K000',
    #   OrderStatus VARCHAR(20) default 'Dang xu ly',
    ServiceID        CHAR(5)
);
#them constraint cho ServiceID
ALTER TABLE Product
    ADD CONSTRAINT Service_ID
        FOREIGN KEY (ServiceID) REFERENCES Service (ServiceID)
            on delete cascade
            on update cascade;

#bang nhap kho / xuat kho
CREATE TABLE ImportExport
(
    OrderID      CHAR(5),
    WarehouseID  CHAR(5),
    InboundDate  DATE not null,
    OutboundDate DATE default NUll,
    constraint ID primary key (WarehouseID, OrderID),
    constraint Warehouse_ID foreign key (WarehouseID) REFERENCES Warehouse (WarehouseID)
        on delete cascade
        on update cascade,
    constraint OrderID foreign key (OrderID) REFERENCES Product (OrderID)
        on delete cascade
        on update cascade
);

# bang nguoi dung
CREATE TABLE AccUser
(
    UserID     CHAR(5) PRIMARY KEY,
    Passwordd   CHAR(20), 
    LastName   VARCHAR(10),
    MiddleName VARCHAR(10),
    FirstName  VARCHAR(10)        not null,
    Birthday   DATE,
    Gender     CHAR(3),
    Phone      VARCHAR(10) unique not null,
    City       VARCHAR(30),
    District   VARCHAR(45),
    Ware       VARCHAR(45),
    Address    VARCHAR(45)
);

#bang tao don hang
CREATE TABLE OrderCreate
(
    OrderID   CHAR(5),
    GiverID   CHAR(5),
    ReciverID char(5),
    OrderDate DATE not null,
    constraint primary key (GiverID, OrderID),
    constraint foreign key (GiverID) REFERENCES AccUser (UserID)
        on delete cascade
        on update cascade,
    constraint foreign key (OrderID) REFERENCES Product (OrderID)
        on delete cascade
        on update cascade
);


# Tao trigger de ngan khi add du lieu vao 2 cot reciverID = OrderID
#DROP TRIGGER before_insert_OrderCreate;
DELIMITER $$
CREATE TRIGGER before_insert_OrderCreate
    BEFORE insert
    ON OrderCreate
    for each row
BEGIN
    IF new.GiverID = new.ReciverID
    then
        signal SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Nguoi gui khong the tu dat don hang cua minh!';
    end if;
END $$
DELIMITER ;

#DROP TRIGGER before_update_OrderCreate
DELIMITER $$
CREATE TRIGGER before_update_OrderCreate
    BEFORE update
    ON OrderCreate
    for each row
BEGIN
    IF new.GiverID = old.ReciverID or old.GiverID = new.ReciverID or new.GiverID = new.ReciverID
    then
        signal SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Nguoi gui khong the tu dat don hang cua minh!';
    end if;
END $$
DELIMITER ;

#Tao bang shipper
CREATE TABLE Shipper
(
    EmployeeID CHAR(5) PRIMARY KEY,
    LastName   VARCHAR(10),
    MiddleName VARCHAR(10),
    FirstName  VARCHAR(10)        not null,
    Gender     CHAR(6),
    Birthday   DATE,
    Phone      VARCHAR(10) unique not null,
    HomeTown   VARCHAR(45)        not null
);
#desc Shipper;
#Tao bang giao hang
CREATE TABLE Send
(
    EmployeeID    CHAR(5),
    OrderID       CHAR(5),
    ReceiptDate   DATE,        #ngay nhan don
    EstimatedDate DATE,        # ngay giao hang du kien = ngay nhan don + 4
    ActualDate    DATE,        # ngay giao hang thuc te - tu nhap
    SendStatus    varchar(30), #Trang thai don giao - tu nhap
    constraint SendID primary key (EmployeeID, OrderID),
    constraint Employee_ID foreign key (EmployeeID) REFERENCES Shipper (EmployeeID)
        on delete cascade
        on update cascade,
    constraint foreign key (OrderID) REFERENCES Product (OrderID)
        on delete cascade
        on update cascade
);
#desc Send;
#Tao bang dich vu
create table Surcharge
(
                           SurchargeID char(5) primary key,
                           SurchargeName varchar(20),
                           Price decimal(5,2)
);
#desc Surcharge;

#Tao bang chi tiet don hang
create table OrderDetails (
                              ItemID char(10),
                              OrderID char(5),
                              ItemName varchar(30) not null,
                              SurchargeID varchar(20),
                              Weight decimal(18,2) not null,
                              constraint DetailsID primary key (ItemID, OrderID),
                              constraint foreign key (OrderID) REFERENCES Product(OrderID)
                                  on delete cascade
                                  on update cascade,
                              constraint foreign key (SurchargeID) REFERENCES Surcharge(SurchargeID)
                                  on delete cascade
                                  on update cascade
);
alter table OrderDetails
add itemprice decimal(20,3);

alter table orderdetails
drop primary key;
alter table orderdetails
add primary key(ItemID);
#desc orderdetails;

# Trigger for Statusofproduct
create table Statusofproduct(
                                OrderID CHAR(5) PRIMARY KEY,
                                CurrentWarehouseID CHAR(5),
                                OrderStatus VARCHAR(30)
);
select * from Statusofproduct;

#DROP TRIGGER after_create_product;
# mỗi khi tạo một đơn hàng trong product sẽ thêm vao bảng Statusofproduct các thông tin dưới đây
DELIMITER $$
CREATE TRIGGER after_create_product
    AFTER INSERT ON product
    for each row
BEGIN
    INSERT INTO Statusofproduct
    SET OrderID = new.OrderID,
           CurrentWarehouseID = '0000',
           OrderStatus = 'Dang xu ly';
    END$$
    DELIMITER ;

#drop TRIGGER after_delete_product;
#khi don hang bi xoa di khoi bang product -> update OrderStatus = 'don hang da bi huy'
DELIMITER $$
    CREATE TRIGGER after_delete_product
        AFTER delete ON product
        for each row
    BEGIN
        UPDATE Statusofproduct as sta
        SET OrderStatus = 'Don hang da bi huy'
        where OLD.OrderID = sta.OrderID and sta.CurrentWarehouseID = '0000';
        END$$
        DELIMITER ;

#Drop TRIGGER after_insert_Warehouse;
# mỗi khi thêm vào importexport thì update bảng statusofproduct
DELIMITER $$
        CREATE TRIGGER after_insert_Warehouse
            AFTER INSERT ON importexport
            FOR EACH ROW
        BEGIN
            IF new.OutboundDate is not null
    THEN
            UPDATE Statusofproduct as Sta
            SET CurrentWarehouseID = NEW.WarehouseID,
                OrderStatus = 'Da roi kho'
            WHERE NEW.OrderID = Sta.OrderID;
            ELSE
            UPDATE Statusofproduct as Sta
            SET CurrentWarehouseID = NEW.WarehouseID,
                OrderStatus = 'Dang trong kho'
            WHERE NEW.OrderID = Sta.OrderID;
        end if;
        END$$
        DELIMITER ;

#DROP TRIGGER after_updateexportdate_Warehouse;
#MOI KHI UPDATE VAO importexport, ExportDate != NULL -> update Statusofproduct
DELIMITER $$
        CREATE TRIGGER after_updateexportdate_Warehouse
            AFTER Update   ON importexport
            FOR EACH ROW
        BEGIN
            IF new.OutboundDate is not null
    THEN
            UPDATE Statusofproduct as Sta
            SET OrderStatus = 'Da roi kho'
            WHERE NEW.OrderID = Sta.OrderID;
        end if;
        END$$
        DELIMITER ;

#DROP TRIGGER after_insert_send;
# moi khi insert vao bang send thi update bang statusofproduct
DELIMITER $$
        CREATE TRIGGER after_insert_send
            AFTER INSERT ON send
            FOR EACH ROW
        BEGIN
            IF NEW.SendStatus IS NULL
    THEN
            UPDATE Statusofproduct as sta
            SET CurrentWarehouseID = 'Done',
                OrderStatus = 'Dang giao hang'
            WHERE NEW.OrderID = sta.OrderID;
            ELSE
            UPDATE Statusofproduct as sta
            SET CurrentWarehouseID = 'Done',
                OrderStatus = NEW.SendStatus
            WHERE NEW.OrderID = sta.OrderID;
        end if;
        END$$
        DELIMITER ;

#Mỗi khi update mà actualdate != null thì hàng đã giao thành công
DELIMITER $$
        CREATE TRIGGER after_update_send
            AFTER UPDATE ON send
            FOR EACH ROW
        BEGIN
            IF OLD.SendStatus <> NEW.SendStatus THEN
            UPDATE Statusofproduct as sta
            SET OrderStatus = NEW.SendStatus
            where NEW.OrderID = sta.OrderID;
        end if;
        END$$
        DELIMITER ;
#select * from Statusofproduct;
# trigger do chan tao
-- Thêm phần này vào cuối file gv nhá
-- Tạo bảng log để lưu trữ thông tin cập nhật của Warehouse
CREATE TABLE WarehouseLog (
                                      LogID INT AUTO_INCREMENT PRIMARY KEY,
                                      WarehouseID CHAR(4),
                                      OldWareName VARCHAR(30),
                                      NewWareName VARCHAR(30),
                                      OldCity VARCHAR(40),
                                      NewCity VARCHAR(40),
                                      OldDistrict VARCHAR(30),
                                      NewDistrict VARCHAR(30),
                                      OldWard VARCHAR(30),
                                      NewWard VARCHAR(30),
                                      OldAddress VARCHAR(30),
                                      NewAddress VARCHAR(30),
                                      ChangeTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );

-- Tạo trigger để lưu trữ thông tin cập nhật vào bảng log sau mỗi lần cập nhật bảng Warehouse
DELIMITER //

        CREATE TRIGGER after_warehouse_update
            AFTER UPDATE ON Warehouse
            FOR EACH ROW
        BEGIN
            INSERT INTO WarehouseLog (WarehouseID, OldWareName, NewWareName, OldCity, NewCity, OldDistrict, NewDistrict, OldWard, NewWard, OldAddress, NewAddress)
            VALUES (OLD.WarehouseID, OLD.WareName, NEW.WareName, OLD.City, NEW.City, OLD.District, NEW.District, OLD.Ward, NEW.Ward, OLD.Address, NEW.Address);
        END //
DELIMITER ;

INSERT INTO AccUser (UserID, Passwordd, LastName, MiddleName, FirstName, Birthday, Gender, Phone, City, District, Ware, Address) VALUES
('U001', '123456','Le', 'Thi', 'Cuc', '2003-03-25', 'Nu', '0948719302', 'Hue', 'Phu Hoi', 'Phu Nhuan', 'So 6 Phu Nhuan'),
('U002', '123456','Ngo', 'Van', 'Khoa', '2003-02-04', 'Nam', '0993028471', 'Quang Tri', 'Hai Lang', 'Thi tran Lang Co', '723 Duong Truong Son'),
('U003', '123456','Tran', 'Van', 'Quang', '2007-12-01', 'Nam', '0965791834', 'Ha Noi', 'Hai Ba Trung', 'Thanh Nhan', '456 Duong Le Dai Hanh'),
('U004', '123456','Pham', 'Van', 'Dung', '1975-04-14', 'Nam', '0920475186', 'TP. Ho Chi Minh', 'Binh Thanh', 'Phuong 11', '123 Duong D2'),
('U005', '123456','Bui', 'Van', 'Minh', '1981-10-23', 'Nam', '0931867249', 'Bac Giang', 'Yen The', 'Xa Yen The', '45 Duong Dong Lac'),
('U006', '123456','Nguyen', 'Van', 'An', '2001-01-12', 'Nam', '0952938416', 'Ha Noi', 'Hoan Kiem', 'Phuong Trang Tien', '723 Pho Hue'),
('U007', '123456','Vo', 'Thi', 'Mai', '2000-03-25', 'Nu', '0986432079', 'Thai Nguyen', 'Thanh pho Thai Nguyen', 'Phuong Quang Vinh', '449 Duong Quang Trung'),
('U008', '123456','Đo', 'Thi', 'Quynh', '1995-07-15', 'Nu', '0975098134', 'Ha Noi', 'Hoan Kiem', 'Phuong Hang Bai', '875 Pho Trang Tien'),
('U009', '123456','Tran', 'Thi', 'Bich', '2002-02-16', 'Nu', '0969273815', 'TP. Ho Chi Minh', 'Quan 1', 'Phuong 9', '789 Duong Nguyen Van Nghi'),
('U010', '123456','Hoang', 'Van', 'Phong', '1992-07-10', 'Nam', '0947162930', 'TP. Ho Chi Minh', 'Go Vap', 'Phuong Phu Nhuan', '185 Hung Vuong'),
            #11-20
('U011', '123456','Le', 'Thi', 'Hanh', '1984-01-02', 'Nu', '0920395847', 'Tien Giang', 'Cai Lay', 'Thi tran Cai Lay', '123 Duong My Thanh An'),
('U012', '123456','Vo', 'Thi', 'Em', '2002-12-05', 'Nu', '0918937460', 'Can Tho', 'Cai Rang', 'An Khanh', 'So 14 An Khanh'),
('U013', '123456','Duong', 'Thi', 'Lan', '1997-09-25', 'Nu', '0957681029', 'Hai Phong', 'Ngo Quyen', 'Le Chan', 'So 23A Le Chan'),
('U014', '123456','Tran', 'Van', 'Minh', '1996-09-22', 'Nam', '0949302781', 'Lao Cai', 'Huyen Sa Pa', 'Xa Sa Pa', '333 Duong Phan Xi Pang'),
('U015', '123456','Nguyen', 'Thi', 'Hong', '2007-11-05', 'Nu', '0972839415', 'Ha Noi', 'Long Bien', 'Phuong Ngoc Lam', '24 Ngo Tram'),
('U016', '123456','Nguyen', 'Thi', 'Lan', '1990-05-16', 'Nu', '0906946044', 'Da Nang', 'Hai Chau', 'Thuan Phuoc', 'So 45 Thuan Phuoc'),
('U017', '123456','Tran', 'Van', 'Son', '1985-11-02', 'Nam', '0961742337', 'Hai Phong', 'Le Chan', 'An Duong', '108 Duong An Duong'),
('U018', '123456','Pham', 'Thi', 'Hue', '1992-08-23', 'Nu', '0987396094', 'Can Tho', 'Ninh Kieu', 'An Hoa', '294 Duong 3/2'),
('U019', '123456','Le', 'Van', 'Hai', '1988-04-10', 'Nam', '0942326688', 'Da Nang', 'Lien Chieu', 'Hoa Khanh', '886 Duong Hoang Van Thai'),
('U020', '123456','Do', 'Thi', 'Linh', '2008-09-14', 'Nu', '0958885686', 'Ha Noi', 'Ba Dinh', 'Ngoc Ha', '035 Duong Hoang Hoa Tham'),
#21-30
('U021', '123456','Hoang', 'Van', 'Tuan', '1991-03-21', 'Nam', '0975079386', 'Hue', 'Phu Vang', 'Phu An', '632 Duong Phu An'),
('U022', '123456','Vu', 'Thi', 'Thu', '1996-12-30', 'Nu', '0994725645', 'Quang Ninh', 'Ha Long', 'Bai Chay', '313 Duong Ha Long'),
('U023', '123456','Ly', 'Van', 'Thanh', '2003-07-25', 'Nam', '0960372201', 'Nam Dinh', 'Nam Truc', 'Truc Hung', '505 Duong Truc Hung'),
('U024', '123456','Ngo', 'Thi', 'Mai', '1993-02-18', 'Nu', '0990451077', 'Hai Duong', 'Cam Giang', 'Cam Phuc', '206 Duong Cam Phuc'),
('U025', '123456','Bui', 'Van', 'Hung', '1987-10-05', 'Nam', '0917414710', 'Bac Giang', 'Yen The', 'Xa Yen The', '99 Duong Dong Lac'),
('U026', '123456','Nguyen', 'Van', 'Hung', '1990-06-12', 'Nam', '0955932843', 'Thanh Hoa', 'Hoang Hoa', 'Hoang Duc', '89 Duong Hoang Duc'),
('U027', '123456','Tran', 'Thi', 'Hanh', '2001-03-22', 'Nu', '0901064092', 'Phu Tho', 'Viet Tri', 'Tien Cat', '86 Duong Tien Cat'),
('U028', '123456','Pham', 'Van', 'Luan', '1994-11-11', 'Nam', '0917649751', 'Quang Binh', 'Dong Hoi', 'Hai Dinh', '117 Duong Hai Dinh'),
('U029', '123456','Le', 'Thi', 'Dao', '1997-08-19', 'Nu', '0923333218', 'Ninh Binh', 'Ninh Hoa', 'Hoa Lu', '19 Duong Hoa Lu'),
('U030', '123456','Hoang', 'Van', 'Khanh', '1985-04-14', 'Nam', '0987232244', 'Ha Nam', 'Phu Ly', 'Trieu Duong', '26 Duong Trieu Duong'),
('U031', '123456','Do', 'Thi', 'Xuan', '2003-09-10', 'Nu', '0970892815', 'Nghe An', 'Vinh', 'Hung Dung', '444 Duong Hung Dung'),
('U032', '123456','Vu', 'Van', 'Tuan', '1990-12-29', 'Nam', '0907113310', 'Quang Ngai', 'Son Tinh', 'Tinh Son', '176 Duong Tinh Son'),
('U033', '123456','Nguyen', 'Thi', 'Lan', '1992-11-16', 'Nu', '0981902291', 'Bac Giang', 'Hiep Hoa', 'Thang', '505 Duong Thang'),
('U034', '123456','Tran', 'Van', 'Kiet', '1988-05-18', 'Nam', '0974627239', 'Binh Duong', 'Di An', 'Tan Dong Hiep', '845 Duong Tan Dong Hiep'),
('U035', '123456','Pham', 'Thi', 'Hong', '1995-07-25', 'Nu', '0939109692', 'Ben Tre', 'Chau Thanh', 'Tam Phuoc', '41 Duong Tam Phuoc');

        #select * from accuser;
#Cái dưới này để nếu sai thì xóa nhập lại thui
#delete from accuser
#where userID like "U%";
#-----------------------------------------------------------
        #Insert du lieu Shipper
INSERT INTO Shipper (EmployeeID, LastName, MiddleName, FirstName, Gender, Birthday, Phone, HomeTown)
VALUES
  ('SP001', 'Nguyen', 'Van', 'Anh', 'Male', '1990-07-15', '0987123456', 'Ha Noi'),
  ('SP002', 'Tran', 'Thi', 'Binh', 'Female', '1995-03-02', '0912345678', 'Ho Chi Minh'),
  ('SP003', 'Le', 'Hong', 'Cuong', 'Male', '1988-11-10', '0905678901', 'Da Nang'),
  ('SP004', 'Pham', 'Thi', 'Dung', 'Female', '1992-09-25', '0978234567', 'Ho Chi Minh'),
  ('SP005', 'Hoang', 'Minh', 'Duc', 'Male', '1993-05-08', '0943567890', 'Ha Noi'),
  ('SP006', 'Tran', 'Van', 'Hai', 'Male', '1991-12-12', '0966789012', 'Ho Chi Minh'),
  ('SP007', 'Hoang', 'Thu', 'Ha', 'Female', '1994-09-03', '0934012345', 'Can Tho'),
  ('SP008', 'Le', 'Thi', 'Kim Anh', 'Female', '1996-06-18', '0956345678', 'Ho Chi Minh'),
  ('SP009', 'Chu', 'Thi', 'Mai', 'Female', '1990-04-29', '0923678901', 'Can Tho'),
  ('SP010', 'Le', 'Van', 'Quang', 'Male', '1986-11-07', '0999901234', 'Ho Chi Minh'),
  ('SP011', 'Tran', 'Quoc', 'Anh', 'Male', '1989-12-11', '0907234567', 'Ha Noi'),
  ('SP012', 'Pham', 'Minh', 'Nhat', 'Male', '1994-05-01', '0945567890', 'Ho Chi Minh'),
  ('SP013', 'Lai', 'Thien', 'Loc', 'Male', '1997-09-14', '0988789012', 'Can Tho'),
  ('SP014', 'Nguyen', 'Trung', 'Dung', 'Male', '1998-03-05', '0919012345', 'Ho Chi Minh'),
  ('SP015', 'Dinh', 'Van', 'Hung', 'Male', '1996-11-19', '0967345678', 'Can Tho'),
  ('SP016', 'Le', 'Quoc', 'Huy', 'Male', '1993-08-27', '0935678901', 'Ho Chi Minh'),
  ('SP017', 'Tran', 'Dinh', 'Nhat Minh', 'Male', '1999-06-14', '0972901234', 'Ho Chi Minh'),
  ('SP018', 'Vu', 'Thi', 'Quynh', 'Female', '1987-02-03', '0909234567', 'Ho Chi Minh'),
  ('SP019', 'Le', 'Minh', 'Khue', 'Female', '1984-06-06', '0946567890', 'Can Tho'),
  ('SP020', 'Vu', 'Manh', 'Quan', 'Male', '1992-12-17', '0969789012', 'Ha Nam');


#insert du lieu phan loai hang
insert into Surcharge (SurchargeID, SurchargeName, Price)
values
    ('SU001', 'Hang thong thuong', '0.00'),
    ('SU002', 'Hang gia tri cao', '0.01'),
    ('SU003', 'Hang de vo', '0.1'),
    ('SU004', 'Hang cong kenh', '0.2'),
    ('SU005', 'Chat long', '0.08'),
    ('SU006', 'Hang dong lanh', '0.1'),
    ('SU007', 'Hang dien tu', '0.1');
#select * from Surcharge;
#Xoa du lieu neu can
#delete from surcharge
#where surchargeID like "SU%";
---------------------------------------------------------------
#Insert Service
INSERT INTO Service (ServiceID, ServiceName, MaxDistance, Price)
VALUES
    ('S101', 'Economy', '50', 10000),
    ('S102', 'Economy', '100', 18000),
    ('S103', 'Economy', '200', 22000),
    ('S104', 'Economy', '300', 28000),
    ('S105', 'Economy', '500', 34000),
    ('S106', 'Economy', '800', 40000),
    ('S107', 'Economy', '>800', 47000),
    ('S201', 'Fast', '50', 15000),
    ('S202', 'Fast', '100', 23000),
    ('S203', 'Fast', '200', 29000),
    ('S204', 'Fast', '300', 35000),
    ('S205', 'Fast', '500', 41000),
    ('S206', 'Fast', '800', 47000),
    ('S207', 'Fast', '>800', 54000),
    ('S301', 'Express', '50', 35000),
    ('S302', 'Express', '100', 49000),
    ('S303', 'Express', '200', 63000),
    ('S304', 'Express', '300', 77000),
    ('S305', 'Express', '500' , 91000),
    ('S306', 'Express', '800' , 105000),
    ('S307', 'Express', '>800' , 119000);
#insert du lieu don hang
#tớ chưa add code total
INSERT INTO Product (OrderID, Payer, PickupCity, PickupDistrict, PickupWard, PickupAddress, RecipientName,
		DeliveryCity, DeliveryDistrict, DeliveryWard, DeliveryAddress, PhoneRecipient, ServiceID)
VALUES
('P001', 'Nguoi nhan', 'Bac Giang', 'Yen The', 'Xa Yen The',
'99 Duong Dong Lac', 'Nguyen Thi Lan', 'Da Nang', 'Hai Chau', 'Thuan Phuoc',
 'So 45 Thuan Phuoc', '0906946044', 'S302'),
('P002', 'Nguoi gui', 'Quang Binh', 'Dong Hoi', 'Hai Dinh',
'117 Duong Hai Dinh', 'Pham Hai Anh', 'TP. Ho Chi Minh', 'Quan 1', 'Ben Nghe',
 'So 49 Ben Nghe',  '0987654321', 'S207'),
('P003', 'Nguoi nhan', 'Can Tho', 'Ninh Kieu', 'Cai Khe',
 'So 84B Cai Khe', 'Pham Van Dung', 'TP. Ho Chi Minh', 'Binh Thanh', 'Phuong 11',
 '123 Duong D2', '0920475186', 'S203'),
('P004', 'Nguoi gui', 'Thai Nguyen', 'Thanh pho Thai Nguyen', 'Phuong Quang Vinh',
'449 Duong Quang Trung', 'Le Van Hai', 'Quang Ninh', 'Ha Long', 'Bai Chay',
 'So 1 Bai Chay', '0942326688', 'S303'),
('P005', 'Nguoi nhan', 'Ha Noi', 'Ba Dinh', 'Ngoc Ha',
'035 Duong Hoang Hoa Tham', 'Tran Van Kiet', 'Ha Noi', 'Ba Dinh', 'Ngoc Ha',
 'So 10 Ngoc Ha', '0974627239', 'S101'),
('P006', 'Nguoi gui', 'Quang Tri', 'Dong Ha', 'Hai Ba Trung',
 'So 112 Hai Ba Trung',  'Bui Van Minh', 'Da Nang', 'Son Tra', 'Phuoc My',
 'So 07A Phuoc My', '0931867249', 'S202'),
('P007', 'Nguoi gui', 'Can Tho', 'Cai Rang', 'An Khanh',
 'So 11 An Khanh',  'Vo Thi Mai', 'Thai Nguyen', 'Thanh pho Thai Nguyen', 'Phuong Quang Vinh',
 '449 Duong Quang Trung', '0986432079', 'S201'),
('P008', 'Nguoi nhan', 'Bac Giang', 'Hiep Hoa', 'Thang',
'505 Duong Thang', 'Vo Thi Em', 'Can Tho', 'Cai Rang', 'An Khanh',
'So 14 An Khanh', '0932548791', 'S107'),
('P009', 'Nguoi nhan', 'Quang Tri', 'Hai Lang', 'Thi tran Lang Co',
'723 Duong Truong Son', 'Hoang Van Phong', 'TP. Ho Chi Minh', 'Go Vap', 'Phuong Phu Nhuan',
'185 Hung Vuong', '0947162930', 'S107'),
('P010', 'Nguoi gui', 'Ha Noi', 'Thanh Xuan', 'Khuong Trung',
 'So 123 Khuong Trung', 'Tran Thi Thanh Thao', 'Nghe An', 'Vinh', 'Hung Dung',
 'So 20 Hung Dung', '0912548796', 'S205'),
 #11-20
('P011', 'Nguoi gui', 'TP. Ho Chi Minh', 'Go Vap', 'Phuong Phu Nhuan',
'185 Hung Vuong', 'Bui Thi Hanh', 'Thai Binh', 'Kien Xuong', 'Minh Quang',
'789 Duong Minh Quang', '0901064092','S207'),
('P012', 'Nguoi nhan', 'Can Tho', 'Cai Rang', 'An Khanh',
'So 14 An Khanh', 'Hoang Anh Xuan', 'Da Nang', 'Ngu Hanh Son', 'My An',
 'So 94 My An', '0953124789', 'S307'),
('P013', 'Nguoi gui', 'Bac Giang', 'Yen The', 'Xa Yen The',
'99 Duong Dong Lac', 'Pham Van Dung', 'Ha Noi', 'Cau Giay', 'Quan Hoa',
 '123 Duong Quan Hoa', '0920475186', 'S102'),
('P014', 'Nguoi nhan', 'Thai Nguyen', 'Ninh Binh', 'Ninh Hoa',
'19 Duong Hoa Lu', 'Pham Thi Hue', 'Vinh Phuc', 'Phuc Yen', 'Xuan Hoa',
 'So 122 Xuan Hoa', '0987396094', 'S202'),
('P015', 'Nguoi gui', 'Ha Noi', 'Ba Dinh', 'Ngoc Ha',
'035 Duong Hoang Hoa Tham', 'Pham Thi Duong', 'Lang Son', 'Lang Son', 'Hoang Van Thu',
 'So 51 Hoang Van Thu', '0924587123','S303'),
('P016', 'Nguoi gui', 'Ha Noi', 'Long Bien', 'Phuong Ngoc Lam',
'24 Ngo Tram', 'Tran Thi Chinh', 'Da Nang', 'Hai Chau', 'Thuan Phuoc',
 'So 011 Thuan Phuoc', '0932478591','S106'),
('P017', 'Nguoi nhan', 'Bac Giang', 'Hiep Hoa', 'Thang',
'505 Duong Thang', 'Vu Thi Thu', 'Quang Ninh', 'Ha Long', 'Bai Chay',
'313 Duong Ha Long', '0994725645',  'S103'),
('P018', 'Nguoi gui', 'Thai Nguyen', 'Thanh pho Thai Nguyen', 'Phuong Quang Vinh',
'449 Duong Quang Trung', 'Le Thi Huyen', 'Ha Tinh', 'Hong Linh', 'Trung Luong',
 'So 08 Trung Luong', '0915784236', 'S206'),
('P019', 'Nguoi nhan', 'Ha Noi', 'Hai Ba Trung', 'Thanh Nhan',
'456 Duong Le Dai Hanh', 'Le Van Hai', 'Da Nang', 'Lien Chieu', 'Hoa Khanh',
 '886 Duong Hoang Van Thai', '0942326688', 'S206'),
('P020', 'Nguoi gui', 'Ha Noi', 'Long Bien', 'Phuong Ngoc Lam',
'24 Ngo Tram', 'Tran Hoang Hien', 'Gia Lai', 'Pleiku', 'Chu Se',
 'So 188 Chu Se', '0925781234', 'S207'),
 #21-30
('P021', 'Nguoi gui', 'Quang Binh', 'Dong Hoi', 'Hai Dinh',
'117 Duong Hai Dinh', 'Nguyen Thi Lan', 'Bac Giang', 'Hiep Hoa', 'Thang',
'505 Duong Thang', '0981902291', 'S106'),
('P022', 'Nguoi nhan', 'TP. Ho Chi Minh', 'Go Vap', 'Phuong Phu Nhuan',
'185 Hung Vuong', 'Pham Van Dung', 'Ha Noi', 'Cau Giay', 'Quan Hoa',
 '123 Duong Quan Hoa', '0920475186', 'S307'),
('P023', 'Nguoi gui',  'Ha Noi', 'Hoan Kiem', 'Phuong Trang Tien',
'723 Pho Hue', 'Hoang Van Khanh', 'Ha Nam', 'Phu Ly', 'Trieu Duong',
'26 Duong Trieu Duong', '0987232244',  'S102'),
('P024', 'Nguoi nhan', 'Ninh Binh', 'Ninh Hoa', 'Hoa Lu',
'19 Duong Hoa Lu', 'Pham Thi Duong', 'Da Nang', 'Son Tra', 'An Hai',
'456 Duong An Hai', '0945678901', 'S206'),
('P025', 'Nguoi gui', 'Binh Duong', 'Di An', 'Tan Dong Hiep',
'845 Duong Tan Dong Hiep', 'Nguyen Van Em', 'Can Tho', 'Ninh Kieu', 'An Hoa',
'123 Duong An Hoa', '0956789012', 'S204'),
('P026', 'Nguoi nhan', 'Can Tho', 'Binh Thuy', 'Long Hoa',
'456 Duong Long Hoa', 'Tran Thi Phuong', 'TP. Ho Chi Minh', 'Binh Thanh', 'Phuong 26',
 '789 Duong Phuong 26', '0967890123', 'S203'),
('P027', 'Nguoi gui', 'Thanh Hoa', 'Hoang Hoa', 'Hoang Duc',
'89 Duong Hoang Duc', 'Duong Thi Lan', 'Hai Phong', 'Ngo Quyen', 'Le Chan',
 'So 23A Le Chan', '0957681029', 'S204'),
('P028', 'Nguoi nhan', 'Quang Ninh', 'Cam Pha', 'Cam Trung',
'789 Duong Cam Trung', 'Pham Thi Huong', 'Hai Duong', 'Cam Giang', 'Cam Phuc',
'123 Duong Cam Phuc', '0989012345', 'S202'),
('P029', 'Nguoi gui', 'Da Nang', 'Lien Chieu', 'Hoa Khanh',
'886 Duong Hoang Van Thai', 'Bui Thi Hanh', 'Thai Binh', 'Kien Xuong', 'Minh Quang',
'789 Duong Minh Quang', '0901064092', 'S306'),
('P030', 'Nguoi nhan', 'Thai Binh', 'Dong Hung', 'Dong Kinh',
'123 Duong Dong Kinh', 'Vo Thi Mai', 'Thai Nguyen', 'Thanh pho Thai Nguyen', 'Phuong Quang Vinh',
 '449 Duong Quang Trung', '0986432079', 'S203'),
#31-40
('P031', 'Nguoi gui', 'Quang Tri', 'Hai Lang', 'Thi tran Lang Co',
'723 Duong Truong Son', 'Hoang Van Khanh', 'Hai Phong', 'Hong Bang', 'Thuong Ly',
'456 Duong Thuong Ly', '0912345679', 'S206'),
('P032', 'Nguoi nhan', 'Hue', 'Phu Vang', 'Phu An',
'632 Duong Phu An', 'Bui Van Minh', 'Bac Giang', 'Yen The', 'Xa Yen The',
'45 Duong Dong Lac', '0931867249', 'S201'),
('P033', 'Nguoi gui', 'Hai Phong', 'Le Chan', 'An Duong',
'108 Duong An Duong', 'Le Thi Minh', 'Hue', 'Vinh Ninh', 'Phu Hoi',
'789 Duong Phu Hoi', '0934567891', 'S206'),
('P034', 'Nguoi nhan', 'Hue', 'An Cuu', 'Thuy Bieu',
'123 Duong Thuy Bieu', 'Le Thi Dao', 'Ninh Binh', 'Ninh Hoa', 'Hoa Lu',
'19 Duong Hoa Lu', '0923333218', 'S106'),
('P035', 'Nguoi gui', 'Quang Tri', 'Dong Ha', 'Hai Ba Trung',
 'So 112 Hai Ba Trung', 'Le Thi Dao', 'Ninh Binh', 'Ninh Hoa', 'Hoa Lu',
'19 Duong Hoa Lu', '0923333218',  'S103'),
('P036', 'Nguoi nhan', 'Quang Binh', 'Dong Hoi', 'Hai Dinh',
'117 Duong Hai Dinh', 'Tran Van Phuc', 'TP. Ho Chi Minh', 'Go Vap', 'Phuong 17',
'789 Duong Phuong 17', '0967890124', 'S107'),
('P037', 'Nguoi gui', 'Ha Nam', 'Phu Ly', 'Trieu Duong',
'26 Duong Trieu Duong', 'Le Thi Quyen', 'Quang Ninh', 'Uong Bi', 'Phuong Bac Son',
'456 Duong Bac Son', '0978901235', 'S104'),
('P038', 'Nguoi nhan', 'TP. Ho Chi Minh', 'Quan 1', 'Phuong 9',
'789 Duong Nguyen Van Nghi', 'Nguyen Van An', 'Ha Noi', 'Hoan Kiem', 'Phuong Trang Tien',
'723 Pho Hue','0952938416', 'S207'),
('P039', 'Nguoi gui', 'Ha Noi', 'Long Bien', 'Phuong Ngoc Lam',
'24 Ngo Tram', 'Nguyen Thi Suong', 'Thai Binh', 'Thai Thuy', 'Thuy Ha',
'789 Duong Thuy Ha', '0990123457', 'S303'),
('P040', 'Nguoi nhan', 'TP. Ho Chi Minh', 'Quan 1', 'Phuong 9',
'789 Duong Nguyen Van Nghi', 'Tran Van The', 'Nam Dinh', 'Y Yen', 'Y Hoa',
'456 Duong Y Hoa', '0901234568', 'S207'),
#41-50
('P041', 'Nguoi nhan', 'Bac Giang', 'Yen The', 'Xa Yen The',
'99 Duong Dong Lac', 'Le Thi Hoa', 'Hai Phong', 'Ngo Quyen', 'May To',
'22 Hoang Van Thu', '0987654321', 'S202'),
('P042', 'Nguoi gui', 'Da Nang', 'Hai Chau', 'Thach Thang',
'120 Tran Phu', 'Pham Van Chinh', 'Hue', 'Phu Nhuan', 'An Cuu',
'18 Tran Cao Van', '0912345678', 'S102'),
('P043', 'Nguoi gui', 'Can Tho', 'Ninh Kieu', 'An Hoa',
'45 Mau Than', 'Bui Van Hung', 'TP. Ho Chi Minh', 'Quan 3', 'Phuong 7',
'80 Vo Thi Sau', '0917414710', 'S103'),
('P044', 'Nguoi nhan', 'Nha Trang', 'Loc Tho', 'Tan Lap',
'30 Le Thanh Ton', 'Hoang Van Ai', 'Quy Nhon', 'Tran Phu', 'Hai Cang',
'12 Nguyen Hue', '0978123456', 'S202'),
('P045', 'Nguoi gui', 'Ha Noi', 'Hai Ba Trung', 'Thanh Nhan',
'456 Duong Le Dai Hanh', 'Le Van Hai', 'Da Nang', 'Lien Chieu', 'Hoa Khanh',
'886 Duong Hoang Van Thai', '0942326688', 'S206'),
('P046', 'Nguoi gui', 'Da Nang', 'Lien Chieu', 'Hoa Khanh',
'886 Duong Hoang Van Thai', 'Nguyen Phuc Khang', 'Hai Phong', 'Le Chan', 'Nghia Xa',
'50 To Hieu', '0945678901', 'S307'),
('P047', 'Nguoi gui', 'Bac Giang', 'Yen The', 'Xa Yen The',
'45 Duong Dong Lac', 'Duong Thi Lan', 'Hai Phong', 'Ngo Quyen', 'Le Chan',
 'So 23A Le Chan', '0957681029', 'S203'),
('P048', 'Nguoi gui', 'Can Tho', 'Cai Rang', 'Ba Lang',
'150 Cach Mang Thang Tam', 'Le Thi Cuc', 'Hue', 'Phu Hoi', 'Phu Nhuan',
'So 6 Phu Nhuan', '0948719302', 'S107'),
('P049', 'Nguoi gui', 'Ha Noi', 'Long Bien', 'Phuong Ngoc Lam',
'24 Ngo Tram', 'Pham Thi Rong', 'Nha Trang', 'Van Ninh', 'Van Gia',
'25 Le Hong Phong', '0956789012', 'S207'),
('P050', 'Nguoi gui', 'Hai Phong', 'Kien An', 'Bac Son',
 '10 Pham Van Dong', 'Hoang Van Khanh', 'Ha Nam', 'Phu Ly', 'Trieu Duong',
'26 Duong Trieu Duong', '0987232244', 'S203');
select * from product;
        #order by OrderID asc;
#Cái dưới này để nếu sai thì xóa nhập lại thui
#delete from product
#where OrderID like "P%";
---------------------------------------------------------------
#insert du lieu bang tao don hang
insert into  OrderCreate (GiverID, OrderID, ReciverID, OrderDate)
values
 ('U002', 'P009', 'U010', '2024-05-27' ), ('U002', 'P031', '', '2024-05-30'),
 ('U003', 'P019', 'U019',  '2024-05-28'), ('U003', 'P045',  'U019', '2024-05-28'),
 ('U005', 'P047', 'U013', '2024-05-31'),
 ('U006', 'P023', 'U030', '2024-05-31'),
 ('U007', 'P044', '', '2024-05-28'), ('U007', 'P018', '', '2024-03-25' ), ('U007', 'P004', 'U019', '2024-02-14'),
 ('U008', 'P048', 'U001', '2023-04-06'),
 ('U009', 'P038', '', '2024-05-27'), ('U009', 'P050', 'U030', '2024-05-29'), ('U009', 'P040', '', '2022-06-01' ),
 ('U010', 'P011', 'U027', '2024-05-09'), ('U010', 'P022', 'U004', '2024-06-01'),
 ('U011', 'P010', '', '2021-09-18'),
 ('U012', 'P012', '', '2024-06-01'),
 ('U014', 'P042', '', '2021-09-30'),
 ('U015', 'P016', '', '2023-02-14'), ('U015', 'P039', '', '2024-05-31'), ('U015', 'P020', '', '2024-05-29'), ('U015', 'P049', '', '2024-02-14' ),
 ('U017', 'P033', '' , '2023-11-13'),
 ('U019', 'P035', 'U029', '2024-05-29'), ('U019', 'P006', 'U005', '2023-11-05' ), ('U019', 'P029', 'U027', '2024-06-01'), ('U019', 'P046', '', '2024-06-01'),
 ('U020', 'P005', 'U034', '2024-05-30'), ('U020', 'P015', '', '2024-06-01' ),
 ('U021', 'P032', 'U005',  '2022-12-22'),
 ('U022', 'P026', '', '2024-05-30'),
 ('U024', 'P030', 'U007', '2024-01-13' ), ('U024', 'P034', 'U029', '2024-05-30'),
 ('U025', 'P041', '', '2024-05-31'), ('U025', 'P013', 'U004', '2021-06-02'), ('U025', 'P001', 'U016',  '2021-03-19'),
 ('U026', 'P028', '', '2022-06-01'), ('U026', 'P027', 'U013', '2024-05-28'),
 ('U028', 'P021', 'U033', '2022-09-09'), ('U028', 'P002', '', '2024-05-28'), ('U028', 'P043', 'U025', '2024-05-28'), ('U028', 'P036', '', '2022-08-31'),
 ('U029', 'P014', 'U018', '2024-05-31'), ('U029', 'P024', '', '2023-07-30'),
 ('U030', 'P037', '', '2021-12-31'),
 ('U031', 'P003',  '', '2024-05-29'),
 ('U033', 'P007', 'U007',  '2022-03-15'), ('U033', 'P017', 'U022', '2024-05-23'), ('U033', 'P008', '', '2024-05-25'),
 ('U034', 'P025', '', '2023-01-08');
#orderdetails

        #select * from OrderCreate;
#delete from OrderCreate
#where OrderID like 'P%';

----------------------------------------
#insert du lieu chi tiet don hang
insert into OrderDetails(ItemID, OrderID, ItemName, SurchargeID, Weight, itemprice)
values
('IID001', 'P002', 'Laptop Gamming Lenovo', 'SU007', 2.5, 25000000),
('IID002', 'P002', 'Mouse', 'SU007', 0.1, 100000),
('IID003', 'P003', 'Keyboard', 'SU007', 0.7, 1000000),
('IID004', 'P004', 'Monitor', 'SU007', 5.0, 4000000),
('IID005', 'P005', 'Printer', 'SU007', 8.0, 10000000),
('IID006', 'P006', 'Scanner', 'SU007', 3.2, 5000000),
('IID007', 'P007', 'Tablet', 'SU007', 0.8, 10000000),
('IID008', 'P008', 'Charger', 'SU007', 0.2, 50000),
('IID009', 'P008', 'Iphone 14 promax', 'SU007', 0.4, 25000000),
('IID010', 'P010', 'Earphones', 'SU007', 0.05, 5000000),
('IID011', 'P011', 'But bi', 'SU001', 0.02, 10000),
('IID012', 'P012', 'Vo ghi chep', 'SU001', 0.5, 100000),
('IID013', 'P013', 'Cap sach', 'SU001', 0.7, 400000),
('IID014', 'P014', 'Hop but', 'SU001', 0.1, 200000),
('IID015', 'P015', 'Bang trang', 'SU001', 0.1, 50000),
('IID016', 'P016', 'But long', 'SU001', 0.03, 100000),
('IID017', 'P017', 'But chi mau', 'SU001', 0.2, 60000),
('IID018', 'P018', 'Got but chi', 'SU001', 0.05, 70000),
('IID019', 'P019', 'Keo', 'SU001', 0.15, 100000),
('IID020', 'P020', 'Thuoc ke', 'SU001', 0.1, 50000),
('IID021', 'P021', 'Giay A4', 'SU001', 0.5, 100000),
('IID022', 'P022', 'Giay in mau', 'SU001', 1.0, 90000),
('IID023', 'P023', 'So tay', 'SU001', 0.3, 30000),
('IID024', 'P024', 'Ghim bam', 'SU001', 0.02, 100000),
('IID025', 'P025', 'Bang keo', 'SU001', 0.1, 50000),
('IID026', 'P026', 'Kep giay', 'SU001', 0.02, 70000),
('IID027', 'P027', 'Dao roc giay', 'SU001', 0.05, 200000),
('IID028', 'P028', 'Bia cung', 'SU001', 0.3, 100000),
('IID029', 'P029', 'Bang dinh', 'SU001', 0.08, 50000),
('IID030', 'P030', 'Cuc tay', 'SU001', 0.02,70000),
('IID031', 'P031', 'Giay note', 'SU001', 0.1, 100000),
('IID032', 'P032', 'Giay nhan', 'SU001', 0.05, 10000),
('IID033', 'P033', 'So ghi chep', 'SU001', 0.4, 100000),
('IID034', 'P034', 'But muc', 'SU001', 0.02, 60000),
('IID035', 'P035', 'Giay bia', 'SU001', 0.5, 90000),
('IID036', 'P036', 'Kep buom', 'SU001', 0.03, 50000),
('IID037', 'P037', 'Giay decal', 'SU001', 0.2, 20000),
('IID038', 'P038', 'But nho', 'SU001', 0.03, 5.500),
('IID039', 'P039', 'Giay than', 'SU001', 0.2, 4.500),
('IID040', 'P040', 'Ghim kep', 'SU001', 0.01, 200000),
('IID041', 'P041', 'So ke hoach', 'SU001', 0.3, 50000),
('IID042', 'P042', 'But gel', 'SU001', 0.02, 50000),
('IID043', 'P043', 'Thit bo', 'SU006', 1.5, 90000),
('IID044', 'P044', 'Ca hoi', 'SU006', 1.2, 70000),
('IID045', 'P045', 'Ga nguyen con', 'SU006', 2.0, 100000),
('IID046', 'P046', 'Tom su', 'SU006', 1.0, 0),
('IID047', 'P047', 'Hai san dong lanh', 'SU006', 2.5, 0),
('IID048', 'P048', 'Nuoc rua chen', 'SU005', 1.0, 0),
('IID049', 'P049', 'Dau goi dau', 'SU005', 0.5, 0),
('IID050', 'P050', 'Sua tam', 'SU005', 0.6, 0),
('IID051', 'P003', 'Nuoc xa vai', 'SU005', 0.8, 0),
('IID052', 'P002', 'Nuoc lau san', 'SU005', 1.2, 90000),
('IID053', 'P003', 'Binh Hoa', 'SU003', 0.8, 70000),
('IID054', 'P004', 'Chen Su', 'SU003', 0.3, 0),
('IID055', 'P005', 'Ly Thuy Tinh', 'SU003', 0.4, 0),
('IID056', 'P001', 'Tu Quan Ao', 'SU004', 50.0, 3000000),
('IID057', 'P001', 'Giuong Ngu', 'SU004', 70.0, 5000000),
('IID058', 'P001', 'Ban Lam Viec', 'SU004', 25.0, 100000),
('IID059', 'P009', 'Dong ho Rolex', 'SU002', 0.1, 100000000),
('IID060', 'P009', 'Lac tay bac', 'SU002', 2.5, 50000000),
('IID061', 'P009', 'Vang nguyen khoi', 'SU002', 1.0, 70000000);

#select * from OrderDetails;
#---------------------------------------------------------------
        #Tao don bi huy 7 18 25 33 42
        delete from product
        where orderid = 'P007' or orderid = 'P018' or orderid = 'P025'
           or orderid = 'P033' or orderid = 'P042';
        #--------------------------------------------------------------

        #insert Kho
INSERT INTO Warehouse (WarehouseID, WareName, Address, Ward, District, City)
VALUES
('W001', 'Kho Dien Bien', '35, Duong Hoang Dieu', 'Phuong Muong Thanh', 'TP Dien Bien Phu', 'Dien Bien'),
('W002', 'Kho Hoa Binh', '7, Duong Nguyen Trai', 'Phuong Thai Binh', 'TP Hoa Binh', 'Hoa Binh'),
('W003', 'Kho Lai Chau', '18, Duong Dien Bien Phu', 'Xa Phong Tho', 'TP Tam Duong', 'Lai Chau'),
('W004', 'Kho Lao Cai', '32, Duong Tran Hung Dao', 'Phuong Duyen Hai', 'TP Lao Cai', 'Lao Cai'),
('W005', 'Kho Son La', '69, Duong Truong Chinh', 'Phuong Chieng Le', 'TP Son La', 'Son La'),
('W006', 'Kho Yen Bai', '14, Duong Tran Phu', 'Phuong Nguyen Thai Hoc', 'TP Yen Bai', 'Yen Bai'),
('W007', 'Kho Bac Giang', '23, Duong Tran Phu', 'Phuong Ngo Quyen', 'TP Bac Giang', 'Bac Giang'),
('W008', 'Kho Bac Kan', '55, Duong Hung Vuong', 'Phuong Nguyen Trai', 'TP Bac Kan', 'Bac Kan'),
('W009', 'Kho Cao Bang', '61, Duong Tran Hung Dao', 'Phuong Song Bang', 'TP Cao Bang', 'Cao Bang'),
('W010', 'Kho Ha Giang', '12, Duong Tran Phu', 'Phuong Minh Khai', 'TP Ha Giang', 'Ha Giang'),
('W011', 'Kho Lang Son', '56, Duong Dong Da', 'Phuong Chi Lang', 'TP Lang Son', 'Lang Son'),
('W012', 'Kho Phu Tho', '77, Duong Tran Hung Dao', 'Phuong Minh Nong', 'TP Viet Tri', 'Phu Tho'),
('W013', 'Kho Quang Ninh', '4, Duong Lac Long Quan', 'Phuong Hong Hai', 'TP Cam Pha', 'Quang Ninh'),
('W014', 'Kho Thai Nguyen', '98, Duong Nguyen Van Cu', 'Phuong Quang Trung', 'TP Thai Nguyen', 'Thai Nguyen'),
('W015', 'Kho Tuyen Quang', '36, Duong Tran Phu', 'Phuong Minh Xuan', 'TP Tuyen Quang', 'Tuyen Quang'),
('W016', 'Kho Bac Ninh', '46, Duong Tran Phu', 'Phuong Kinh Bac', 'TP Bac Ninh', 'Bac Ninh'),
('W017', 'Kho Ha Nam', '21, Duong Tran Phu', 'Phuong Quang Trung', 'TP Phu Ly', 'Ha Nam'),
('W018', 'Kho Ha Noi', '87, Duong Tran Hung Dao', 'Phuong Trang Tien', 'Quan Hoan Kiem', 'Ha Noi'),
('W019', 'Kho Ha Noi', '65, Duong Nguyen Trai', 'Phuong Thanh Xuan', 'Quan Thanh Xuan', 'Ha Noi'),
('W020', 'Kho Hai Duong', '52, Duong Tran Phu', 'Phuong Lam Son', 'TP Hai Duong', 'Hai Duong'),
('W021', 'Kho Hai Phong', '58, Duong Ngo Quyen', 'Phuong Niem Nghia', 'TP Hai Phong', 'Hai Phong'),
('W022', 'Kho Hung Yen', '25, Duong Le Loi', 'Phuong Hong Chau', 'TP Hung Yen', 'Hung Yen'),
('W023', 'Kho Nam Dinh', '85, Duong Nguyen Van Cu', 'Phuong Tran Quang Dieu', 'TP Nam Dinh', 'Nam Dinh'),
('W024', 'Kho Ninh Binh', '61, Duong Le Loi', 'Phuong Nam Binh', 'TP Ninh Binh', 'Ninh Binh'),
('W025', 'Kho Thai Binh', '9, Duong Tran Phu', 'Phuong Quang Trung', 'TP Thai Binh', 'Thai Binh'),
('W026', 'Kho Vinh Phuc', '46, Duong Le Loi', 'Phuong Ngo Quyen', 'TP Vinh Yen', 'Vinh Phuc'),
('W027', 'Kho Ha Tinh', '17, Duong Tran Phu', 'Phuong Thach Trung', 'TP Ha Tinh', 'Ha Tinh'),
('W028', 'Kho Nghe An', '9, Duong Hung Vuong', 'Phuong Hung Dung', 'TP Vinh', 'Nghe An'),
('W029', 'Kho Quang Binh', '15, Duong Tran Hung Dao', 'Phuong Dong Phu', 'TP Dong Hoi', 'Quang Binh'),
('W030', 'Kho Quang Tri', '476, Duong Tran Phu', 'Phuong 1', 'TP Dong Ha', 'Quang Tri'),
('W031', 'Kho Thanh Hoa', '64, Duong Le Loi', 'Phuong Dien Bien', 'TP Thanh Hoa', 'Thanh Hoa'),
('W032', 'Kho Thua Thien Hue', '71, Duong Hung Vuong', 'Phuong Phu Hoi', 'TP Hue', 'Thua Thien Hue'),
('W033', 'Kho Dak Lak', '135, Duong Tran Phu', 'Phuong Tan An', 'TP Buon Ma Thuot', 'Dak Lak'),
('W034', 'Kho Dak Nong', '7, Duong Hung Vuong', 'Phuong Nghia Trung', 'TP Gia Nghia', 'Dak Nong'),
('W035', 'Kho Gia Lai', '62, Duong Tran Hung Dao', 'Phuong Nguyen Trai', 'TP Pleiku', 'Gia Lai'),
('W036', 'Kho Kon Tum', '96, Duong Tran Phu', 'Phuong Duy Tan', 'TP Kon Tum', 'Kon Tum'),
('W037', 'Kho Lam Dong', '84, Duong Le Loi', 'Phuong 1', 'TP Da Lat', 'Lam Dong'),
('W038', 'Kho Ba Ria Vung Tau', '123, Duong Tran Phu', 'Phuong Long Binh', 'TP Vung Tau', 'Ba Ria Vung Tau'),
('W039', 'Kho Binh Duong', '789, Duong Hung Vuong', 'Phuong Phu Cuong', 'TP Thu Dau Mot', 'Binh Duong'),
('W040', 'Kho Binh Phuoc', '71, Duong Tran Hung Dao', 'Phuong Tan Binh', 'TP Dong Xoai', 'Binh Phuoc'),
('W041', 'Kho Dong Nai', '56, Duong Tran Phu', 'Phuong Tan Mai', 'TP Bien Hoa', 'Dong Nai'),
('W042', 'Kho Ho Chi Minh', '98, Duong Le Loi', 'Quan 1', 'TP Ho Chi Minh', 'Ho Chi Minh'),
('W043', 'Kho Tay Ninh', '64, Duong Hung Vuong', 'Phuong Ninh Son', 'TP Tay Ninh', 'Tay Ninh'),
('W044', 'Kho An Giang', '81, Duong Tran Phu', 'Phuong My Thoi', 'TP Long Xuyen', 'An Giang'),
('W045', 'Kho Bac Lieu', '45, Duong Hung Vuong', 'Phuong 1', 'TP Bac Lieu', 'Bac Lieu'),
('W046', 'Kho Ben Tre', '39, Duong Tran Hung Dao', 'Phuong 2', 'TP Ben Tre', 'Ben Tre'),
('W047', 'Kho Ca Mau', '16, Duong Tran Phu', 'Phuong 7', 'TP Ca Mau', 'Ca Mau'),
('W048', 'Kho Can Tho', '84, Duong Le Loi', 'Quan Ninh Kieu', 'TP Can Tho', 'Can Tho'),
('W049', 'Kho Dong Thap', '16, Duong Hung Vuong', 'Phuong 4', 'TP Cao Lanh', 'Dong Thap'),
('W050', 'Kho Hau Giang', '58, Duong Nguyen Hue', 'Phuong My Thanh', 'TP Vi Thanh', 'Hau Giang'),
('W051', 'Kho Kien Giang', '22, Duong Ly Thuong Kiet', 'Phuong An Hoa', 'TP Rach Gia', 'Kien Giang'),
('W052', 'Kho Long An', '74, Duong Truong Dinh', 'Phuong 5', 'TP Tan An', 'Long An'),
('W053', 'Kho Soc Trang', '93, Duong Nguyen Trung Truc', 'Phuong 1', 'TP Soc Trang', 'Soc Trang'),
('W054', 'Kho Tien Giang', '82, Duong Tran Hung Dao', 'Phuong 3', 'TP My Tho', 'Tien Giang'),
('W055', 'Kho Tra Vinh', '51, Duong Le Loi', 'Phuong 2', 'TP Tra Vinh', 'Tra Vinh'),
('W056', 'Kho Vinh Long', '27, Duong Hung Vuong', 'Phuong 4', 'TP Vinh Long', 'Vinh Long'),
('W057', 'Kho Binh Dinh', '12, Duong Nguyen Hue', 'Phuong Tran Phu', 'TP Quy Nhon', 'Binh Dinh'),
('W058', 'Kho Khanh Hoa', '8, Duong Tran Phu', 'Phuong Loc Tho', 'TP Nha Trang', 'Khanh Hoa'),
('W059', 'Kho Ninh Thuan', '36, Duong 16/4', 'Phuong Phu Ha', 'TP Phan Rang-Thap Cham', 'Ninh Thuan'),
('W060', 'Kho Phu Yen', '57, Duong Tran Hung Dao', 'Phuong 7', 'TP Tuy Hoa', 'Phu Yen'),
('W061', 'Kho Binh Thuan', '123, Duong Le Loi', 'Phuong Phan Thiet', 'TP Phan Thiet', 'Binh Thuan'),
('W062', 'Kho Da Nang', '456, Duong Nguyen Van Linh', 'Phuong Hai Chau', 'Quan Hai Chau', 'Da Nang'),
('W063', 'Kho Quang Nam', '789, Duong Tran Hung Dao', 'Phuong An My', 'TP Tam Ky', 'Quang Nam'),
('W064', 'Kho Quang Ngai', '1011, Duong Ly Thuong Kiet', 'Phuong Tran Hung Dao', 'TP Quang Ngai', 'Quang Ngai');

#Insert bang nhap xuat
insert into ImportExport(WarehouseID, OrderID, InboundDate, OutboundDate)
values
	('W007', 'P001', '2021-03-19', '2021-03-19'),
	('W028', 'P001', '2021-03-19', '2021-03-19'),
	('W062', 'P001', '2021-03-19', '2021-03-19'),

	('W029', 'P002', '2024-05-28', '2024-05-29'),
	('W032', 'P002', '2024-05-29', '2024-05-31'),
	('W064', 'P002', '2024-05-31', NULL),

	('W048', 'P003', '2024-05-29', '2024-05-29'),
	('W046', 'P003', '2024-05-29', '2024-05-31'),
	('W042', 'P003', '2024-05-31', '2024-06-01'),

	('W014', 'P004', '2024-02-14', '2024-02-14'),
	('W018', 'P004', '2024-02-14', '2024-02-14'),
	('W013', 'P004', '2024-02-14', '2024-02-14'),
	('W030', 'P006', '2023-11-05', '2023-11-06'),
	('W032', 'P006', '2023-11-06', '2023-11-06'),
	('W062', 'P006', '2023-11-07', '2023-11-08'),
	('W007', 'P008', '2024-05-26', '2024-05-28'),
	('W027', 'P008', '2024-05-28', '2024-05-28'),
	('W032', 'P008', '2024-05-28', '2024-05-29'),
	('W057', 'P008', '2024-05-29', '2024-05-31'),
	('W042', 'P008', '2024-05-31', '2024-06-01'),
	('W048', 'P008', '2024-06-01', '2024-06-01'),
	('W030', 'P009', '2024-05-27', '2024-05-28'),
	('W062', 'P009', '2024-05-29', '2024-05-31'),
	('W058', 'P009', '2024-05-31', '2024-06-01'),
	('W018', 'P010', '2021-09-18', '2021-09-19'),
	('W023', 'P010', '2021-09-19', '2021-09-20'),
	('W031', 'P010', '2021-09-21', '2021-09-21'),
	('W028', 'P010', '2021-09-21', '2021-09-23'),
	('W042', 'P011', '2024-05-09', '2024-05-10'),
	('W057', 'P011', '2024-05-10', '2024-05-11'),
	('W029', 'P011', '2024-05-11', '2024-05-13'),
	('W025', 'P011', '2024-05-14', '2024-05-14'),
	('W048', 'P012', '2024-06-01', '2024-06-01'),
	('W058', 'P012', '2024-06-01', '2024-06-01'),
	('W062', 'P012', '2024-06-01', '2024-06-01'),
	('W007', 'P013', '2021-06-02', '2021-06-03'),
	('W019', 'P013', '2021-06-03', '2021-06-04'),
--
('W024','P015','2024-06-01', NULL),
('W018', 'P016', '2023-02-14', '2023-02-15'),
('W017', 'P016', '2023-02-15', '2023-02-16'),
('W024', 'P016', '2023-02-16', '2023-02-17'),
('W031', 'P016', '2023-02-17', '2023-02-18'),
('W028', 'P016', '2023-02-18', '2023-02-19'),
('W027', 'P016', '2023-02-19', '2023-02-20'),
('W029', 'P016', '2023-02-20', '2023-02-21'),
('W030', 'P016', '2023-02-21', '2023-02-22'),
('W032', 'P016', '2023-02-22', '2023-02-23'),
('W062', 'P016', '2023-02-23', '2023-02-24'),
('W007','P017', '2024-05-23', '2024-05-24'),
('W020','P017', '2024-05-24', '2024-05-25'),
('W013','P017', '2024-05-25', '2024-05-26'),
('W018','P019', '2024-05-28', '2024-05-29'),
('W017','P019', '2024-05-29', '2024-05-29'),
('W024','P019', '2024-05-30', '2024-05-30'),
('W031','P019', '2024-05-30', '2024-05-31'),
('W028','P019', '2024-05-31', '2024-05-31'),
('W062','P019', '2024-06-01', '2024-06-01'),
('W018','P020', '2024-05-29', '2024-05-30'),
('W031','P020', '2024-05-30', '2024-06-01'),
('W035','P020', '2024-06-01', '2024-06-01'),
('W029','P021', '2022-09-09', '2022-09-09'),
('W013','P021', '2022-09-10', '2022-09-11'),
('W007','P021', '2022-09-11', '2022-09-12'),
('W042','P022', '2024-06-01', '2024-06-01'),
('W024','P024', '2023-07-30', '2023-07-30'),
('W017','P024', '2023-07-31', '2023-08-01'),
('W062','P024', '2023-08-01', '2023-08-02'),
('W048','P026', '2024-05-30', '2024-05-31'),
('W024','P026', '2024-05-31', '2024-05-31'),
('W042','P026', '2024-06-01', '2024-06-01'),
#---
('W031','P027', '2024-05-28', '2024-05-29'),
('W024','P027', '2024-05-29', '2024-05-29'),
('W023','P027', '2024-05-30', '2024-05-31'),
('W025','P027', '2024-05-31',  NULL),
('W013','P028', '2022-06-01', '2022-06-01'),
('W021','P028', '2022-06-01', '2022-06-02'),
('W020','P028', '2022-06-02',  '2022-06-02'),
('W062','P029', '2024-06-01', '2024-06-01'),
('W032','P029', '2024-06-01', '2024-06-01'),
('W030','P029', '2024-06-01', '2024-06-01'),
('W029','P029', '2024-06-01', '2024-06-01'),
('W027','P029', '2024-06-01', '2024-06-01'),
('W028','P029', '2024-06-01', '2024-06-01'),
('W031','P029', '2024-06-01', '2024-06-01'),
('W024','P029', '2024-06-01', '2024-06-01'),
('W023','P029', '2024-06-01', '2024-06-01'),
('W025','P029', '2024-06-01', NULL),
('W025','P030', '2024-01-13', '2024-01-14'),
('W020','P030', '2024-01-14', '2024-01-14'),
('W007','P030', '2024-01-14',  '2024-01-15'),
('W032','P032', '2022-12-22', '2022-12-22'),
('W030','P032', '2022-12-22', '2022-12-22'),
('W029','P032', '2022-12-22', '2022-12-23'),
('W027','P032', '2022-12-23', '2022-12-23'),
('W028','P032', '2022-12-24', '2022-12-24'),
('W031','P032', '2022-12-24', '2022-12-25'),
('W002','P032', '2022-12-25', '2022-12-25'),
('W018','P032', '2022-12-25', '2022-12-26'),
('W007','P032', '2022-12-26', '2022-12-26'),
('W032','P034', '2024-05-30', '2024-05-30'),
('W030','P034', '2024-05-30', '2024-05-30'),
('W029','P034', '2024-05-31', '2024-05-31'),
('W028','P034', '2024-05-31', '2024-05-31'),
('W031','P034', '2024-05-31', '2024-06-01'),
('W024','P034', '2024-06-01',  '2024-06-01'),
('W030','P035', '2024-05-29', '2024-05-30'),
('W029','P035', '2024-05-30', '2024-05-30'),
('W028','P035', '2024-05-31',  NULL),
('W029', 'P036', '2022-08-31', '2022-08-31'),
('W030', 'P036', '2022-08-31', '2022-09-01'),
('W032', 'P036', '2022-09-01', '2022-09-01'),
('W063', 'P036', '2022-09-01', '2022-09-01'),
('W064', 'P036', '2022-09-02', '2022-09-02'),
('W057', 'P036', '2022-09-02', '2022-09-03'),
('W060', 'P036', '2022-09-03', '2022-09-03'),
('W033', 'P036', '2022-09-03', '2022-09-03'),
('W037', 'P036', '2022-09-03', '2022-09-03'),
('W041', 'P036', '2022-09-03', '2022-09-04'),
('W039', 'P036', '2022-09-04', '2022-09-04'),
('W042', 'P036', '2022-09-05',  '2022-09-05' ),
('W017','P037', '2021-12-31', '2021-12-31'),
('W022','P037', '2021-12-31', '2022-01-01'),
('W020','P037', '2022-01-01', '2022-01-01'),
('W013','P037', '2022-01-02',  '2022-01-02'),
('W042', 'P038', '2024-05-27', '2024-05-27'),
('W041', 'P038', '2024-05-27', '2024-05-28'),
('W037', 'P038', '2024-05-28', '2024-05-28'),
('W033', 'P038', '2024-05-28', '2024-05-29'),
('W035', 'P038', '2024-05-29', '2024-05-29'),
('W036', 'P038', '2024-05-29', '2024-05-29'),
('W063', 'P038', '2024-05-29', '2024-05-30'),
('W032', 'P038', '2024-05-30', '2024-05-30'),
('W030', 'P038', '2024-05-30', '2024-05-31'),
('W029', 'P038', '2024-05-31',  '2024-06-01'),
('W018','P039', '2024-05-31', '2024-05-31'),
('W017','P039', '2022-05-31', '2024-05-31'),
('W025','P039', '2024-05-31', '2024-05-31'),
('W042','P040', '2022-06-01', '2022-06-01'),
('W061','P040', '2022-06-02', '2022-06-02'),
('W058','P040', '2022-06-03', '2022-06-03'),
('W064','P040', '2022-06-04', '2022-06-04'),
('W062','P040', '2022-06-04', '2024-06-04'),
('W030','P040', '2022-06-05', '2022-06-05'),
('W028','P040', '2022-06-06', '2022-06-07'),
('W031','P040', '2022-06-07', '2022-06-07'),
('W023','P040', '2022-06-07', '2022-06-07'),
('W007','P041', '2024-05-31', '2024-05-31'),
('W020','P041', '2024-05-31', '2024-06-01'),
('W021','P041', '2024-06-01', '2024-06-01'),
('W048','P043', '2024-05-28', '2024-05-29'),
('W054','P043', '2024-05-29', '2024-05-29'),
('W042','P043', '2024-05-30', NULL),
('W058','P044', '2024-05-28', '2024-05-29'),
('W060','P044', '2024-05-29', '2024-05-29'),
('W057','P044', '2024-05-31', '2024-05-31'),
('W018','P045', '2024-05-28', '2024-05-28'),
('W024','P045', '2024-05-29', '2024-05-29'),
('W027','P045', '2024-05-30', '2024-05-30'),
('W029','P045', '2024-05-31', '2024-05-31'),
('W032','P045', '2024-06-01', '2024-06-01'),
('W062','P045', '2024-06-01', '2024-06-01'),
('W062','P046', '2024-06-01', '2024-06-01'),
('W030','P046', '2024-06-01', '2024-06-01'),
('W028','P046', '2024-06-01', '2024-06-01'),
('W023','P046', '2024-06-01', NULL),
('W048','P048', '2023-04-07', '2023-04-07'),
('W042','P048', '2023-04-08', '2023-04-08'),
('W041','P048', '2023-04-08', '2023-04-08'),
('W061','P048', '2023-04-09', '2023-04-09'),
('W054','P048', '2023-04-09', '2023-04-09'),
('W058','P048', '2023-04-10', '2023-04-10'),
('W063','P048', '2023-04-11', '2023-04-11'),
('W032','P048', '2023-04-11', '2023-04-11'),
('W018','P049', '2024-02-14', '2024-02-14'),
('W023','P049', '2024-02-14', '2024-02-15'),
('W024','P049', '2024-02-16', '2024-02-16'),
('W027','P049', '2024-02-17', '2024-02-17'),
('W029','P049', '2024-02-17', '2024-02-17'),
('W064','P049', '2024-02-18', '2024-02-18'),
('W057','P049', '2024-02-19', '2024-02-20'),
('W058','P049', '2024-02-20', '2024-02-20'),
('W021','P050', '2024-05-29', '2024-05-29'),
('W022','P050', '2024-05-29', '2024-05-29'),
('W020','P050', '2024-05-30', '2024-05-31');

#Insert bang giao hang

insert into send(EmployeeID, OrderID, ReceiptDate, EstimatedDate, ActualDate, SendStatus)
values
('SP001', 'P001', '2021-03-19',	'2021-03-19', '2021-03-19',	'Thanh cong'), #Dang Nang
('SP005', 'P003', '2024-06-01',	'2024-06-05', NULL,     	NULL), # TPHCM
('SP002', 'P004', '2024-02-14',	'2024-02-14', '2024-02-14',	'Thanh cong'),#Quang Ninh
('SP001', 'P006', '2023-11-08',	'2023-11-10', '2023-11-09',	'Thanh cong'), #Da Nang
('SP004', 'P008', '2024-06-01',	'2024-06-06', NULL,	       NULL), #Can Tho
('SP010', 'P010', '2021-09-23',	'2021-09-25', '2021-09-24',	'Thanh cong'),#Nghe An
('SP009', 'P011', '2024-05-14',	'2024-05-15', '2024-05-15',	'Thanh cong'), #Thai Binh
('SP001', 'P012', '2024-06-01',	'2024-06-02', NULL,	        NULL), #Dang Nang
('SP008', 'P013', '2021-06-04',	'2021-06-05', '2021-06-04',	'Thanh cong'), #Ha Noi
('SP001', 'P016', '2023-02-24',	'2023-02-26', '2023-02-28',	'Thanh cong'), #Da Nang
('SP002', 'P017', '2024-05-26',	'2024-05-27', NULL,	        'Hoan'),#Quang Ninh
('SP001', 'P019', '2024-06-01',	'2024-06-04', NULL,	       NULL),  #Da Nang
('SP006', 'P021', '2022-09-12',	'2022-09-15', '2022-09-14',	'Thanh cong'), #Bac Giang
('SP001', 'P024', '2023-08-02',	'2023-08-03', '2023-08-03',	'Thanh cong'), #Da Nang
('SP005', 'P026', '2024-06-01',	'2024-06-03', NULL,	        NULL), #Ho Chi Minh
('SP012', 'P028', '2022-06-02',	'2022-06-02', '2022-06-03',	'Thanh cong'), #Hai Duong
('SP014', 'P030', '2024-01-15',	'2024-01-16', '2024-01-20',	'Thanh cong'), #Thai Nguyen
('SP006', 'P032', '2022-12-26',	'2022-12-27', '2022-12-30',	'Thanh cong'), #Bac Giang
('SP011', 'P034', '2024-06-01',	'2024-06-02', NULL,	        NULL), #Ninh Binh
('SP005', 'P036', '2022-09-05',	'2022-09-06', '2022-09-10',	'Thanh cong'),#Ho Chi Minh
('SP002', 'P037', '2022-01-02',	'2022-01-03', '2022-01-02',	'Thanh cong'),#Quang Ninh
('SP009', 'P039', '2024-06-01',	'2024-06-01', NULL,	        'Hoan'), #Thai Binh
('SP007', 'P040', '2022-06-07',	'2022-06-09',  '2022-06-15', 'Thanh cong'), #Nam Dinh
('SP015', 'P041', '2024-06-01',	'2024-06-05', NULL,	        NULL), #Hai Phong
('SP013', 'P044', '2024-05-31',	'2024-05-31', '2024-06-01',	'Thanh cong'), #Quy nhon
('SP019', 'P045', '2024-06-01',	'2024-06-03', NULL,     	NULL), # Da Nang
('SP016', 'P048', '2023-04-11',	'2023-04-12', '2023-04-13',	'Thanh cong'), #Hue
('SP020', 'P049', '2024-02-20',	'2024-02-22', '2024-02-23',	'Thanh cong'); #Nha Trang
#function tinh tien ship
DELIMITER $$
        CREATE FUNCTION ShipPrice( OrderServiceID char(5) )
            RETURNS decimal(12, 3)
            DETERMINISTIC
        BEGIN
	declare OrderShipPrice decimal(12,3) default 0;
        select Price
        into OrderShipPrice
        from Service
        where OrderServiceID = ServiceID;
        return (OrderShipPrice);
        END$$
        DELIMITER ;

#function tinh tien don
DELIMITER $$
        CREATE FUNCTION OrderPrice( OrderItemID char(5), Payer varchar(15))
            RETURNS decimal(12, 3)
            DETERMINISTIC
        BEGIN
	declare OrderPrice decimal(12,3) default 0;
        select sum(itemprice)
        into OrderPrice
        from orderdetails
        where OrderItemID = OrderID
        group by OrderID;
        return (OrderPrice);
        END$$
        DELIMITER ;

#function tinh tien pp cod,
#drop function OrderPrice;
DELIMITER $$
        CREATE FUNCTION OrderCod( OrderItemID char(5), Payer varchar(15))
            RETURNS decimal(12, 3)
            DETERMINISTIC
        BEGIN
	declare ShipPrice decimal(12,3) default 0;
	declare OrderCod decimal(12,3) default 0;
    if Payer = 'nguoi nhan'
    then
        select ShipPrice(ServiceID)
        into ShipPrice
        from product
        where OrderItemID = OrderID;
        set OrderCod = ShipPrice*0.2;
        else set OrderCod = 0.00;
    end if;
    return (OrderCod);
    END$$
    DELIMITER ;

#function tinh pp hang
#drop function OrderSurcharge;
DELIMITER $$
    CREATE FUNCTION OrderSurcharge(OrderItemID char(5))
        RETURNS decimal(12, 3)
        DETERMINISTIC
    BEGIN
	declare OrderSurcharge decimal(12,3) default 0;
    declare ItemSurcharge decimal(12,3) default 0;
    declare ShipPrice decimal(12,3) default 0;
    declare OrderCod decimal(12,3);
    #Xac dinh % phu phi
    select sum(Surcharge.price)
    into ItemSurcharge
    from Surcharge, OrderDetails
    where OrderItemID = OrderID and OrderDetails.SurchargeID = Surcharge.SurchargeID
    group by OrderID;
    select ShipPrice(ServiceID)
    into ShipPrice
    from product
    where OrderItemID = OrderID;
    select OrderCod(OrderID, Payer)
    into OrderCod
    from product
    where OrderItemID = OrderID;
    set OrderSurcharge = (ShipPrice+OrderCod)*ItemSurcharge;
    return (OrderSurcharge);
    END$$
    DELIMITER ;

#function tinh tong tien
DELIMITER $$
    CREATE FUNCTION Total(OrderItemID char(5))
        RETURNS decimal(12, 3)
        DETERMINISTIC
    BEGIN
	declare ShipPrice decimal(12,3) default 0;
    declare OrderPrice decimal(12,3) default 0;
    declare OrderCod decimal(12,3) default 0;
    declare ShipSurcharge decimal(12,3) default 0;
    declare Total decimal(12,3) default 0;
    #Xac dinh % phu phi
    select ShipPrice(ServiceID)
    into ShipPrice
    from product
    where OrderItemID = OrderID;
    select OrderPrice(OrderID, Payer)
    into OrderPrice
    from product
    where OrderItemID = OrderID;
    select OrderCod(OrderID, Payer)
    into OrderCod
    from product
    where OrderItemID = OrderID;
    select OrderSurcharge(OrderID)
    into ShipSurcharge
    from product
    where OrderItemID = OrderID;
    set total = ShipPrice + OrderPrice + OrderCod + ShipSurcharge;
    return (total);
    END$$
    DELIMITER ;

#Tinh so tien thu nguoi nhan
DELIMITER $$
    CREATE FUNCTION RecipientPay( OrderItemID char(5), Payer varchar(15))
        RETURNS decimal(12, 3)
        DETERMINISTIC
    BEGIN
	declare RecipientPay decimal(12,3) default 0;
	declare total decimal(12,3) default 0;
    if Payer = 'nguoi nhan'
    then
    select total(OrderID)
    into total
    from product
    where OrderItemID = OrderID;
    set RecipientPay = total;
    else set RecipientPay = 0.00;
end if;
return (RecipientPay);
END$$
DELIMITER ;
use gvproject;
create view PriceTotal as
select OrderID, ServiceID, ShipPrice(ServiceID) as ShipPrice, OrderPrice(OrderID, Payer) as OrderPrice,
       OrderCod(OrderID, Payer) as OrderCod, OrderSurcharge(OrderID) as ShipSurcharge, Total(OrderID) as Total,
       RecipientPay(OrderID, Payer) as RecipientPay
from product;