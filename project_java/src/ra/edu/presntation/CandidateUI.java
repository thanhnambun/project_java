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
import ra.edu.utils.ConsoleColor;
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
            System.out.println(ConsoleColor.CYAN + "\n=========== MENU ỨNG VIÊN ===========" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "1. Thay đổi thông tin cá nhân" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "2. Đổi mật khẩu" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "3. Xem danh sách đơn ứng tuyển" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "4. Xem chi tiết đơn và xác nhận phỏng vấn" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "5. Xem vị trí tuyển dụng & công nghệ liên quan" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "6. Nộp đơn ứng tuyển" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "7. Hủy đơn ứng tuyển " + ConsoleColor.RESET);
            System.out.println(ConsoleColor.RED + "8. Đăng xuất" + ConsoleColor.RESET);
            System.out.print(ConsoleColor.YELLOW + "Chọn chức năng (1-8): " + ConsoleColor.RESET);
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> updateProfile(sc, candidateService, candidate);
                case "2" -> changePassword(sc, candidateService, candidate);
                case "3" -> showApplications(candidate, applicationService, sc);
                case "4" -> showApplicationDetailAndConfirm(sc, applicationService, candidate);
                case "5" -> showPositionsAndTechnologies(sc);
                case "6" -> applyForPosition(sc, candidate, applicationService);
                case "7" -> deleteApplication(sc, applicationService, candidate);
                case "8" -> {
                    MainApplication.logout();
                    return;
                }
                default -> System.out.println(ConsoleColor.RED + "Vui lòng chọn từ 1 đến 8." + ConsoleColor.RESET);
            }
        }
    }

    private static void updateProfile(Scanner sc, CandidateService service, Candidate candidate) {
        while (true) {
            System.out.println(ConsoleColor.CYAN + "\n--- " + ConsoleColor.YELLOW + "Cập nhật hồ sơ" + ConsoleColor.CYAN + " ---" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "1. Tên" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "2. Số điện thoại" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "3. Giới tính" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "4. Mô tả bản thân" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "5. Số năm kinh nghiệm" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "6. Ngày sinh (yyyy-MM-dd)" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "7. Thoát & Lưu" + ConsoleColor.RESET);

            int choice = Validator.validateInt(sc, ConsoleColor.YELLOW + "Chọn mục cần cập nhật: " + ConsoleColor.RESET);

            switch (choice) {
                case 1:
                    candidate.setName(Validator.validateString(sc, ConsoleColor.YELLOW + "Nhập tên: " + ConsoleColor.RESET, new StringRule(100, 0)));
                    break;
                case 2:
                    candidate.setPhone(candidate.inputPhone(sc, ConsoleColor.YELLOW + "Nhập số điện thoại: " + ConsoleColor.RESET));
                    break;
                case 3:
                    candidate.setGender(Validator.validateStatus(sc, ConsoleColor.YELLOW + "Giới tính (male/female/other): " + ConsoleColor.RESET, CandidateGender.class));
                    break;
                case 4:
                    candidate.setDescription(Validator.validateString(sc, ConsoleColor.YELLOW + "Mô tả bản thân: " + ConsoleColor.RESET, new StringRule(100, 0)));
                    break;
                case 5:
                    candidate.setExperience(Validator.validateInt(sc, ConsoleColor.YELLOW + "Số năm kinh nghiệm: " + ConsoleColor.RESET));
                    break;
                case 6:
                    candidate.setDob(Date.valueOf(Validator.validateDate(ConsoleColor.YELLOW + "Ngày sinh (yyyy-MM-dd): " + ConsoleColor.RESET, sc)));
                    break;
                case 7:
                    service.updateProfile(candidate);
                    System.out.println(ConsoleColor.GREEN + "Cập nhật thông tin thành công." + ConsoleColor.RESET);
                    System.out.println(ConsoleColor.YELLOW + String.format("%-5s | %-20s | %-25s | %-15s | %-10s",
                            "ID", "Tên Sinh Viên", "Email", "Phone", "Kinh Nghiệm") + ConsoleColor.RESET);
                    System.out.println("-------------------------------------------------------------------------------");

                    System.out.printf("%-5d | %-20s | %-25s | %-15s | %-10d%n",
                            candidate.getId(),
                            candidate.getName(),
                            candidate.getEmail(),
                            candidate.getPhone(),
                            candidate.getExperience());
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Vui lòng chọn từ 1 đến 7." + ConsoleColor.RESET);
                    break;
            }
        }
    }

    private static void changePassword(Scanner sc, CandidateService service, Candidate candidate) {
        Account account = service.findAccountById(candidate.getId());

        while (true) {
            String email = Validator.validateString(sc, ConsoleColor.YELLOW + "Nhập email: " + ConsoleColor.RESET, new StringRule(100, 0));
            if (candidate.getEmail().equalsIgnoreCase(email)) {
                break;
            } else {
                System.err.println(ConsoleColor.RED + "Email không chính xác. Vui lòng nhập lại." + ConsoleColor.RESET);
            }
        }

        String oldPassword;
        while (true) {
            oldPassword = AccountValidator.validatePassword(sc, ConsoleColor.YELLOW + "Nhập mật khẩu cũ: " + ConsoleColor.RESET);
            if (account.getPassword().equals(oldPassword)) {
                break;
            } else {
                System.err.println(ConsoleColor.RED + "Mật khẩu cũ không chính xác. Vui lòng nhập lại." + ConsoleColor.RESET);
            }
        }

        String newPassword = AccountValidator.validatePassword(sc, ConsoleColor.YELLOW + "Nhập mật khẩu mới: " + ConsoleColor.RESET);

        boolean success = service.changePasswordUser(account.getId(), oldPassword, newPassword);

        if (success) {
            System.out.println(ConsoleColor.GREEN + "Đổi mật khẩu thành công." + ConsoleColor.RESET);
        } else {
            System.err.println(ConsoleColor.RED + "Đổi mật khẩu không thành công. Vui lòng thử lại sau." + ConsoleColor.RESET);
        }
    }


    private static void showApplications(Candidate candidate, ApplicationService service, Scanner sc) {
        List<Application> applications = service.findApplicationByCandidateId(candidate.getId());
        if (applications.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Bạn chưa có đơn ứng tuyển nào." + ConsoleColor.RESET);
            return;
        }

        int pageSize = Validator.validateInt(sc, ConsoleColor.YELLOW + "Vui lòng nhập số đơn ứng tuyển trên 1 trang: " + ConsoleColor.RESET);
        int totalPages = (int) Math.ceil((double) applications.size() / pageSize);
        int currentPage = 1;

        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, applications.size());

            System.out.println(ConsoleColor.CYAN + String.format("%-5s | %-20s | %-30s | %-12s", "ID đơn", "Vị trí", "CV URL", "Trạng thái") + ConsoleColor.RESET);
            System.out.println(ConsoleColor.CYAN + "------------------------------------------------------------------------------------------------------" + ConsoleColor.RESET);

            for (int i = start; i < end; i++) {
                Application app = applications.get(i);
                System.out.printf("%-5d | %-20d | %-30s | %-12s%n",
                        app.getId(), app.getRecruitmentPositionId(),
                        app.getCvUrl() != null ? app.getCvUrl() : "N/A",
                        app.getProgress().name());
            }

            System.out.printf(ConsoleColor.YELLOW + "Trang %d / %d%n" + ConsoleColor.RESET, currentPage, totalPages);
            System.out.println(ConsoleColor.YELLOW + "1. Trang trước | 2. Trang sau | 3. Thoát" + ConsoleColor.RESET);

            int choice = Validator.validateInt(sc, ConsoleColor.YELLOW + "Chọn thao tác: " + ConsoleColor.RESET);

            if (choice == 1 && currentPage > 1) currentPage--;
            else if (choice == 2 && currentPage < totalPages) currentPage++;
            else if (choice == 3) break;
            else System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
        }
    }

    private static void showApplicationDetailAndConfirm(Scanner sc, ApplicationService service, Candidate candidate) {
        List<Application> applications = service.findApplicationByCandidateId(candidate.getId());
        if (applications == null || applications.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Bạn chưa có đơn ứng tuyển nào để xem chi tiết." + ConsoleColor.RESET);
            return;
        }

        int appId = Validator.validateInt(sc, ConsoleColor.YELLOW + "Nhập ID đơn ứng tuyển: " + ConsoleColor.RESET);
        Optional<Application> application = applications.stream().filter(a -> a.getId() == appId).findFirst();

        if (application.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy đơn ứng tuyển." + ConsoleColor.RESET);
            return;
        }

        Application app = application.get();
        System.out.println(ConsoleColor.CYAN + String.format("%-5s | %-20s | %-30s | %-12s", "ID", "Vị trí", "CV URL", "Trạng thái") + ConsoleColor.RESET);
        System.out.printf("%-5d | %-20d | %-30s | %-12s%n",
                app.getId(), app.getRecruitmentPositionId(),
                app.getCvUrl() != null ? app.getCvUrl() : "N/A",
                app.getProgress().name());

        if (app.getProgress() == Progress.interviewing && app.getInterviewRequestDate() != null) {
            System.out.println(ConsoleColor.YELLOW + "Bạn có muốn xác nhận tham gia phỏng vấn?" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "1. Có" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "2. Không" + ConsoleColor.RESET);
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                app.setInterviewRequestResult("đã xác nhận phỏng vấn");
                System.out.println(ConsoleColor.GREEN + "Cập nhật xác nhận phỏng vấn thành công." + ConsoleColor.RESET);
            } else if (choice == 2) {
                app.setInterviewRequestResult("đã hủy phỏng vấn");
                System.out.println(ConsoleColor.GREEN + "Cập nhật hủy phỏng vấn thành công." + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
                return;
            }
            System.out.println("đã cập nhật thành công");
            service.updateInterviewRequestResult(app);
        }
    }

    private static void showPositionsAndTechnologies(Scanner sc) {
        RecruitmentPositionService positionService = new RecruitmentPositionServiceImp();
        RecruitmentPositionUI.findAllRecruitment(sc, positionService);

        System.out.println(ConsoleColor.YELLOW + "Bạn có muốn xem công nghệ yêu cầu của vị trí không?" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "1. Có" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "2. Không" + ConsoleColor.RESET);
        int choice = Validator.validateInt(sc, "Vui lòng chọn: ");
        if (choice != 1) {
            return;
        }

        RecruitmentPositionTechnologyService rtpService = new RecruitmentPositionTechnologyServiceImp();
        TechnologyService techService = new TechnologyServiceImp();

        int positionId = Validator.validateInt(sc, ConsoleColor.YELLOW + "Nhập ID vị trí: " + ConsoleColor.RESET);
        RecruitmentPosition position = positionService.findRecruitmentPositionById(positionId);

        if (position == null) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy vị trí." + ConsoleColor.RESET);
            return;
        }

        List<Integer> techIds = rtpService.getRecruitmentPositionTechnology(positionId);
        if (techIds == null || techIds.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Chưa có công nghệ liên quan đến vị trí này." + ConsoleColor.RESET);
            return;
        }

        System.out.println(ConsoleColor.CYAN + "Các công nghệ yêu cầu cho vị trí \"" + position.getName() + "\":" + ConsoleColor.RESET);
        for (int techId : techIds) {
            Technology tech = techService.findTechnologyById(techId);
            System.out.println(ConsoleColor.GREEN + "- " + tech.getName() + " (ID: " + tech.getId() + ")" + ConsoleColor.RESET);
        }
    }

    private static void applyForPosition(Scanner sc, Candidate candidate, ApplicationService service) {
        RecruitmentPositionService positionService = new RecruitmentPositionServiceImp();
        int positionId = Validator.validateInt(sc, ConsoleColor.YELLOW + "Nhập ID vị trí muốn ứng tuyển: " + ConsoleColor.RESET);
        RecruitmentPosition position = positionService.findRecruitmentPositionById(positionId);

        if (position == null) {
            System.out.println(ConsoleColor.RED + "Vị trí không tồn tại." + ConsoleColor.RESET);
            return;
        }

        CandidateUI.candidateChoiceTechnology(sc, candidate, position);

        Application existingApp = service.findByCandidateAndPosition(candidate.getId(), positionId);
        if (existingApp != null) {
            if (existingApp.getInterviewResult() != null && existingApp.getInterviewResult() != Result.disqualified) {
                System.err.println(ConsoleColor.RED + "Bạn đã ứng tuyển vào vị trí này rồi và đang trong quá trình xử lý." + ConsoleColor.RESET);
                return;
            }
        }

        String cvLink = Validator.validateString(sc, ConsoleColor.YELLOW + "Nhập đường dẫn đến CV: " + ConsoleColor.RESET, new StringRule(100, 0));

        Application newApp = new Application();
        newApp.setCandidateId(candidate.getId());
        newApp.setRecruitmentPositionId(positionId);
        newApp.setCvUrl(cvLink);

        service.saveApplication(newApp);
        System.out.println(ConsoleColor.GREEN + "Nộp đơn thành công cho vị trí: " + position.getName() + ConsoleColor.RESET);
    }

    public static void candidateChoiceTechnology(Scanner sc, Candidate candidate, RecruitmentPosition position) {
        CandidateTechnologyService candidateTechnologyService = new CandidateTechnologyServiceImp();
        RecruitmentPositionTechnologyService rtpService = new RecruitmentPositionTechnologyServiceImp();
        List<Integer> techIds = rtpService.getRecruitmentPositionTechnology(position.getId());

        while (true) {
            int technologyId = Validator.validateInt(sc, ConsoleColor.YELLOW + "Vui lòng nhập ID công nghệ bạn sử dụng:" + ConsoleColor.RESET);
            Optional<Integer> technology = techIds.stream().filter(c -> c == technologyId).findFirst();

            if (technology.isEmpty()) {
                System.out.println(ConsoleColor.RED + "Vị trí bạn chọn không yêu cầu công nghệ với id bên trên." + ConsoleColor.RESET);
            } else {
                candidateTechnologyService.saveCandidateTechnology(candidate.getId(), technologyId);
                System.out.println(ConsoleColor.GREEN + "Đã thêm công nghệ thành công." + ConsoleColor.RESET);
            }

            System.out.println(ConsoleColor.CYAN + "Bạn có muốn thêm công nghệ nữa không?" + ConsoleColor.RESET);
            System.out.println("1. Có");
            System.out.println("2. Không");

            int choice = Validator.validateInt(sc, ConsoleColor.YELLOW + "Vui lòng lựa chọn:" + ConsoleColor.RESET);
            if (choice != 1) {
                break;
            }
        }
    }

    public static void deleteApplication(Scanner sc, ApplicationService service, Candidate candidate) {
        List<Application> applications = service.findApplicationByCandidateId(candidate.getId());
        if (applications.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Bạn chưa có đơn ứng tuyển nào." + ConsoleColor.RESET);
            return;
        }

        Application selectedApp = null;
        while (true) {
            int applicationId = Validator.validateInt(sc, ConsoleColor.YELLOW + "Vui lòng nhập mã đơn cần xóa: " + ConsoleColor.RESET);
            Optional<Application> checkID = applications.stream()
                    .filter(app -> app.getId() == applicationId)
                    .findFirst();

            if (checkID.isPresent()) {
                selectedApp = checkID.get();
                break;
            } else {
                System.out.println(ConsoleColor.RED + "Không tìm thấy đơn ứng tuyển với mã đã nhập. Vui lòng thử lại." + ConsoleColor.RESET);
            }
        }

        if (selectedApp.getProgress() == Progress.pending) {
            service.deleteApplication(selectedApp.getId());
            System.out.println(ConsoleColor.GREEN + "Xoá đơn ứng tuyển thành công." + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.RED + "Chỉ có thể xoá đơn ở trạng thái *pending*." + ConsoleColor.RESET);
        }
    }

}
