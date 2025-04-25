package ra.edu.validate;

import com.mysql.cj.protocol.x.XMessage;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.technology.TechnologyService;

import java.util.List;
import java.util.Scanner;

public class TechnologyValidator {
    public static String validateTechnologyName(Scanner scanner, String message, TechnologyService technologyService) {
        System.out.println(message);
        while (true) {
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.err.println("Không được để trống tên công nghệ");
                continue;
            }

            if (!isTechnologyNameExist(name, technologyService)) {
                return name;
            } else {
                System.err.println("Tên công nghệ đã tồn tại, vui lòng nhập lại");
            }
        }
    }
    public static boolean isTechnologyNameExist(String name, TechnologyService technologyService) {
        return technologyService.isExistTechnology(name);
    }


}
