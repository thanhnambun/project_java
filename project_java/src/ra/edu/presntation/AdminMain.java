package ra.edu.presntation;

import ra.edu.MainApplication;

import java.util.Scanner;

public class AdminMain {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println("\n=========== MENU QUẢN TRỊ ===========");
            System.out.println("1. Quản lý công nghệ");
            System.out.println("2. Quản lý ứng viên");
            System.out.println("3. Quản lý vị trí tuyển dụng");
            System.out.println("4. Quản lý đơn ứng tuyển");
            System.out.println("5. Đăng xuất");
            System.out.println("=====================================");
            System.out.print("Chọn chức năng (1-5): ");
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
                    MainApplication.logout();
                    return;
                default:
                    System.out.println("Vui lòng chọn từ 1 đến 5.");
            }
        }
    }


}

