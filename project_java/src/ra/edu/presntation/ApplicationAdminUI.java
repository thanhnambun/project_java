package ra.edu.presntation;

import java.util.Scanner;

public class ApplicationAdminUI {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ ĐƠN ỨNG TUYỂN ===");
            System.out.println("1. Danh sách đơn ứng tuyển");
            System.out.println("2. Lọc theo trạng thái và kết quả");
            System.out.println("3. Huỷ đơn ứng tuyển");
            System.out.println("4. Xem chi tiết đơn & cập nhật trạng thái xử lý");
            System.out.println("5. Chuyển sang trạng thái phỏng vấn");
            System.out.println("6. Cập nhật kết quả phỏng vấn");
            System.out.println("7. Quay lại");
            System.out.print("Chọn: ");
            switch (sc.nextLine()) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Chọn sai, nhập lại!");
            }
        }
    }
}
