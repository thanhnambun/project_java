package ra.edu.presntation;

import ra.edu.MainApplication;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.application.Application;
import ra.edu.business.model.application.Progress;
import ra.edu.business.model.application.Result;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.recruitmenPosition.RecruitmentPosition;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.application.ApplicationService;
import ra.edu.business.service.application.ApplicationServiceImp;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.business.service.candidate.CandidateServiceImp;
import ra.edu.business.service.candidateTechnology.CandidateTechnologyService;
import ra.edu.business.service.candidateTechnology.CandidateTechnologyServiceImp;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionService;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionServiceImp;
import ra.edu.business.service.recruitmentPositionTechnology.RecruitmentPositionTechnologyService;
import ra.edu.business.service.recruitmentPositionTechnology.RecruitmentPositionTechnologyServiceImp;
import ra.edu.business.service.technology.TechnologyService;
import ra.edu.business.service.technology.TechnologyServiceImp;
import ra.edu.presntation.RecruitmentPositionUI;
import ra.edu.validate.AccountValidator;
import ra.edu.validate.CandidateValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CandidateUI {

    public static void menu(Scanner sc, int candidateId) {
        CandidateService candidateService = new CandidateServiceImp();
        ApplicationService applicationService = new ApplicationServiceImp();
        Candidate candidate = candidateService.findById(candidateId);

        while (true) {
            System.out.println("\n=========== MENU ỨNG VIÊN ===========");
            System.out.println("1. Thay đổi thông tin cá nhân");
            System.out.println("2. Đổi mật khẩu");
            System.out.println("3. Xem danh sách đơn ứng tuyển");
            System.out.println("4. Xem chi tiết đơn và xác nhận phỏng vấn");
            System.out.println("5. Xem vị trí tuyển dụng & công nghệ liên quan");
            System.out.println("6. Nộp đơn ứng tuyển");
            System.out.println("7. Hủy đơn ứng tuyển ");
            System.out.println("8. Đăng xuất");
            System.out.print("Chọn chức năng (1-7): ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> updateProfile(sc, candidateService, candidate);
                case "2" -> changePassword(sc, candidateService, candidate);
                case "3" -> showApplications(candidate, applicationService, sc);
                case "4" -> showApplicationDetailAndConfirm(sc, applicationService);
                case "5" -> showPositionsAndTechnologies(sc);
                case "6" -> applyForPosition(sc, candidate, applicationService);
                case "7"-> deleteApplication(sc,applicationService,candidate);
                case "8" -> {
                    MainApplication.logout();
                    return;
                }
                default -> System.out.println("Vui lòng chọn từ 1 đến 7.");
            }
        }
    }

    private static void updateProfile(Scanner sc, CandidateService service, Candidate candidate) {
        while (true) {
            System.out.println("\n--- Cập nhật hồ sơ ---");
            System.out.println("1. Tên");
            System.out.println("2. Số điện thoại");
            System.out.println("3. Giới tính");
            System.out.println("4. Mô tả bản thân");
            System.out.println("5. Số năm kinh nghiệm");
            System.out.println("6. Ngày sinh (yyyy-MM-dd)");
            System.out.println("7. Thoát & Lưu");

            int choice = Validator.validateInt(sc, "Chọn mục cần cập nhật");

            switch (choice) {
                case 1:
                    candidate.setName(Validator.validateString(sc, "Nhập tên: ", new StringRule(100, 0)));
                    break;
                case 2:
                    candidate.setPhone(candidate.inputPhone(sc, "Nhập số điện thoại: "));
                    break;
                case 3:
                    candidate.setGender(Validator.validateStatus(sc, "Giới tính (male/female/other): ", CandidateGender.class));
                    break;
                case 4:
                    candidate.setDescription(Validator.validateString(sc, "Mô tả bản thân: ", new StringRule(100, 0)));
                    break;
                case 5:
                    candidate.setExperience(Validator.validateInt(sc, "Số năm kinh nghiệm: "));
                    break;
                case 6:
                    candidate.setDob(Date.valueOf(Validator.validateDate("Ngày sinh (yyyy-MM-dd): ", sc)));
                    break;
                case 7:
                    service.updateProfile(candidate);
                    System.out.println("Cập nhật thông tin thành công.");
                    System.out.printf("%-5s | %-20s | %-25s | %-15s | %-10s%n",
                            "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm");
                    System.out.println("-------------------------------------------------------------------------------");

                    System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                            candidate.getId(),
                            candidate.getName(),
                            candidate.getEmail(),
                            candidate.getPhone(),
                            candidate.getExperience());
                    return;
                default:
                    System.out.println("Vui lòng chọn từ 1 đến 7.");
                    break;
            }
        }
    }

    private static void changePassword(Scanner sc, CandidateService service, Candidate candidate) {
        Account account = service.findAccountById(candidate.getId());
        while (true) {
            String email = Validator.validateString(sc, "Nhập email: ", new StringRule(100, 0));
            if (candidate.getEmail().equalsIgnoreCase(email)) {
                break;
            } else {
                System.err.println("Email không chính xác. Vui lòng nhập lại.");
            }
        }
        String oldPassword;
        while (true) {
            oldPassword = AccountValidator.validatePassword(sc, "Nhập mật khẩu cũ: ");
            if (account.getPassword().equals(oldPassword)) {
                break;
            } else {
                System.err.println("Mật khẩu cũ không chính xác. Vui lòng nhập lại.");
            }
        }
        String newPassword = AccountValidator.validatePassword(sc, "Nhập mật khẩu mới: ");
        boolean success = service.changePasswordUser(account.getId(), oldPassword, newPassword);

        if (success) {
            System.out.println("Đổi mật khẩu thành công.");
        } else {
            System.err.println("Đổi mật khẩu không thành công. Vui lòng thử lại sau.");
        }
    }


    private static void showApplications(Candidate candidate, ApplicationService service, Scanner sc) {
        List<Application> applications = service.findApplicationByCandidateId(candidate.getId());
        if (applications.isEmpty()) {
            System.out.println("Bạn chưa có đơn ứng tuyển nào.");
            return;
        }

        int pageSize = Validator.validateInt(sc, "vui lòng nhập số đơn ứng tuyển trên 1 trang");
        int totalPages = (int) Math.ceil((double) applications.size() / pageSize);
        int currentPage = 1;

        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, applications.size());

            System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n", "ID", "Ứng viên", "Vị trí", "CV URL", "Trạng thái");
            System.out.println("------------------------------------------------------------------------------------------------------");

            for (int i = start; i < end; i++) {
                Application app = applications.get(i);
                System.out.printf("%-5d | %-12d | %-20d | %-30s | %-12s%n",
                        app.getId(), app.getCandidateId(), app.getRecruitmentPositionId(),
                        app.getCvUrl() != null ? app.getCvUrl() : "N/A",
                        app.getProgress().name());
            }

            System.out.printf("Trang %d / %d%n", currentPage, totalPages);
            System.out.println("1. Trang trước | 2. Trang sau | 3. Thoát");
            int choice = Validator.validateInt(sc, "Chọn thao tác: ");

            if (choice == 1 && currentPage > 1) currentPage--;
            else if (choice == 2 && currentPage < totalPages) currentPage++;
            else if (choice == 3) break;
            else System.out.println("Lựa chọn không hợp lệ.");
        }
    }


    private static void showApplicationDetailAndConfirm(Scanner sc, ApplicationService service) {
        int appId = Validator.validateInt(sc, "Nhập ID đơn ứng tuyển: ");
        Application app = service.findApplicationById(appId);

        if (app == null) {
            System.out.println("Không tìm thấy đơn ứng tuyển.");
            return;
        }

        System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n", "ID", "Ứng viên", "Vị trí", "CV URL", "Trạng thái");
        System.out.printf("%-5d | %-12d | %-20d | %-30s | %-12s%n",
                app.getId(), app.getCandidateId(), app.getRecruitmentPositionId(),
                app.getCvUrl() != null ? app.getCvUrl() : "N/A",
                app.getProgress().name());

        if (app.getProgress() == Progress.interviewing && app.getInterviewRequestDate() != null) {
            System.out.println("Bạn có muốn xác nhận tham gia phỏng vấn?");
            System.out.println("1. Có");
            System.out.println("2. Không");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                app.setInterviewRequestResult("đã xác nhận phỏng vấn");
            } else if (choice == 2) {
                app.setInterviewRequestResult("đã hủy phỏng vấn");
            } else {
                System.out.println("Lựa chọn không hợp lệ.");
                return;
            }

            service.updateInterviewRequestResult(app);
            System.out.println("Cập nhật xác nhận phỏng vấn thành công.");
        }
    }

    private static void showPositionsAndTechnologies(Scanner sc) {
        RecruitmentPositionService positionService = new RecruitmentPositionServiceImp();
        RecruitmentPositionUI.findAllRecruitment(sc, positionService);

        System.out.println("Bạn có muốn xem công nghệ yêu cầu của vị trí không?");
        System.out.println("1. Có");
        System.out.println("2. Không");
        int choice = Validator.validateInt(sc, "Vui lòng chọn: ");
        if (choice != 1) {
            return;
        }

        RecruitmentPositionTechnologyService rtpService = new RecruitmentPositionTechnologyServiceImp();
        TechnologyService techService = new TechnologyServiceImp();

        int positionId = Validator.validateInt(sc, "Nhập ID vị trí: ");
        RecruitmentPosition position = positionService.findRecruitmentPositionById(positionId);

        if (position == null) {
            System.out.println("Không tìm thấy vị trí.");
            return;
        }

        List<Integer> techIds = rtpService.getRecruitmentPositionTechnology(positionId);
        if (techIds == null || techIds.isEmpty()) {
            System.out.println("Chưa có công nghệ liên quan đến vị trí này.");
            return;
        }

        System.out.println("Các công nghệ yêu cầu cho vị trí \"" + position.getName() + "\":");
        for (int techId : techIds) {
            Technology tech = techService.findTechnologyById(techId);
            System.out.println("- " + tech.getName() + " (ID: " + tech.getId() + ")");
        }
    }


    private static void applyForPosition(Scanner sc, Candidate candidate, ApplicationService service) {
        RecruitmentPositionService positionService = new RecruitmentPositionServiceImp();
        TechnologyService techService = new TechnologyServiceImp();
        int positionId = Validator.validateInt(sc, "Nhập ID vị trí muốn ứng tuyển: ");
        RecruitmentPosition position = positionService.findRecruitmentPositionById(positionId);
        if (position == null) {
            System.out.println("Vị trí không tồn tại.");
            return;
        }
        CandidateUI.candidateChoiceTechnology(sc, candidate, techService);
        Application existingApp = service.findByCandidateAndPosition(candidate.getId(), positionId);

        if (existingApp != null) {
            if (existingApp.getInterviewResult() != null && existingApp.getInterviewResult() != Result.disqualified) {
                System.err.println("Bạn đã ứng tuyển vào vị trí này rồi và đang trong quá trình xử lý.");
                return;
            }
        }

        String cvLink = Validator.validateString(sc, "Nhập đường dẫn đến CV: ", new StringRule(100, 0));

        Application newApp = new Application();
        newApp.setCandidateId(candidate.getId());
        newApp.setRecruitmentPositionId(positionId);
        newApp.setCvUrl(cvLink);

        service.saveApplication(newApp);
        System.out.println("Nộp đơn thành công cho vị trí: " + position.getName());
    }

    public static void candidateChoiceTechnology(Scanner sc, Candidate candidate, TechnologyService techService) {
        CandidateTechnologyService candidateTechnologyService = new CandidateTechnologyServiceImp();

        while (true) {
            int technologyId = Validator.validateInt(sc, "Vui lòng nhập ID công nghệ bạn sử dụng:");
            Technology technology = techService.findTechnologyById(technologyId);

            if (technology == null) {
                System.out.println("Không tìm thấy công nghệ với ID trên.");
            } else {
                candidateTechnologyService.saveCandidateTechnology(candidate.getId(), technologyId);
                System.out.println("Đã thêm công nghệ thành công.");
            }

            System.out.println("Bạn có muốn thêm công nghệ nữa không?");
            System.out.println("1. Có");
            System.out.println("2. Không");

            int choice = Validator.validateInt(sc, "Vui lòng lựa chọn:");
            if (choice != 1) {
                break;
            }
        }
    }
    public static void deleteApplication(Scanner sc, ApplicationService service,Candidate candidate) {
        List<Application> applications = service.findApplicationByCandidateId(candidate.getId());
        if (applications.isEmpty()) {
            System.out.println("Bạn chưa có đơn ứng tuyển nào.");
            return;
        }
        int  applicationId = Validator.validateInt(sc,"vui lòng nhập mã đơn cần xóa ");
        Optional<Application > checkID = applications.stream().filter(app-> app.getId() == applicationId).findFirst();
        if (checkID.isPresent()) {
            if (checkID.get().getProgress() == Progress.pending) {
                service.deleteApplication(applicationId);
                System.out.println("Xoá đơn ứng tuyển thành công.");
            } else {
                System.out.println("Chỉ có thể xoá đơn ở trạng thái *pending*.");
            }
        } else {
            System.out.println("Không tìm thấy đơn ứng tuyển với mã đã nhập.");
        }
    }

}
