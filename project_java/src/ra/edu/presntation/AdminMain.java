package ra.edu.presntation;

import ra.edu.MainApplication;
import ra.edu.presntation.candidateAdmin.CandidateAdminUI;
import ra.edu.utils.ConsoleColor;

import java.util.Scanner;

public class AdminMain {
    public static void Menu(Scanner sc) {
        while (true) {
            System.out.println(ConsoleColor.CYAN + "\n=========== MENU QUẢN TRỊ ===========");
            System.out.println(ConsoleColor.GREEN + "1. Quản lý công nghệ");
            System.out.println(ConsoleColor.GREEN + "2. Quản lý ứng viên");
            System.out.println(ConsoleColor.GREEN + "3. Quản lý vị trí tuyển dụng");
            System.out.println(ConsoleColor.GREEN + "4. Quản lý đơn ứng tuyển");
            System.out.println(ConsoleColor.RED + "5. Đăng xuất");
            System.out.println(ConsoleColor.CYAN + "=====================================" + ConsoleColor.RESET);
            System.out.print("Chọn chức năng (1-5): ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    TechnologyUI.Menu(sc);
                    break;
                case "2":
                    CandidateAdminUI.Menu(sc);
                    break;
                case "3":
                    RecruitmentPositionUI.Menu(sc);
                    break;
                case "4":
                    ApplicationAdminUI.Menu(sc);
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

