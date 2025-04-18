package ra.edu.validate;

import com.mysql.cj.protocol.x.XMessage;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.technology.TechnologyService;

import java.util.Scanner;

public class TechnologyValidator {
    public static String isExitName(Scanner scanner , String message, TechnologyService technologyService) {
        System.out.println(message);
        while (true) {
            String name = scanner.next();
            if (name.isEmpty()) {
                System.err.println("không được để trống tên công nghệ");
            }
            Technology technology = technologyService.findTechnologyByName(name);
            if (technology == null) {
                return name;
            }else {
                System.err.println("tên công nghệ đã tồn tại vui lòng nhập lại ");
            }
        }
    }
}
