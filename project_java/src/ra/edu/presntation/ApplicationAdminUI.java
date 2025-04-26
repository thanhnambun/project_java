package ra.edu.presntation;

import ra.edu.business.model.application.Application;
import ra.edu.business.model.application.Progress;
import ra.edu.business.model.application.Result;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.application.ApplicationService;
import ra.edu.business.service.application.ApplicationServiceImp;
import ra.edu.utils.ConsoleColor;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationAdminUI {
    public static void Menu(Scanner sc) {
        ApplicationService applicationService = new ApplicationServiceImp();
        while (true) {
            System.out.println(ConsoleColor.CYAN + "\n===== QUẢN LÝ ĐƠN ỨNG TUYỂN =====" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "1. Danh sách đơn ứng tuyển" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "2. Tìm đơn theo vị trí tuyển dụng" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "3. Tìm đơn theo trạng thái" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "4. Cập nhật kết quả phỏng vấn" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "5. Tạo đơn ứng tuyển mới" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "6. Xóa đơn ứng tuyển" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "7. Quay lại" + ConsoleColor.RESET);
            System.out.print(ConsoleColor.BLUE + "Chọn chức năng: " + ConsoleColor.RESET);
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
                    updateInterviewResult(applicationService, sc);
                    break;
                case "7":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
            }
        }
    }

    public static void showAllApplication(Scanner sc, ApplicationService applicationService) {
        int totalPages = applicationService.getTotalApplicationPages();
        int currentPage = 1;
        int pagesize = Validator.validateInt(sc, "vui lòng nhập số lượng đơn trên 1 trang");

        while (true) {
            System.out.println(ConsoleColor.BOLD + "\nDanh sách đơn ứng tuyển:" + ConsoleColor.RESET + currentPage + "/" + totalPages + " ---");

            List<Application> applicationList = applicationService.findAllApplications(currentPage, pagesize);
            if (applicationList.isEmpty()) {
                System.out.println("Không có đơn ứng tuyển nào trong trang này.");
            } else {
                System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n",
                        ConsoleColor.CYAN + "ID" + ConsoleColor.RESET,
                        ConsoleColor.CYAN + "Ứng viên" + ConsoleColor.RESET,
                        ConsoleColor.CYAN + "Vị trí tuyển dụng" + ConsoleColor.RESET,
                        ConsoleColor.CYAN + "CV URL" + ConsoleColor.RESET,
                        ConsoleColor.CYAN + "Trạng thái" + ConsoleColor.RESET);
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
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
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
                applicationList = applicationService.filterApplicationsResult(result.name());
                break;
            default:
                System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
                return;
        }

        if (applicationList.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "Không tìm thấy đơn ứng tuyển nào phù hợp." + ConsoleColor.RESET);
            return;
        }

        System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n",
                ConsoleColor.CYAN + "ID" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "Ứng viên" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "Vị trí tuyển dụng" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "CV URL" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "Trạng thái" + ConsoleColor.RESET
        );
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
        if (application.getInterviewResult() == Result.canceled) {
            System.out.println(ConsoleColor.RED + "Đơn đã bị hủy, không thể hủy tiếp." + ConsoleColor.RESET);

            return;
        }
        String destroyReason = Validator.validateString(sc, "vui lòng nhập lý do hủy đơn ", new StringRule(255, 0));
        application.setDestroyReason(destroyReason);
        applicationService.cancelApplication(application);
        System.out.println(" đã hủy đơn thành công");
        showAllApplication(sc, applicationService);
    }

    public static void viewApplication(Scanner sc, ApplicationService applicationService) {
        Application application = findApplicationId(sc, applicationService);
        applicationService.viewApplicationDetail(application.getId());
        System.out.printf("%-5s | %-12s | %-20s | %-30s | %-12s%n",
                ConsoleColor.CYAN + "ID" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "Ứng viên" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "Vị trí tuyển dụng" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "CV URL" + ConsoleColor.RESET,
                ConsoleColor.CYAN + "Trạng thái" + ConsoleColor.RESET
        );
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5d | %-12d | %-20d | %-30s | %-12s%n",
                application.getId(),
                application.getCandidateId(),
                application.getRecruitmentPositionId(),
                application.getCvUrl() != null ? application.getCvUrl() : "N/A",
                application.getProgress().name()
        );
    }

    public static void moveToApplication(Scanner sc, ApplicationService applicationService) {
        Application application = findApplicationId(sc, applicationService);
        LocalDateTime now = LocalDateTime.now();

        if (application.getProgress() == Progress.interviewing) {
            System.out.println(ConsoleColor.RED + "Đơn đã ở trạng thái phỏng vấn, không thể cập nhật lại." + ConsoleColor.RESET);
            return;
        }

        LocalDate requestDateOnly;
        do {
            requestDateOnly = Validator.validateDate("Vui lòng nhập ngày gửi thông báo", sc);
            if (requestDateOnly.atStartOfDay().isBefore(now)) {
                System.out.println(ConsoleColor.RED + "Ngày gửi không được nhỏ hơn hiện tại." + ConsoleColor.RESET);
            }
        } while (requestDateOnly.atStartOfDay().isBefore(now));

        LocalTime requestTime = Validator.validateTime("Vui lòng nhập giờ gửi thông báo", sc);
        LocalDateTime requestDateTime = LocalDateTime.of(requestDateOnly, requestTime);

        String link = Validator.validateString(sc, "Vui lòng nhập đường dẫn tới CV: ", new StringRule(255, 0));

        LocalDate interviewDateOnly;
        do {
            interviewDateOnly = Validator.validateDate("Vui lòng nhập ngày phỏng vấn", sc);
            if (interviewDateOnly.atStartOfDay().isBefore(now)) {
                System.out.println(ConsoleColor.RED + "Ngày phỏng vấn không được nhỏ hơn hiện tại." + ConsoleColor.RESET);
            }
        } while (interviewDateOnly.atStartOfDay().isBefore(now));

        LocalTime interviewTime = Validator.validateTime("Vui lòng nhập giờ phỏng vấn", sc);
        LocalDateTime interviewDateTime = LocalDateTime.of(interviewDateOnly, interviewTime);

        application.setInterviewRequestDate(requestDateTime);
        application.setInterviewLink(link);
        application.setInterviewTime(interviewDateTime);

        applicationService.moveToInterviewing(application);
        System.out.println("Đơn đã được chuyển sang chế độ phỏng vấn.");
    }


    public static void updateInterviewResult(ApplicationService applicationService, Scanner sc) {
        Application application = findApplicationId(sc, applicationService);

        if (application.getProgress() != Progress.interviewing) {
            System.out.println(ConsoleColor.RED + "Đơn không ở trạng thái phỏng vấn, không thể cập nhật kết quả." + ConsoleColor.RESET);
            return;
        }

        if (application.getInterviewResult() != null) {
            System.out.println(ConsoleColor.RED + "Đơn chưa có phản hồi từ ứng viên, không thể cập nhật lại." + ConsoleColor.RESET);
            return;
        }

        Result result_status = Validator.validateStatus(sc, "vui lòng nhập kết quả phỏng vấn (ví dụ disqualified,passed,canceled) ", Result.class);
        String result_note = Validator.validateString(sc, "vui lòng nhập ghi chú của cuộc phỏng vấn ", new StringRule(255, 0));

        application.setInterviewResult(result_status);
        application.setInterviewResultNote(result_note);

        applicationService.updateInterviewResult(application);
        System.out.println("Đã cập nhật thành công kết quả phỏng vấn");

        showAllApplication(sc, applicationService);
    }
}