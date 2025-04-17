package ra.edu.validate;

import ra.edu.business.service.candidate.CandidateService;

import java.util.Scanner;

public class CandidateValidator {
    public static String validateEmail(Scanner scanner, String message, CandidateService candidateService) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập email sinh viên, vui lòng nhập lại");
                continue;
            }
            if (Validator.isValidEmail(value)) {
                if (candidateService.isEmailExists(value)) {
                    return value;
                }else {
                    System.err.println("tên email đã bị trùng vui lòng nhập lại");
                }
            }
            System.err.println("Không đúng định dạng email, vui lòng nhập lại");

        }
    }
    public static String inputEmail(Scanner scanner, String message) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập email sinh viên, vui lòng nhập lại");
                continue;
            }
            if (Validator.isValidEmail(value)) {
                    return value;

            }
            System.err.println("Không đúng định dạng email, vui lòng nhập lại");
        }
    }
}
