package ra.edu.presntation;

import ra.edu.business.model.application.Application;
import ra.edu.business.model.application.Progress;
import ra.edu.business.model.application.Result;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.application.ApplicationService;
import ra.edu.business.service.application.ApplicationServiceImp;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationAdminUI {
    public static void Menu(Scanner sc) {
        ApplicationService applicationService = new ApplicationServiceImp();
        while (true) {
            System.out.println("\n=== QUẢN LÝ ĐƠN ỨNG TUYỂN ===");
            System.out.println("1. Danh sách đơn ứng tuyển");
            System.out.println("2. Lọc theo trạng thái và kết quả");
            System.out.println("3. Huỷ đơn ứng tuyển");
            System.out.println("4. Xem chi tiết đơn & cập nhật trạng thái xử lý");
            System.out.println("5. Chuyển sang trạng thái phỏng vấn");
            System.out.println("6. Cập nhật kết quả phỏng vấn");
            System.out.println("7. Quay lại");
            System.out.print("Chọn: ");
            switch (sc.nextLine()) {
                case "1":
                    showAllApplication(sc, applicationService);
                    break;
                case "2":
                    findApplication(sc, applicationService);
                    break;
                case "3":
                    cancelApplication(sc, applicationService);
                    break;
                case "4":
                    viewApplication(sc, applicationService);
                    break;
                case "5":
                    moveToApplication(sc, applicationService);
                    break;
                case "6":
                    updateInterviewResult(applicationService,sc);
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Chọn sai, nhập lại!");
            }
        }
    }

    public static void showAllApplication(Scanner sc, ApplicationService applicationService) {
        int totalPages = applicationService.getTotalApplicationPages();
        int currentPage = 1;
        int pagesize = Validator.validateInt(sc,"vui lòng nhập số lượng đơn trên 1 trang");

        while (true) {
            System.out.println("\n--- Danh sách đơn ứng tuyển - Trang " + currentPage + "/" + totalPages + " ---");

            List<Application> applicationList = applicationService.findAllApplications(currentPage,pagesize);
            if (applicationList.isEmpty()) {
                System.out.println("Không có đơn ứng tuyển nào trong trang này.");
            } else {
                System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n", "ID", "Ứng viên", "Vị trí tuyển dụng", "CV URL", "Trạng thái");
                System.out.println("----------------------------------------------------------------------------------------------------------");

                for (Application application : applicationList) {
                    System.out.printf("%-5d | %-12d | %-20d | %-30s | %-12s%n",
                            application.getId(),
                            application.getCandidateId(),
                            application.getRecruitmentPositionId(),
                            application.getCvUrl() != null ? application.getCvUrl() : "N/A",
                            application.getProgress().name()
                    );
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

    public static void findApplication(Scanner sc, ApplicationService applicationService) {
        System.out.println("Lọc đơn ứng tuyển theo:");
        System.out.println("1. Trạng thái");
        System.out.println("2. Kết quả");
        int choice = Validator.validateInt(sc, "Vui lòng chọn (1 hoặc 2): ");

        List<Application> applicationList = new ArrayList<>();

        switch (choice) {
            case 1:
                Progress progress = Validator.validateStatus(sc, "Vui lòng nhập trạng thái (ví dụ: pending, interviewing, etc.):", Progress.class);
                applicationList = applicationService.filterApplicationsProgress(progress.name());
                break;
            case 2:
                Result result = Validator.validateStatus(sc, "Vui lòng nhập kết quả (ví dụ: passed, disqualified, etc.):", Result.class);
                applicationList = applicationService.filterApplicationsResult( result.name());
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        if (applicationList.isEmpty()) {
            System.out.println("Không tìm thấy đơn ứng tuyển nào phù hợp.");
            return;
        }

        System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n", "ID", "Ứng viên", "Vị trí tuyển dụng", "CV URL", "Trạng thái");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        for (Application application : applicationList) {
            System.out.printf("%-5d | %-12d | %-20d | %-30s | %-12s%n",
                    application.getId(),
                    application.getCandidateId(),
                    application.getRecruitmentPositionId(),
                    application.getCvUrl() != null ? application.getCvUrl() : "N/A",
                    application.getProgress().name()
            );
        }
    }
    
    public static Application findApplicationId(Scanner sc, ApplicationService applicationService) {
        int applicationId = Validator.validateInt(sc, "vui lòng nhập id của đơn ứng tuyển");
        Application application = applicationService.findApplicationById(applicationId);
        if (application == null) {
            System.err.println("không tìm thấy id của đơn ");
        }
        return application;
    }

    public static void cancelApplication(Scanner sc, ApplicationService applicationService) {
        Application application = findApplicationId(sc, applicationService);
        String destroyReason = Validator.validateString(sc, "vui lòng nhập lý do hủy đơn ", new StringRule(255, 0));
        application.setDestroyReason(destroyReason);
        applicationService.cancelApplication(application);
        System.out.println(" đã hủy đơn thành công");
    }

    public static void viewApplication(Scanner sc, ApplicationService applicationService) {
        Application application = findApplicationId(sc, applicationService);
        applicationService.viewApplicationDetail(application.getId());
        System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n", "ID", "Ứng viên", "Vị trí tuyển dụng", "CV URL", "Trạng thái");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5d | %-12d | %-20d | %-30s | %-12s%n",
                application.getId(),
                application.getCandidateId(),
                application.getRecruitmentPositionId(),
                application.getCvUrl() != null ? application.getCvUrl() : "N/A",
                application.getProgress()
        );
    }

    public static void moveToApplication(Scanner sc, ApplicationService applicationService) {
        Application application = findApplicationId(sc, applicationService);
        LocalDateTime request_date = Validator.validateDate("vui lòng nhập ngày gửi thông báo ", sc).atStartOfDay();
        String link = Validator.validateString(sc, "vui lòng nhập đường dẫn tới cv", new StringRule(255, 0));
        LocalDateTime time = Validator.validateDate("vui lòng nhập ngày phỏng vấn ", sc).atStartOfDay();
        application.setInterviewRequestDate(request_date);
        application.setInterviewLink(link);
        application.setInterviewTime(time);
        applicationService.moveToInterviewing(application);
        System.out.println(" đơn đã được chuyển sang chế độ phỏng vấn ");
    }

    public static void updateInterviewResult(ApplicationService applicationService, Scanner sc) {
        Application application = findApplicationId(sc, applicationService);
        Result result_status = Validator.validateStatus(sc, "vui lòng nhập kết quả phỏng vấn ", Result.class);
        String result_note = Validator.validateString(sc, "vui lòng nhập ghi chú của cuộc phỏng vấn ", new StringRule(255, 0));
        application.setInterviewResult(result_status);
        application.setInterviewResultNote(result_note);
        applicationService.updateInterviewResult(application);
        System.out.println(" đã cập nhật thành công kết quả phỏng vấn");
    }
}
