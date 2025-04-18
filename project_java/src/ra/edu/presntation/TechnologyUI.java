package ra.edu.presntation;

import ra.edu.business.model.Status;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.technology.TechnologyService;
import ra.edu.business.service.technology.TechnologyServiceImp;
import ra.edu.validate.TechnologyValidator;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class TechnologyUI {
    public static void Menu(Scanner sc) {
        TechnologyService technologyService = new TechnologyServiceImp();
        while (true) {
            System.out.println("\n=== QUẢN LÝ CÔNG NGHỆ ===");
            System.out.println("1. Xem danh sách công nghệ");
            System.out.println("2. Thêm công nghệ");
            System.out.println("3. Cập nhật công nghệ");
            System.out.println("4. Xoá công nghệ");
            System.out.println("5. Quay lại");
            System.out.print("Chọn: ");
            switch (sc.nextLine()) {
                case "1":
                    findTechnology(sc, technologyService);
                    break;
                case "2":
                    saveTechnology(sc, technologyService);
                    break;
                case "3":
                    updateTechnology(sc, technologyService);
                    break;
                case "4":
                    deleteTechnology(sc, technologyService);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Chọn sai, nhập lại!");
            }
        }
    }

    public static void findTechnology(Scanner sc, TechnologyService technologyService) {
        int totalPages = technologyService.getTotalTechnologies();
        int currentPage = 1;

        while (true) {
            System.out.println("\n--- Danh sách phòng ban - Trang " + currentPage + "/" + totalPages + " ---");

            List<Technology> technologylist = technologyService.findAll(currentPage);
            if (technologylist.isEmpty()) {
                System.out.println("Không có phòng ban nào trong trang này.");
            } else {
                System.out.printf("%-5s | %-30s | %-10s%n", "ID", "Tên Công Nghệ", "Trạng Thái");
                System.out.println("-------------------------------------------------------------");

                for (Technology technology : technologylist) {
                    System.out.printf("%-5d | %-30s | %-10s%n",
                            technology.getId(), technology.getName(), technology.getStatus());
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

    public static void saveTechnology(Scanner sc, TechnologyService technologyService) {
        Technology technology1 = new Technology();
        technology1.inputData(sc, technologyService);
        technologyService.saveTechnology(technology1);
    }

    public static void updateTechnology(Scanner sc, TechnologyService technologyService) {
        int id = Validator.validateInt(sc, "vui lòng nhập id của công nghệ");
        Technology technology = technologyService.findTechnologyById(id);
        if (technology != null) {
            while (true) {
                System.out.println("1. sửa tên công nghệ ");
                System.out.println("2. sửa trạng thái ");
                System.out.println("3.thoát");
                System.out.println("vui lòng chọn");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    String name = TechnologyValidator.isExitName(sc, "vui lòng nhập tên công nghệ ", technologyService);
                    technology.setName(name);
                } else if (choice == 2) {
                    Status status_new = Validator.validateStatus(sc, "vui lòng nhập trạng thái mới ", Status.class);
                    technology.setStatus(status_new);
                } else if (choice == 3) {
                    System.out.println("đã thoát chỉnh sửa ");
                    technologyService.updateTechnology(technology);
                    break;
                } else {
                    System.out.println("vui lòng cho từ 1-3");
                }
            }
        } else {
            System.out.println("không tìm thấy phòng ban có id như trên");
        }
        findTechnology(sc, technologyService);
    }

    public static void deleteTechnology(Scanner sc, TechnologyService technologyService) {
        int id = Validator.validateInt(sc, "vui lòng nhập id của công nghệ");
        Technology technology = technologyService.findTechnologyById(id);
        technologyService.deleteTechnology(technology);
        System.out.println("đã xóa thành công " + technology);
        findTechnology(sc, technologyService);
    }
}
