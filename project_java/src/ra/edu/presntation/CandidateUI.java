package ra.edu.presntation;

import ra.edu.MainApplication;

import java.util.Scanner;

public class CandidateUI {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println("\n=========== MENU ỨNG VIÊN ===========");
            System.out.println("1. Thay đổi thông tin cá nhân");
            System.out.println("2. Đổi mật khẩu");
            System.out.println("3. Xem đơn ứng tuyển của bạn");
            System.out.println("4. Xem chi tiết đơn và xác nhận phỏng vấn (nếu có)");
            System.out.println("5. Xem & nộp đơn tuyển dụng");
            System.out.println("6. Đăng xuất");
            System.out.println("=====================================");
            System.out.print("Chọn chức năng (1-6): ");
            String choice = sc.nextLine();
            switch (choice) {
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
                    MainApplication.logout();
                    return;
                default:
                    System.out.println("Vui lòng chọn từ 1 đến 6.");
            }
        }
    }
}
