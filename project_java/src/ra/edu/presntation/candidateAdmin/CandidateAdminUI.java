package ra.edu.presntation.candidateAdmin;

import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateStatus;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.business.service.candidate.CandidateServiceImp;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class CandidateAdminUI {
    public static void Menu(Scanner sc) {
        CandidateService candidateService = new CandidateServiceImp();
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
                    findCandidate(sc, candidateService);
                    break;
                case "2":
                    blockAccount(sc,candidateService);
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

    public static void findCandidate(Scanner sc, CandidateService candidateService) {
        int totalPages = candidateService.getTotalPages();
        int currentPage = 1;

        while (true) {
            System.out.println("\n--- Danh sách phòng ban - Trang " + currentPage + "/" + totalPages + " ---");

            List<Candidate> candidateslist = candidateService.findAll(currentPage);
            if (candidateslist.isEmpty()) {
                System.out.println("Không có phòng ban nào trong trang này.");
            } else {
                System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
                        "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
                System.out.println("-------------------------------------------------------------------------------");

                for (Candidate candidate : candidateslist) {
                    System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10.1f%n",
                            candidate.getId(),
                            candidate.getName(),
                            candidate.getEmail(),
                            candidate.getPhone(),
                            candidate.getExperience());
                }

            }
            System.out.println("\nChọn hành động:");
            System.out.println("1. Trang trước");
            System.out.println("2. Trang sau");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Đang ở trang đầu tiên.");
                    }
                    break;
                case "2":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Đang ở trang cuối cùng.");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public static Candidate finCandidateById(Scanner sc, CandidateService candidateService) {
        int id = Validator.validateInt(sc, "vui lòng nhập id của ứng viên ");
        return candidateService.findById(id);
    }

    public static void blockAccount(Scanner sc, CandidateService candidateService) {
        Candidate candidate = finCandidateById(sc, candidateService);
        while (true) {
            System.out.println("1. block tài khoản ");
            System.out.println("2. mở block tài khoản ");
            System.out.println("3. thoát");
            int choice = Validator.validateInt(sc, "vui lòng chọn ");
            switch (choice) {
                case 1:
                    candidate.setStatus(CandidateStatus.block);
                    break;
                case 2:
                    candidate.setStatus(CandidateStatus.active);
                    break;
                case 3:
                    System.exit(0);
                    candidateService.updateProfile(candidate);
                    break;
                default:
                    System.out.println("vui lòng nhập từ 1 đến 3 ");
                    break;
            }
        }
    }

    public static void resetPassword(Scanner sc, CandidateService candidateService) {
        Candidate candidate =finCandidateById(sc,candidateService);

        String new_password = PasswordGenerator.generateRandomPassword(10);
    }
}
