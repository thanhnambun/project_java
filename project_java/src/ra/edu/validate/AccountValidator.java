package ra.edu.validate;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountValidator {
    public static String validatePassword(Scanner sc, String message) {
        String password;
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*/\\\\<>?~]).{8,}$";
        Pattern pattern = Pattern.compile(regex);

        while (true) {
            System.out.println(message);
            password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Mật khẩu không được để trống. Vui lòng nhập lại.");
            } else if (pattern.matcher(password).matches()) {
                return password;
            } else {
                System.out.println("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
            }
        }
    }


}
