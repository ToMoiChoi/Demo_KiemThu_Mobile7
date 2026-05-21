# 🥎 HƯỚNG DẪN KIỂM THỬ TÍNH NĂNG BOUNCING BALLS

Tài liệu này giải thích cách chúng ta kiểm thử tính năng "Bouncing Balls" (Quả bóng nảy) trong ứng dụng ApiDemos, dành cho cả những người mới bắt đầu (newbie).

---

## 1. Tính năng Bouncing Balls là gì?
Đây là một màn hình hoạt họa đơn giản:
*   Mỗi khi bạn chạm (tap) vào màn hình, một quả bóng màu sắc sẽ xuất hiện.
*   Quả bóng sẽ nảy xuống đáy màn hình và mờ dần rồi biến mất.
*   Nền màn hình thay đổi màu sắc liên tục.

---

## 2. Thách thức khi kiểm thử (Dành cho Newbie)
Trong kiểm thử phần mềm, chúng ta thường tìm các nút (Button) hoặc ô nhập liệu (Input) bằng mã ID. Tuy nhiên, các quả bóng trong tính năng này **không phải là Button**.
*   Chúng được "vẽ" trực tiếp lên màn hình bằng code đồ họa.
*   Robot (Appium) không thể "nhìn thấy" từng quả bóng để đếm xem có bao nhiêu quả.

**Giải pháp:** Chúng ta kiểm thử bằng cách thực hiện các hành động chạm vào màn hình và xác nhận ứng dụng vẫn hoạt động ổn định, không bị treo (crash).

---

## 3. Cấu trúc Code kiểm thử

### A. Page Object (`BouncingBallsPage.java`)
Đây là "bộ điều khiển" cho tính năng này.
*   **`navigateToBouncingBalls()`**: Robot tự tìm menu "Animation" rồi bấm vào "Bouncing Balls".
*   **`tapAtPosition(x, y)`**: Robot giả lập ngón tay chạm vào một tọa độ chính xác (x, y) trên màn hình để tạo ra bóng.
*   **`getAnimationContainer()`**: Lấy vùng chứa toàn bộ hiệu ứng để kiểm tra xem nó có đang hiển thị hay không.

### B. Test Script (`BouncingBallsTest.java`)
Đây là kịch bản chạy thử:
1.  **`testPageOpens`**: Kiểm tra xem robot có mở đúng trang và vùng hoạt họa có hiện ra không.
2.  **`testMultipleTaps`**: Robot chạm liên tục vào giữa màn hình và các góc để tạo ra nhiều bóng cùng lúc.
3.  **`testRandomTaps`**: Robot chạm ngẫu nhiên 10 điểm khắp màn hình để thử độ bền của ứng dụng.

---

## 🚀 4. Cách chạy Test
1.  **Bật Appium Server** trên máy tính.
2.  **Mở Android Studio**, tìm đến file `BouncingBallsTest.java`.
3.  Chuột phải vào tên Class và chọn **Run**.

---

## 💡 Lưu ý cho Newbie
*   **Tọa độ (Coordinates):** Màn hình điện thoại giống như một bản đồ. Góc trên bên trái là (0,0). Chúng ta dùng code để tính toán điểm giữa màn hình dựa trên kích thước thật của điện thoại bạn đang dùng.
*   **Assertion (Xác nhận):** Vì không đếm được bóng, chúng ta sử dụng `Assert.assertTrue` để chắc chắn vùng chứa hiệu ứng vẫn tồn tại sau khi ném bóng liên tục. Nếu app bị văng (crash), test sẽ báo lỗi ngay lập tức.
