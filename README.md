# KickVerse — Cửa hàng Giày Thương mại Điện tử Cao cấp (Premium Sneaker & Streetwear E-Commerce)

KickVerse là một nền tảng thương mại điện tử chuyên cung cấp giày sneaker và thời trang streetwear cao cấp. Dự án được thiết kế theo phong cách **Luxury Streetwear Dark Mode** sang trọng, mang lại trải nghiệm mua sắm đẳng cấp, trực quan và mượt mà cho người dùng.

---

## 🏗️ Kiến trúc & Công nghệ (Tech Stack)

Dự án được xây dựng với mô hình Fullstack tách biệt hoàn toàn Frontend và Backend:

### 1. Frontend (`kick-fe`)
- **Framework**: Vue 3 + Vite
- **Quản lý trạng thái (State Management)**: Pinia
- **Định tuyến (Routing)**: Vue Router
- **CSS / Styling**: Tailwind CSS + Custom Design System (Định nghĩa trong `main.css`)
- **Tính năng nổi bật**:
  - Giao diện Dark Mode cao cấp với tông màu Vàng Gold chủ đạo (`#c8a96e`) kết hợp Tím Điện (`#6c63ff`).
  - Hệ thống Responsive linh hoạt (`useResponsive`) tương thích hoàn hảo trên Mobile và Desktop.
  - Đồng bộ trạng thái đăng nhập liên tab (Cross-tab Session Sync).

### 2. Backend (`kick-api`)
- **Framework**: Java Spring Boot
- **Build Tool**: Maven
- **Cơ sở dữ liệu (Database)**: PostgreSQL (Khởi chạy qua Docker Compose)
- **ORM / Data Access**: Spring Data JPA / Hibernate

---

## 🌟 Các tính năng chính (Key Features)

### 🛒 Dành cho Khách hàng (Public & Customer Portal)
- **Xem cửa hàng & Tìm kiếm sản phẩm**: Lọc giày theo phân loại (Running, Lifestyle).
- **Trang chi tiết sản phẩm**: Xem chi tiết sản phẩm, chọn size, thêm vào giỏ hàng/danh sách yêu thích.
- **Giỏ hàng & Thanh toán**: Quản lý giỏ hàng trực quan, hỗ trợ Checkout nhanh chóng.
- **Wishlist & Notifications**: Lưu trữ các sản phẩm yêu thích và nhận thông báo thời gian thực.
- **Đăng ký / Đăng nhập**: Hỗ trợ chế độ tài khoản Demo nhanh cho nhiều vai trò khác nhau.

### 💼 Dành cho Nhân viên & Quản trị (Admin & Staff Portal)
- **Trang Quản trị (Admin Dashboard)**: Quản lý sản phẩm, đơn hàng, khách hàng và xem thống kê doanh thu.
- **Trang Nhân viên (Staff Dashboard)**: Quản lý và xử lý đơn hàng, hỗ trợ khách hàng nhanh chóng.

---

## 🚀 Hướng dẫn chạy thử (Local Setup)

### Yêu cầu hệ thống:
- Java JDK 17+ (cho Backend)
- Node.js 18+ & npm (cho Frontend)
- Docker & Docker Compose (cho Database)

### Bước 1: Khởi chạy Database & Backend
1. Di chuyển vào thư mục backend:
   ```bash
   cd kick-api
   ```
2. Khởi chạy Docker Compose để cài đặt PostgreSQL:
   ```bash
   docker compose up -d
   ```
3. Chạy ứng dụng Spring Boot:
   ```bash
   mvnw spring-boot:run
   ``` (hoặc chạy `./mvnw spring-boot:run` trên macOS/Linux, `mvnw.cmd spring-boot:run` trên Windows)

### Bước 2: Khởi chạy Frontend
1. Di chuyển vào thư mục frontend:
   ```bash
   cd kick-fe
   ```
2. Cài đặt các gói phụ thuộc (dependencies):
   ```bash
   npm install
   ```
3. Chạy ứng dụng ở chế độ phát triển (Development mode):
   ```bash
   npm run dev
   ```
4. Truy cập website tại địa chỉ mặc định: [http://localhost:5173](http://localhost:5173).

---

## 🎨 Design Tokens (Bảng màu & Kiểu chữ)
Chi tiết về quy chuẩn hệ thống thiết kế (Design System) của dự án được ghi nhận đầy đủ tại tệp tin [DESIGN.md](file:///d:/Workspaces/PET/KickStore/DESIGN.md).
