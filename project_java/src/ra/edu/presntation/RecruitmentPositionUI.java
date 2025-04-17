package ra.edu.presntation;

import java.util.Scanner;

public class RecruitmentPositionUI {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ VỊ TRÍ TUYỂN DỤNG ===");
            System.out.println("1. Thêm vị trí");
            System.out.println("2. Cập nhật vị trí");
            System.out.println("3. Xóa vị trí");
            System.out.println("4. Danh sách vị trí");
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
