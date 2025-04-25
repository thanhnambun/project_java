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
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class RecruitmentPositionUI {
    public static void Menu(Scanner sc) {
        RecruitmentPositionService recruitmentPositionServivce = new RecruitmentPositionServiceImp();

        while (true) {
            System.out.println("\n=== QUẢN LÝ VỊ TRÍ TUYỂN DỤNG ===");
            System.out.println("1.Danh sách vị trí");
            System.out.println("2. thêm vị trí");
            System.out.println("3. cập nhật vị trí");
            System.out.println("4. xóa vị trí");
            System.out.println("5. Quay lại");
            System.out.print("Chọn: ");
            switch (sc.nextLine()) {
                case "1":
                    findAllRecruitment(sc, recruitmentPositionServivce);
                    break;
                case "2":
                    saveRecruitment(sc, recruitmentPositionServivce);
                    break;
                case "3":
                    updateRecruitment(sc, recruitmentPositionServivce);
                    break;
                case "4":
                    deleteRecruitment(sc, recruitmentPositionServivce);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Chọn sai, nhập lại!");
            }
        }
    }

    public static void findAllRecruitment(Scanner sc, RecruitmentPositionService recruitmentPositionServivce) {
        int totalPages = recruitmentPositionServivce.getTotalPagesRecruitmentPosition();
        int currentPage = 1;

        while (true) {
            System.out.println("\n--- Danh sách vị trí tuyển dụng - Trang " + currentPage + "/" + totalPages + " ---");

            List<RecruitmentPosition> positions = recruitmentPositionServivce.findAll(currentPage);
            if (positions.isEmpty()) {
                System.out.println("Không có vị trí nào trong trang này.");
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
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    if (currentPage > 1) currentPage--;
                    else System.out.println("Đang ở trang đầu tiên.");
                    break;
                case "2":
                    if (currentPage < totalPages) currentPage++;
                    else System.out.println("Đang ở trang cuối cùng.");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    public static void saveRecruitment(Scanner sc, RecruitmentPositionService recruitmentPositionServivce) {
        RecruitmentPosition  recruitmentPosition = new RecruitmentPosition();
        TechnologyService technologyService1 = new TechnologyServiceImp();
        RecruitmentPositionTechnologyService technologyService = new RecruitmentPositionTechnologyServiceImp();
        recruitmentPosition.inputData(sc);
        recruitmentPositionServivce.saveRecruitmentPosition(recruitmentPosition);
        TechnologyUI.Menu(sc);
        int idTechnology = Validator.validateInt(sc,"vui lòng nhập id của công nghệ");
        Technology technology = technologyService1.findTechnologyById(idTechnology);
        if (technology == null) {
            technologyService.recruitmentPositionTechnology(recruitmentPosition.getId(), idTechnology);
            System.out.println("Thêm mới thành công!");
        }else {
            System.err.println("không tìm thấy id của công nghệ trên ! Vui lòng xem lại ");
        }
    }

    public static void updateRecruitment(Scanner sc, RecruitmentPositionService recruitmentPositionServivce) {
        int id = Validator.validateInt(sc, "Nhập ID vị trí cần sửa: ");
        RecruitmentPosition rp = recruitmentPositionServivce.findRecruitmentPositionById(id);
        if (rp != null) {
            while (true) {
                System.out.println("1. Sửa tên vị trí");
                System.out.println("2. Sửa mô tả");
                System.out.println("3. Sửa trạng thái");
                System.out.println("4. Thoát");
                System.out.print("Chọn: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        String name = Validator.validateString(sc,"vui lòng nhập tên vị trí câ tuyển ",new StringRule(255,0));
                        if (rp.getName().equals(name)) {
                            rp.setName(name);
                        }

                        break;
                    case 2:
                        System.out.print("Nhập mô tả mới: ");
                        String desc = sc.nextLine();
                        rp.setDescription(desc);
                        break;
                    case 3:
                        Status newStatus = Validator.validateStatus(sc, "Nhập trạng thái (ACTIVE/INACTIVE): ", Status.class);
                        rp.setStatus(newStatus);
                        break;
                    case 4:
                        recruitmentPositionServivce.updateRecruitmentPosition(rp);
                        System.out.println("Cập nhật thành công.");
                        return;
                    default:
                        System.out.println("Chọn từ 1-4.");
                }
            }
        } else {
            System.out.println("Không tìm thấy vị trí có ID đã nhập.");
        }
    }

    public static void deleteRecruitment(Scanner sc, RecruitmentPositionService service) {
        int id = Validator.validateInt(sc, "Nhập ID vị trí muốn xoá: ");
        RecruitmentPosition rp = service.findRecruitmentPositionById(id);
        if (rp != null) {
            service.deleteRecruitmentPosition(rp);
            System.out.println("Đã xoá thành công: " + rp.getName());
        } else {
            System.out.println("Không tìm thấy vị trí có ID đã nhập.");
        }
    }
}

