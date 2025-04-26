package ra.edu.presntation;

import ra.edu.business.dao.candidate.CandidateDaoImp;
import ra.edu.business.model.Status;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.recruitmenPosition.RecruitmentPosition;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.business.service.candidate.CandidateServiceImp;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionService;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionServiceImp;
import ra.edu.business.service.recruitmentPositionTechnology.RecruitmentPositionTechnologyService;
import ra.edu.business.service.recruitmentPositionTechnology.RecruitmentPositionTechnologyServiceImp;
import ra.edu.business.service.technology.TechnologyService;
import ra.edu.business.service.technology.TechnologyServiceImp;
import ra.edu.utils.ConsoleColor;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class RecruitmentPositionUI {
    public static void Menu(Scanner sc) {
        RecruitmentPositionService recruitmentPositionService = new RecruitmentPositionServiceImp();

        while (true) {
            System.out.println(ConsoleColor.CYAN + "\n=== QUẢN LÝ VỊ TRÍ TUYỂN DỤNG ===" + ConsoleColor.RESET);
            System.out.println("1. Danh sách vị trí");
            System.out.println("2. Thêm vị trí");
            System.out.println("3. Cập nhật vị trí");
            System.out.println("4. Xóa vị trí");
            System.out.println("5. Quay lại");
            System.out.print(ConsoleColor.YELLOW + "Chọn: " + ConsoleColor.RESET);

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    findAllRecruitment(sc, recruitmentPositionService);
                    break;
                case "2":
                    saveRecruitment(sc, recruitmentPositionService);
                    break;
                case "3":
                    updateRecruitment(sc, recruitmentPositionService);
                    break;
                case "4":
                    deleteRecruitment(sc, recruitmentPositionService);
                    break;
                case "5":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Chọn sai, vui lòng nhập lại!" + ConsoleColor.RESET);
            }
        }
    }

    public static void findAllRecruitment(Scanner sc, RecruitmentPositionService recruitmentPositionService) {
        int pageSize = Validator.validateInt(sc, "Vui lòng nhập số lượng vị trí tuyển dụng trên 1 trang: ");
        int totalItems = recruitmentPositionService.getTotalPagesRecruitmentPosition();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int currentPage = 1;

        while (true) {
            System.out.println(ConsoleColor.CYAN + "\n--- Danh sách vị trí tuyển dụng - Trang " + currentPage + "/" + totalPages + " ---" + ConsoleColor.RESET);

            List<RecruitmentPosition> positions = recruitmentPositionService.findAll(currentPage, pageSize);
            if (positions.isEmpty()) {
                System.out.println(ConsoleColor.RED + "Không có vị trí nào trong trang này." + ConsoleColor.RESET);
            } else {
                System.out.printf("%-5s | %-25s | %-50s | %-10s%n", "ID", "Tên vị trí", "Mô tả", "Trạng thái");
                System.out.println("---------------------------------------------------------------------------------------------");
                for (RecruitmentPosition rp : positions) {
                    System.out.printf("%-5d | %-25s | %-50s | %-10s%n",
                            rp.getId(), rp.getName(), rp.getDescription(), rp.getStatus());
                }
            }

            System.out.println("\nChọn hành động:");
            System.out.println("1. Trang trước");
            System.out.println("2. Trang sau");
            System.out.println("0. Thoát");
            System.out.print(ConsoleColor.YELLOW + "Nhập lựa chọn: " + ConsoleColor.RESET);
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    if (currentPage > 1) currentPage--;
                    else System.out.println(ConsoleColor.RED + "Đang ở trang đầu tiên." + ConsoleColor.RESET);
                    break;
                case "2":
                    if (currentPage < totalPages) currentPage++;
                    else System.out.println(ConsoleColor.RED + "Đang ở trang cuối cùng." + ConsoleColor.RESET);
                    break;
                case "0":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
            }
        }
    }

    public static void saveRecruitment(Scanner sc, RecruitmentPositionService recruitmentPositionService) {
        RecruitmentPosition recruitmentPosition = new RecruitmentPosition();
        recruitmentPosition.inputData(sc);
        recruitmentPositionService.saveRecruitmentPosition(recruitmentPosition);

        System.out.println(ConsoleColor.GREEN + "Thêm vị trí thành công! Tiếp theo, chọn công nghệ yêu cầu cho vị trí này." + ConsoleColor.RESET);

        TechnologyService technologyService = new TechnologyServiceImp();
        RecruitmentPositionTechnologyService rpTechService = new RecruitmentPositionTechnologyServiceImp();

        TechnologyUI.Menu(sc);

        while (true) {
            int techId = Validator.validateInt(sc, "Nhập ID công nghệ (hoặc nhập 0 để kết thúc): ");
            if (techId == 0) break;

            Technology tech = technologyService.findTechnologyById(techId);
            if (tech != null) {
                rpTechService.recruitmentPositionTechnology(recruitmentPosition.getId(), techId);
                System.out.println(ConsoleColor.GREEN + "Thêm công nghệ \"" + tech.getName() + "\" thành công!" + ConsoleColor.RESET);
            } else {
                System.err.println(ConsoleColor.RED + "Không tìm thấy công nghệ với ID: " + techId + ConsoleColor.RESET);
            }

            System.out.println("Bạn có muốn thêm công nghệ khác?");
            System.out.println("1. Có");
            System.out.println("2. Không");
            int choice = Validator.validateInt(sc, "Lựa chọn: ");
            if (choice != 1) break;
        }
    }

    public static void updateRecruitment(Scanner sc, RecruitmentPositionService recruitmentPositionService) {
        int id = Validator.validateInt(sc, "Nhập ID vị trí cần sửa: ");
        RecruitmentPosition rp = recruitmentPositionService.findRecruitmentPositionById(id);

        if (rp == null) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy vị trí có ID đã nhập." + ConsoleColor.RESET);
            return;
        }

        while (true) {
            System.out.println("\n--- CẬP NHẬT VỊ TRÍ TUYỂN DỤNG ---");
            System.out.println("1. Sửa tên vị trí");
            System.out.println("2. Sửa mô tả");
            System.out.println("3. Sửa trạng thái");
            System.out.println("4. Lưu và thoát");
            int choice = Validator.validateInt(sc, "Chọn: ");

            switch (choice) {
                case 1:
                    String newName = Validator.validateString(sc, "Nhập tên mới: ", new StringRule(255, 0));
                    if (!newName.equals(rp.getName())) {
                        rp.setName(newName);
                        System.out.println(ConsoleColor.GREEN + "Đã cập nhật tên." + ConsoleColor.RESET);
                    } else {
                        System.out.println(ConsoleColor.YELLOW + "Tên mới giống với tên cũ. Không thay đổi." + ConsoleColor.RESET);
                    }
                    break;
                case 2:
                    String newDesc = Validator.validateString(sc, "Nhập mô tả mới: ", new StringRule(500, 0));
                    if (!newDesc.equals(rp.getDescription())) {
                        rp.setDescription(newDesc);
                        System.out.println(ConsoleColor.GREEN + "Đã cập nhật mô tả." + ConsoleColor.RESET);
                    } else {
                        System.out.println(ConsoleColor.YELLOW + "Mô tả mới giống mô tả cũ. Không thay đổi." + ConsoleColor.RESET);
                    }
                    break;
                case 3:
                    Status newStatus = Validator.validateStatus(sc, "Nhập trạng thái (ACTIVE/INACTIVE): ", Status.class);
                    if (newStatus != rp.getStatus()) {
                        rp.setStatus(newStatus);
                        System.out.println(ConsoleColor.GREEN + "Đã cập nhật trạng thái." + ConsoleColor.RESET);
                    } else {
                        System.out.println(ConsoleColor.YELLOW + "Trạng thái mới giống trạng thái hiện tại." + ConsoleColor.RESET);
                    }
                    break;
                case 4:
                    recruitmentPositionService.updateRecruitmentPosition(rp);
                    System.out.println(ConsoleColor.GREEN + "Cập nhật thành công." + ConsoleColor.RESET);
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ. Chọn từ 1 đến 4." + ConsoleColor.RESET);
            }
        }
    }

    public static void deleteRecruitment(Scanner sc, RecruitmentPositionService service) {
        int id = Validator.validateInt(sc, "Nhập ID vị trí muốn xoá: ");
        RecruitmentPosition rp = service.findRecruitmentPositionById(id);

        if (rp == null) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy vị trí có ID đã nhập." + ConsoleColor.RESET);
            return;
        }

        System.out.println("Bạn có chắc chắn muốn xoá vị trí \"" + rp.getName() + "\"? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            service.deleteRecruitmentPosition(rp);
            System.out.println(ConsoleColor.GREEN + "Đã xoá thành công: " + rp.getName() + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.YELLOW + "Đã huỷ thao tác xoá." + ConsoleColor.RESET);
        }
    }
}

