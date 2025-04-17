package ra.edu.presntation;

import java.util.Scanner;

public class CandidateAdminUI {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ ỨNG VIÊN ===");
            System.out.println("1. Xem danh sách ứng viên");
            System.out.println("2. Khóa / mở khóa tài khoản");
            System.out.println("3. Reset mật khẩu");
            System.out.println("4. Tìm theo tên");
            System.out.println("5. Lọc theo kinh nghiệm");
            System.out.println("6. Lọc theo tuổi");
            System.out.println("7. Lọc theo giới tính");
            System.out.println("8. Lọc theo công nghệ");
            System.out.println("9. Quay lại");
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
                    break;
                case "8":
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Chọn sai, nhập lại!");
            }
        }
    }
}
