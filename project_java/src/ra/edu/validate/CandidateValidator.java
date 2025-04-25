package ra.edu.validate;

import ra.edu.business.service.candidate.CandidateService;

import java.util.Scanner;

public class CandidateValidator {
    public static String validateEmail(Scanner scanner, String message, CandidateService candidateService) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập email ứng viên, vui lòng nhập lại.");
                continue;
            }
            if (!Validator.isValidEmail(value)) {
                System.err.println("Không đúng định dạng email, vui lòng nhập lại.");
                continue;
            }
            if (isEmailExist(value, candidateService)) {
                System.err.println("Email đã bị trùng, vui lòng nhập lại.");
                continue;
            }
            return value;
        }
    }

    public static boolean isEmailExist(String email, CandidateService candidateService) {
        return candidateService.isEmailExist(email);
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

    public static String validatePhone(Scanner scanner, String message, CandidateService candidateService) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Không được để trống.");
                continue;
            }
            if (!Validator.isValidPhoneNumberVN(value)) {
                System.err.println("Số điện thoại không đúng định dạng. Vui lòng nhập lại.");
                continue;
            }
            if (isExistPhone(value, candidateService)) {
                System.err.println("Số điện thoại đã bị trùng. Vui lòng nhập lại.");
            } else {
                return value;
            }
        }
    }


    public static boolean isExistPhone(String phone, CandidateService candidateService) {
        return candidateService.isPhoneExist(phone);
    }

}
