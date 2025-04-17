package ra.edu.presntation;

import java.util.Scanner;

public class TechnologyUI {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ CÔNG NGHỆ ===");
            System.out.println("1. Xem danh sách công nghệ");
            System.out.println("2. Thêm công nghệ");
            System.out.println("3. Cập nhật công nghệ");
            System.out.println("4. Xoá công nghệ");
            System.out.println("5. Quay lại");
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
                    return;
                default:
                    System.out.println("Chọn sai, nhập lại!");
            }
        }
    }
}
