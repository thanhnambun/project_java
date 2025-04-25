package ra.edu.validate;

import java.util.Scanner;

public class RecruitmentPositionValidator {
    public static boolean validateMinSalary(Double minSalary , Double maxSalary) {
        if (minSalary == null || maxSalary == null) {
            return true;
        }

        if (minSalary <= maxSalary) {
            return true;
        }

        System.out.println("Lỗi: Lương tối thiểu không được lớn hơn lương tối đa.");
        return false;
    }
}
