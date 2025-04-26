package ra.edu.presntation.candidateAdmin;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.application.Application;
import ra.edu.business.model.application.Result;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.candidate.CandidateStatus;
import ra.edu.business.service.application.ApplicationService;
import ra.edu.business.service.application.ApplicationServiceImp;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.business.service.candidate.CandidateServiceImp;
import ra.edu.business.service.technology.TechnologyService;
import ra.edu.business.service.technology.TechnologyServiceImp;
import ra.edu.presntation.CandidateUI;
import ra.edu.presntation.TechnologyUI;
import ra.edu.utils.ConsoleColor;
import ra.edu.validate.CandidateValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CandidateAdminUI {
    public static void Menu(Scanner sc) {
        CandidateService candidateService = new CandidateServiceImp();
        TechnologyService technologyService = new TechnologyServiceImp();
        ApplicationService applicationService = new ApplicationServiceImp();

        while (true) {
            System.out.println(ConsoleColor.BLUE + "\n=== QUẢN LÝ ỨNG VIÊN ===" + ConsoleColor.RESET);
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
                    blockAccount(sc, candidateService, applicationService);
                    break;
                case "3":
                    resetPassword(sc, candidateService);
                    break;
                case "4":
                    finCandidateByName(sc, candidateService);
                    break;
                case "5":
                    finCandidateByExperience(sc, candidateService);
                    break;
                case "6":
                    finCandidateByAge(sc, candidateService);
                    break;
                case "7":
                    finCandidateByGender(sc, candidateService);
                    break;
                case "8":
                    finCandidateByTechnology(sc, candidateService, technologyService);
                    break;
                case "9":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Chọn sai, nhập lại!" + ConsoleColor.RESET);
            }
        }
    }


    public static void findCandidate(Scanner sc, CandidateService candidateService) {
        int totalPages = candidateService.getTotalPages();
        int currentPage = 1;
        int pageSize = Validator.validateInt(sc, "vui lòng nhập số lượng ứng viên trên 1 trang");

        while (true) {
            System.out.println(ConsoleColor.BLUE + "\n--- Danh sách ứng viên - Trang " + currentPage + "/" + totalPages + " ---" + ConsoleColor.RESET);

            List<Candidate> candidateslist = candidateService.findAll(currentPage, pageSize);
            if (candidateslist.isEmpty()) {
                System.out.println(ConsoleColor.RED + "Không có ứng viên nào trong trang này." + ConsoleColor.RESET);
            } else {
                System.out.printf(ConsoleColor.BLUE + "%-5s | %-20s | %-25s | %-15s | %-10s%n" + ConsoleColor.RESET,
                        "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
                System.out.println("-------------------------------------------------------------------------------");

                for (Candidate candidate : candidateslist) {
                    System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
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
                        System.out.println(ConsoleColor.RED + "Đang ở trang đầu tiên." + ConsoleColor.RESET);
                    }
                    break;
                case "2":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println(ConsoleColor.RED + "Đang ở trang cuối cùng." + ConsoleColor.RESET);
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
                    break;
            }
        }
    }


    public static Candidate finCandidateById(Scanner sc, CandidateService candidateService) {
        Candidate candidate = null;
        do {
            int id = Validator.validateInt(sc, "Vui lòng nhập ID của ứng viên: ");
            candidate = candidateService.findById(id);
            if (candidate == null) {
                System.out.println(ConsoleColor.RED + "Không tìm thấy ứng viên với ID: " + id + ", vui lòng thử lại!" + ConsoleColor.RESET);
            }
        } while (candidate == null);
        return candidate;
    }

    public static void blockAccount(Scanner sc, CandidateService candidateService, ApplicationService applicationService) {
        Candidate candidate = finCandidateById(sc, candidateService);
        System.out.println(ConsoleColor.YELLOW + "Vui lòng lựa chọn" + ConsoleColor.RESET);
        System.out.println("1. Block tài khoản");
        System.out.println("2. Mở block tài khoản");
        System.out.println("3. Thoát");

        int choice = Validator.validateInt(sc, "Vui lòng chọn: ");
        switch (choice) {
            case 1:
                candidate.setStatus(CandidateStatus.block);
                candidateService.blockCandidate(candidate, Result.disqualified.name(), LocalDateTime.now());
                System.out.println(ConsoleColor.RED + ">> Đã khóa tài khoản!" + ConsoleColor.RESET);
                break;
            case 2:
                candidate.setStatus(CandidateStatus.active);
                candidateService.blockCandidate(candidate, null, null);
                System.out.println(ConsoleColor.GREEN + ">> Đã mở khóa tài khoản!" + ConsoleColor.RESET);
                break;
            case 3:
                candidateService.updateProfile(candidate);
                return;
            default:
                System.out.println(ConsoleColor.RED + "Vui lòng nhập từ 1 đến 3!" + ConsoleColor.RESET);
                return;
        }

        System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                candidate.getId(),
                candidate.getName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getExperience());
    }

    public static void resetPassword(Scanner sc, CandidateService candidateService) {
        Candidate candidate = finCandidateById(sc, candidateService);
        Account account = candidateService.findAccountById(candidate.getId());
        String new_password = PasswordGenerator.generateRandomPassword(10);
        account.setPassword(new_password);
        candidateService.changePassword(account);
        System.out.println(ConsoleColor.GREEN + ">> Đã reset thành công mật khẩu với giá trị: "
                + ConsoleColor.YELLOW + account.getPassword() + ConsoleColor.RESET);
    }

    public static void finCandidateByName(Scanner sc, CandidateService candidateService) {
        String name = Validator.validateString(sc, "Vui lòng nhập tên ứng viên cần tìm kiếm: ", new StringRule(100, 0));
        List<Candidate> candidates = candidateService.searchByName(name);
        if (candidates.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy tên ứng viên như trên." + ConsoleColor.RESET);
            return;
        }

        System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);
        System.out.println("-------------------------------------------------------------------------------");

        for (Candidate c : candidates) {
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhone(),
                    c.getExperience());
        }
    }


    public static void finCandidateByExperience(Scanner sc, CandidateService candidateService) {
        int experience = Validator.validateInt(sc, "vui lòng nhập số năm kinh nghiệm");
        List<Candidate> candidateList = candidateService.filterByExperience(experience);
        if (candidateList.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy ứng viên có số năm kinh nghiệm như trên." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);
        for (Candidate c : candidateList) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhone(),
                    c.getExperience());
        }
    }

    public static void finCandidateByAge(Scanner sc, CandidateService candidateService) {
        int minAge = Validator.validateInt(sc, "vui lòng nhập tuổi");
        int maxAge = Validator.validateInt(sc, "vui lòng nhập tuổi");
        List<Candidate> candidateList = candidateService.filterByAge(minAge, maxAge);
        if (candidateList.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy ứng viên có số độ tuổi như trên." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);
        for (Candidate c : candidateList) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhone(),
                    c.getExperience());
        }
    }

    public static void finCandidateByGender(Scanner sc, CandidateService candidateService) {
        System.out.println("1.male ");
        System.out.println("2.female");
        System.out.println("3.other");
        System.out.println("4. thoát");
        int choice = Validator.validateInt(sc, "vui lòng lựa chon");
        String candidateGender;
        switch (choice) {
            case 1:
                candidateGender = "male";
                break;
            case 2:
                candidateGender = "female";
                break;
            case 3:
                candidateGender = "other";
                break;
            case 4:
                System.out.println("đã chon được giới tính ,vui lòng đợi một chút");
                return;
            default:
                System.out.println("vui lòng chọn từ 1 đến 4");
                return;
        }
        List<Candidate> candidateList = candidateService.filterByGender(candidateGender);
        if (candidateList.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy ứng viên với giới tính: " + candidateGender + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                    "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);

            for (Candidate candidate : candidateList) {
                System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getEmail(),
                        candidate.getPhone(),
                        candidate.getExperience());
            }
        }
    }

    public static void finCandidateByTechnology(Scanner sc, CandidateService candidateService, TechnologyService technologyService) {
        TechnologyUI.findTechnology(sc, technologyService);
        int tech_id = Validator.validateInt(sc, "vui lòng nhập id của công nghệ");
        List<Candidate> candidateList = candidateService.filterByTechnology(tech_id);
        if (candidateList.isEmpty()) {
            System.err.println("không tìm thấy ứng viên có sử dụng công nghệ như trên");
            return;
        }
        System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);

        for (Candidate c : candidateList) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhone(),
                    c.getExperience());
        }
    }
}
