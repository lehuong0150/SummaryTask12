# 🏨 Hotel Booking Management System

## 📌 Giới thiệu
Đây là một hệ thống quản lý đặt phòng khách sạn (**Hotel Booking Management System**) được xây dựng bằng **Kotlin**.  
Ứng dụng mô phỏng các nghiệp vụ cơ bản của khách sạn và thể hiện rõ các khái niệm **Lập trình Hướng Đối Tượng (OOP)**.

---

## ✨ Các chức năng chính
- **Tìm kiếm và lọc phòng**
  - Tìm kiếm theo loại phòng (`STANDARD / DELUXE / SUITE`)
  - Lọc theo khoảng giá
  - Chỉ hiển thị phòng còn trống (`isAvailable = true`)

- **Tạo đặt phòng (Booking)**
  - Quy trình đặt phòng cho khách thường
  - Khách hàng VIP có thể thêm `specialRequests`
  - Tự động tính toán giảm giá dựa trên thành viên
  - Chọn phương thức thanh toán (`CREDIT_CARD / CASH`)

- **Hủy đặt phòng**
  - Xác thực dựa trên trạng thái đặt và phương thức thanh toán
  - Cập nhật trạng thái phòng trở lại trống
  - Thay đổi trạng thái booking → `"CANCELLED"`
  - Reset `totalAmount = 0.0` khi hủy thành công

- **Quản lý phòng**
  - Thêm phòng mới
  - Cập nhật thông tin phòng
  - Xóa phòng theo ID

- **Xem danh sách**
  - Toàn bộ phòng
  - Toàn bộ khách hàng
  - Toàn bộ booking

---

## 🧩 Các khái niệm OOP áp dụng
- **Abstract Class**  
  - `Person`, `Room`
- **Inheritance (Kế thừa)**  
  - `Customer` → `VIPCustomer`  
  - `Room` → `StandardRoom`, `DeluxeRoom`, `SuiteRoom`
- **Polymorphism (Đa hình)**  
  - Qua interface `Discountable` và override của `VIPCustomer`
- **Encapsulation (Đóng gói)**  
  - Thuộc tính `private/protected`, getter/setter
- **Interface**  
  - `Discountable` dùng để áp dụng chính sách giảm giá

---

## 🏗️ Kiến trúc hệ thống

### 1. Person (abstract class)
- Thuộc tính: `id`, `name`, `email`, `phone`
- Phương thức trừu tượng:  
  - `getRole()`
  - `getDisplayInfo()`
  - `validateInfo()`

### 2. Customer (open class, kế thừa từ Person)
- Thuộc tính: `membershipLevel`
- Phương thức: `getDisplayInfo()` → hiển thị thông tin khách hàng
- Cài đặt `Discountable` để được hưởng ưu đãi
- Là lớp cha của `VIPCustomer`

### 3. VIPCustomer (kế thừa từ Customer)
- Thuộc tính: `specialRequests`
- Phương thức: `addSpecialRequest()`
- Override `discountRate()` → áp dụng giảm giá cao hơn khách thường

### 4. Room (abstract class)
- Thuộc tính: `id`, `price`, `isAvailable`, `type`
- Phương thức: `getInfo()`
- Kế thừa thành 3 loại phòng:
  - `StandardRoom` – phòng tiêu chuẩn
  - `DeluxeRoom` – phòng cao cấp (có `hasOceanView`)
  - `SuiteRoom` – phòng hạng sang

### 5. Booking
- Thuộc tính:  
  - `id`, `customerId`, `roomId`, `dateCreated`, `nights`,  
    `methodPayment`, `status`, `totalAmount`
- Liên kết:
  - `customerId` → `Customer`
  - `roomId` → `Room`
- Sử dụng `Discountable`:  
  Nếu khách hàng là **VIP**, hệ thống tự động tính **giảm giá** khi tính `totalAmount`.
  
### 6. HotelDB (Database giả lập)
- `HotelDB` là một **object singleton** dùng để mô phỏng **cơ sở dữ liệu trong bộ nhớ (in-memory DB)**.
- Lưu trữ và quản lý danh sách:
  - `customerList` – danh sách khách hàng
  - `roomList` – danh sách phòng
  - `bookingList` – danh sách booking
- Có sẵn dữ liệu mẫu được khởi tạo trong `init { ... }`
- Cung cấp các chức năng CRUD:
  - `addCustomer()`, `getCustomer()`
  - `addRoom()`, `getRoom()`
  - `addBooking()`, `getBooking()`
  - `updateBooking()`, `deleteRoom()`
