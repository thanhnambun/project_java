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
                    blockAccount(sc, candidateService);
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
                    finCandidateByTechnology(sc,candidateService,technologyService);
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
        int pageSize = Validator.validateInt(sc,"vui long nhập số lượng ứng viên trên 1 trang");

        while (true) {
            System.out.println("\n--- Danh sách phòng ban - Trang " + currentPage + "/" + totalPages + " ---");

            List<Candidate> candidateslist = candidateService.findAll(currentPage,pageSize);
            if (candidateslist.isEmpty()) {
                System.out.println("Không có phòng ban nào trong trang này.");
            } else {
                System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
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
        ApplicationService applicationService = new ApplicationServiceImp();
        Candidate candidate = finCandidateById(sc, candidateService);
        while (true) {
            System.out.println("1. block tài khoản ");
            System.out.println("2. mở block tài khoản ");
            System.out.println("3. thoát");
            int choice = Validator.validateInt(sc, "vui lòng chọn ");
            switch (choice) {
                case 1:
                    candidate.setStatus(CandidateStatus.block);
                    List<Application> applicationList = applicationService.findApplicationByCandidateId(candidate.getId());
                    for (Application application : applicationList) {
                        application.setInterviewResult(Result.disqualified);
                        application.setDestroyAt(LocalDateTime.now());
                    }
                    break;
                case 2:
                    candidate.setStatus(CandidateStatus.active);
                    break;
                case 3:
                    candidateService.updateProfile(candidate);
                    return;
                default:
                    System.out.println("vui lòng nhập từ 1 đến 3 ");
                    break;
            }
        }
    }

    public static void resetPassword(Scanner sc, CandidateService candidateService) {
        Candidate candidate = finCandidateById(sc, candidateService);
        Account account = candidateService.findAccountById(candidate.getId());
        String new_password = PasswordGenerator.generateRandomPassword(10);
        account.setPassword(new_password);
        candidateService.changePassword(account);
        System.out.println("đã reset thành công mật khẩu với giá trị " + account.getPassword());
    }

    public static void finCandidateByName(Scanner sc, CandidateService candidateService) {
        String name = Validator.validateString(sc, "vui lòng nhập tên ứng viên cần tìm kiếm ", new StringRule(100, 0));
        List<Candidate> candidate = candidateService.searchByName(name);
        if (candidate.isEmpty()) {
            System.err.println("không ti thấy tên ứng viên như trên");
        }
        for (Candidate c : candidate) {
            System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
                    "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10.1f%n",
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
            System.err.println("không tìm thấy ứng viên có số năm kinh nghiệm như trên");
        }
        for (Candidate c : candidateList) {
            System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
                    "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10.1f%n",
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
            System.err.println("không tìm thấy ứng viên có số tuổi như trên");
        }
        for (Candidate c : candidateList) {
            System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
                    "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10.1f%n",
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
            System.out.println("Không tìm thấy ứng viên với giới tính: " + candidateGender);
        } else {
            System.out.printf("%-5s | %-20s | %-10s | %-20s%n", "ID", "Tên", "Giới tính", "Email");
            System.out.println("-------------------------------------------------------------");
            for (Candidate candidate : candidateList) {
                System.out.printf("%-5d | %-20s | %-10s | %-20s%n",
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getGender(),
                        candidate.getEmail());
            }
        }
    }

    public static void finCandidateByTechnology(Scanner sc, CandidateService candidateService, TechnologyService technologyService) {
        TechnologyUI.findTechnology(sc,technologyService);
        int tech_id = Validator.validateInt(sc,"vui lòng nhập id của công nghệ");
        List<Candidate> candidateList = candidateService.filterByTechnology(tech_id);
        if (candidateList.isEmpty()) {
            System.err.println("không tìm thấy ứng viên có sử dụng công nghệ như trên");
        }
        for (Candidate c : candidateList) {
            System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
                    "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10.1f%n",
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhone(),
                    c.getExperience());
        }
    }
}
