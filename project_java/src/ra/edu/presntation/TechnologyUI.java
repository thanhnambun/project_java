package ra.edu.presntation;

import ra.edu.business.model.Status;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.technology.TechnologyService;
import ra.edu.business.service.technology.TechnologyServiceImp;
import ra.edu.utils.ConsoleColor;
import ra.edu.validate.StringRule;
import ra.edu.validate.TechnologyValidator;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class TechnologyUI {
    public static void Menu(Scanner sc) {
        TechnologyService technologyService = new TechnologyServiceImp();
        while (true) {
            System.out.println(ConsoleColor.BLUE + "\n=== QUẢN LÝ CÔNG NGHỆ ===" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "1. Xem danh sách công nghệ" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "2. Thêm công nghệ" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "3. Cập nhật công nghệ" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "4. Xoá công nghệ" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "5. Tìm kiếm công nghệ theo tên" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "6. Quay lại" + ConsoleColor.RESET);
            System.out.print(ConsoleColor.CYAN + "Chọn: " + ConsoleColor.RESET);

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
                    searchTechnologyByName(sc, technologyService);
                    break;
                case "6":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Chọn sai, nhập lại!" + ConsoleColor.RESET);
            }
        }
    }

    public static void findTechnology(Scanner sc, TechnologyService technologyService) {
        int totalPages = technologyService.getTotalTechnologies();
        int currentPage = 1;
        int pageSize = Validator.validateInt(sc, ConsoleColor.CYAN + "Vui lòng nhập số lượng công nghệ trên 1 trang: " + ConsoleColor.RESET);

        while (true) {
            displayTechnologyListHeader(currentPage, totalPages);

            List<Technology> technologyList = technologyService.findAll(currentPage, pageSize);
            if (technologyList.isEmpty()) {
                System.out.println(ConsoleColor.YELLOW + "Không có công nghệ nào trong trang này." + ConsoleColor.RESET);
            } else {
                displayTechnologyList(technologyList);
            }

            String choice = getPaginationChoice(sc);
            switch (choice) {
                case "1":
                    currentPage = (currentPage > 1) ? currentPage - 1 : currentPage;
                    displayNavigationMessage(currentPage, totalPages, "Đang ở trang đầu tiên.");
                    break;
                case "2":
                    currentPage = (currentPage < totalPages) ? currentPage + 1 : currentPage;
                    displayNavigationMessage(currentPage, totalPages, "Đang ở trang cuối cùng.");
                    break;
                case "3":
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
                    break;
            }
        }
    }

    private static void displayTechnologyListHeader(int currentPage, int totalPages) {
        System.out.println(ConsoleColor.BLUE + "\n--- Danh sách công nghệ - Trang " + currentPage + "/" + totalPages + " ---" + ConsoleColor.RESET);
    }

    private static void displayTechnologyList(List<Technology> technologyList) {
        System.out.printf("%-5s | %-30s | %-10s%n", ConsoleColor.GREEN + "ID" + ConsoleColor.RESET,
                ConsoleColor.GREEN + "Tên Công Nghệ" + ConsoleColor.RESET,
                ConsoleColor.GREEN + "Trạng Thái" + ConsoleColor.RESET);
        System.out.println("-------------------------------------------------------------");

        for (Technology technology : technologyList) {
            System.out.printf("%-5d | %-30s | %-10s%n",
                    technology.getId(), technology.getName(), technology.getStatus());
        }
    }

    private static String getPaginationChoice(Scanner sc) {
        System.out.println("\nChọn hành động:");
        System.out.println(ConsoleColor.YELLOW + "1. Trang trước | 2. Trang sau | 3. Thoát" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.CYAN + "Nhập lựa chọn: " + ConsoleColor.RESET);
        return sc.nextLine();
    }

    private static void displayNavigationMessage(int currentPage, int totalPages, String message) {
        if ((currentPage == 1 && message.equals("Đang ở trang đầu tiên.")) ||
                (currentPage == totalPages && message.equals("Đang ở trang cuối cùng."))) {
            System.out.println(ConsoleColor.RED + message + ConsoleColor.RESET);
        }
    }


    public static void saveTechnology(Scanner sc, TechnologyService technologyService) {
        System.out.println(ConsoleColor.BLUE + "\n--- THÊM CÔNG NGHỆ MỚI ---" + ConsoleColor.RESET);

        Technology newTechnology = new Technology();
        newTechnology.inputData(sc, technologyService);

        boolean isSaved = technologyService.saveTechnology(newTechnology);
        if (isSaved) {
            System.out.println(ConsoleColor.GREEN + "Thêm công nghệ thành công!" + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.RED + "Thêm công nghệ thất bại. Vui lòng kiểm tra lại!" + ConsoleColor.RESET);
        }
        findTechnology(sc, technologyService);
    }

    public static void updateTechnology(Scanner sc, TechnologyService technologyService) {
        int id = Validator.validateInt(sc, ConsoleColor.YELLOW + "Vui lòng nhập ID của công nghệ: " + ConsoleColor.RESET);
        Technology technology = technologyService.findTechnologyById(id);

        if (technology == null) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy công nghệ có ID như trên." + ConsoleColor.RESET);
            return;
        }

        while (true) {
            System.out.println(ConsoleColor.BLUE + "\n--- CHỈNH SỬA CÔNG NGHỆ ---" + ConsoleColor.RESET);
            System.out.println("1. Sửa tên công nghệ");
            System.out.println("2. Sửa trạng thái");
            System.out.println("3. Thoát");
            System.out.print("Vui lòng chọn: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    updateTechnologyName(sc, technology, technologyService);
                    break;
                case "2":
                    updateTechnologyStatus(sc, technology);
                    break;
                case "3":
                    technologyService.updateTechnology(technology);
                    System.out.println(ConsoleColor.GREEN + "Đã lưu thay đổi và thoát chỉnh sửa." + ConsoleColor.RESET);
                    findTechnology(sc, technologyService);
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Vui lòng chọn từ 1 đến 3." + ConsoleColor.RESET);
            }
        }
    }

    private static void updateTechnologyName(Scanner sc, Technology technology, TechnologyService technologyService) {
        while (true) {
            String name = Validator.validateString(sc, "Nhập tên công nghệ mới: ", new StringRule(100, 0));

            if (technology.getName().equalsIgnoreCase(name)) {
                System.out.println(ConsoleColor.YELLOW + "Tên mới trùng với tên cũ, không có thay đổi." + ConsoleColor.RESET);
                return;
            }

            if (!TechnologyValidator.isTechnologyNameExist(name, technologyService)) {
                technology.setName(name);
                System.out.println(ConsoleColor.GREEN + "Cập nhật tên thành công!" + ConsoleColor.RESET);
                return;
            } else {
                System.out.println(ConsoleColor.RED + "Tên công nghệ đã tồn tại, vui lòng nhập lại." + ConsoleColor.RESET);
            }
        }
    }

    private static void updateTechnologyStatus(Scanner sc, Technology technology) {
        while (true) {
            System.out.println("Chọn trạng thái mới:");
            System.out.println("1. ACTIVE");
            System.out.println("2. DELETED");
            System.out.print("Chọn: ");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    technology.setStatus(Status.active);
                    System.out.println(ConsoleColor.GREEN + "Đã cập nhật trạng thái sang ACTIVE." + ConsoleColor.RESET);
                    return;
                case "2":
                    technology.setStatus(Status.delete);
                    System.out.println(ConsoleColor.GREEN + "Đã cập nhật trạng thái sang DELETED." + ConsoleColor.RESET);
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ. Vui lòng chọn 1 hoặc 2." + ConsoleColor.RESET);
            }
        }
    }


    public static void deleteTechnology(Scanner sc, TechnologyService technologyService) {
        int id = Validator.validateInt(sc, ConsoleColor.YELLOW + "Vui lòng nhập ID của công nghệ: " + ConsoleColor.RESET);
        Technology technology = technologyService.findTechnologyById(id);

        if (technology == null) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy công nghệ với ID đã nhập." + ConsoleColor.RESET);
            return;
        }

        technologyService.deleteTechnology(technology);
        System.out.println(ConsoleColor.GREEN + "Đã xóa công nghệ thành công." + ConsoleColor.RESET);

        System.out.printf(ConsoleColor.BLUE + "%-5s | %-30s | %-10s%n" + ConsoleColor.RESET,
                "ID", "Tên Công Nghệ", "Trạng Thái");
        System.out.println(ConsoleColor.BLUE + "-------------------------------------------------------------" + ConsoleColor.RESET);
        System.out.printf("%-5d | %-30s | %-10s%n",
                technology.getId(), technology.getName(), technology.getStatus());

        findTechnology(sc, technologyService);
    }

    public static void searchTechnologyByName(Scanner sc, TechnologyService technologyService) {
        String name = Validator.validateString(sc,
                ConsoleColor.YELLOW + "Vui lòng nhập tên công nghệ cần tìm kiếm: " + ConsoleColor.RESET,
                new StringRule(100, 0));

        List<Technology> technologyList = technologyService.findTechnologyByName(name);

        if (technologyList == null || technologyList.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Không tìm thấy công nghệ có tên trên." + ConsoleColor.RESET);
            return;
        }

        int pageSize = Validator.validateInt(sc,
                ConsoleColor.YELLOW + "Vui lòng nhập số công nghệ trên mỗi trang: " + ConsoleColor.RESET);
        int totalPage = (int) Math.ceil((double) technologyList.size() / pageSize);
        int currentPage = 1;

        while (true) {
            displayTechnologyPage(technologyList, currentPage, pageSize, totalPage);

            System.out.println(ConsoleColor.YELLOW + "1. Trang trước | 2. Trang sau | 3. Thoát" + ConsoleColor.RESET);
            int choice = Validator.validateInt(sc, "Chọn: ");

            switch (choice) {
                case 1:
                    if (currentPage > 1) currentPage--;
                    else System.out.println(ConsoleColor.RED + "Đang ở trang đầu tiên." + ConsoleColor.RESET);
                    break;
                case 2:
                    if (currentPage < totalPage) currentPage++;
                    else System.out.println(ConsoleColor.RED + "Đang ở trang cuối cùng." + ConsoleColor.RESET);
                    break;
                case 3:
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ." + ConsoleColor.RESET);
            }
        }
    }

    private static void displayTechnologyPage(List<Technology> techList, int currentPage, int pageSize, int totalPage) {
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, techList.size());

        System.out.printf(ConsoleColor.BLUE + "\n%-5s | %-30s | %-10s%n" + ConsoleColor.RESET,
                "ID", "Tên Công Nghệ", "Trạng Thái");
        System.out.println(ConsoleColor.BLUE + "-------------------------------------------------------------" + ConsoleColor.RESET);

        for (int i = start; i < end; i++) {
            Technology tech = techList.get(i);
            System.out.printf("%-5d | %-30s | %-10s%n", tech.getId(), tech.getName(), tech.getStatus());
        }

        System.out.printf(ConsoleColor.GREEN + "Trang %d / %d%n" + ConsoleColor.RESET, currentPage, totalPage);
    }
}
