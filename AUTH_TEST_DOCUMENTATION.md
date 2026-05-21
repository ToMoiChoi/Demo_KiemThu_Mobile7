# 📖 HƯỚNG DẪN ĐỌC HIỂU HỆ THỐNG KIỂM THỬ AUTHENTICATION (40 CASES)

Tài liệu này giúp bạn nắm bắt nhanh cấu trúc code, luồng xử lý dữ liệu và kịch bản kiểm thử tự động của module Đăng ký/Đăng nhập.

---

## 🏗 1. Kiến trúc hệ thống
Dự án được chia làm hai phần tách biệt theo tiêu chuẩn công nghiệp:
1.  **Application Logic (Phần App):** Xây dựng giao diện và xử lý nghiệp vụ (Java & XML).
2.  **Automation Test (Phần Robot):** Sử dụng Appium + TestNG để tự động điều khiển điện thoại theo mô hình **POM (Page Object Model)**.

---

## 📱 2. Chi tiết phần Ứng dụng (App Side)

### A. Giao diện (Layouts)
*   **`auth_main.xml`**: Cổng chào với 2 nút `Login` / `Register`.
*   **`auth_register.xml`**: Form đầy đủ 4 ô nhập. Đã thêm nút **Back** để hỗ trợ người dùng và Robot quay lại nhanh.
*   **`auth_login.xml`**: Form gọn nhẹ cho đăng nhập.

### B. Xử lý nghiệp vụ (Java Logic)
*   **`UserStorage.java`**: Đóng vai trò như một "Thủ thư". Nó chịu trách nhiệm mở file `users.txt`, ghi dữ liệu mới khi đăng ký và tìm kiếm thông tin khi có yêu cầu đăng nhập.
*   **`RegisterActivity.java`**: Chứa bộ não **Validation**. 
    *   **Thứ tự ưu tiên:** Kiểm tra Tên -> Email -> Pass -> Khớp Pass. 
    *   *Ví dụ:* Nếu bạn để trống tên, nó báo lỗi ngay và không kiểm tra mật khẩu bên dưới cho đến khi bạn sửa xong tên.
*   **Tự động điều hướng:** Cả hai trang đều gọi lệnh `finish()` sau khi thực hiện thành công, giúp màn hình tự đóng lại và quay về menu chính.

---

## 🤖 3. Chi tiết bộ Test tự động (Automation Side)

Chúng ta sử dụng mô hình **Page Object Model** để script test đọc giống như ngôn ngữ tự nhiên.

### A. Các "Tay điều khiển" (Page Objects)
*   **`AuthPage`, `LoginPage`, `RegisterPage`**: Thay vì viết code phức tạp, ta dùng các hàm như `enterUsername()`, `clickSubmit()`.
*   **Cơ chế "Chống vấp" (Synchronization):** Trong hàm nhập liệu (`type`), robot được dạy phải: *Nhập chữ -> Đợi chữ hiện lên ô -> Rồi mới làm việc tiếp*. Điều này giúp test không bao giờ bị lỗi "chưa nhập xong đã bấm nút".

### B. Kịch bản 40 Test Cases (`AuthFormTest.java`)
Đây là phần quan trọng nhất, chia làm 2 nhóm:

1.  **TC01 - TC15 (Kiểm tra lỗi & Đăng ký):**
    *   Robot cố tình nhập sai (trống, ngắn, email lỗi, pass yếu...) để xem App có bắt lỗi đúng không.
    *   **Hàm `assertError` đặc biệt:** Tôi đã bọc lệnh kiểm tra trong `try-catch`. Khi App báo lỗi đúng như mong đợi, Robot sẽ ghi nhận là **Thành công (Xanh)** thay vì báo lỗi hệ thống (Đỏ).
2.  **TC16 - TC40 (Đăng nhập & Luồng dữ liệu):**
    *   Robot thực hiện đăng nhập cho tất cả các tài khoản đã tạo ở nhóm trên.
    *   Kiểm tra tính đúng đắn khi truy xuất file `txt`.

---

## ❓ 4. Các câu hỏi thường gặp khi Review Code

**Q: Tại sao test lỗi mà kết quả vẫn hiện màu xanh (Success)?**
> **A:** Vì mục tiêu của test case đó là kiểm tra "tính năng báo lỗi". Nếu App báo đúng lỗi ta mong muốn, nghĩa là App đang chạy đúng thiết kế -> Test phải Xanh.

**Q: Tại sao phải thêm nút Back trong khi Android có nút Back hệ thống?**
> **A:** Nút Back trên giao diện giúp Appium định vị chính xác bằng ID (`btn_reg_back`), đảm bảo tính ổn định tuyệt đối trên mọi phiên bản Android, tránh việc Robot bị mất tiêu điểm (focus).

**Q: Dữ liệu test lấy từ đâu?**
> **A:** Dữ liệu hoàn toàn là "Dynamic" (Động). Tài khoản được tạo ra từ chuỗi Test Register sẽ ngay lập tức trở thành dữ liệu đầu vào cho chuỗi Test Login phía sau.

---

## 🚀 5. Cách chạy
1.  **Build App:** Chạy lệnh `./gradlew assembleDebug`.
2.  **Bật Appium:** Gõ lệnh `appium` trong terminal.
3.  **Chạy Test:** Chuột phải vào `AuthFormTest.java` -> **Run**.

---
*Tài liệu này được biên soạn bởi Automation Test Engineer.*
