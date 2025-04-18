package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String PHONE_VIETTEL_PREFIXES = "086|096|097|098|039|038|037|036|035|034|033|032";
    private static final String PHONE_VINAPHONE_PREFIXES = "091|094|088|083|084|085|081|082";
    private static final String PHONE_MOBIFONE_PREFIXES = "070|079|077|076|078|089|090|093";

    public static boolean isValidEmail(String value) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return value != null && Pattern.matches(emailRegex, value.trim());
    }

    public static boolean isValidPhoneNumberVN(String value) {
        String phoneRegex = "(" + PHONE_VIETTEL_PREFIXES + "|" + PHONE_VINAPHONE_PREFIXES + "|" + PHONE_MOBIFONE_PREFIXES + ")\\d{7}";
        return value != null && Pattern.matches(phoneRegex, value.trim());
    }

    public static int validateInt(Scanner sc,String message) {
        while (true) {
            System.out.println(message);
            try {
                int number = Integer.parseInt(sc.nextLine());return number;
            } catch (Exception e) {
                System.out.println("không đúng định dạng ");
            }
        }
    }

    public static double validateDouble(Scanner sc) {
        while (true) {
            System.out.println("vui lòng nhập 1 số thực kiểu double");
            try {
                double number = Double.parseDouble(sc.nextLine());
                return number;
            } catch (Exception e) {
                System.out.println("không đúng định dạng kiểu double");
            }
        }
    }

    private static boolean parseEnumValue(String input, Class<?> type) {
        Class<? extends Enum> enumType = (Class<? extends Enum>) type;
        for (Enum constant : enumType.getEnumConstants()) {
            if (input.equalsIgnoreCase(constant.name())) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Enum<T>> T validateStatus(Scanner scanner, String message, Class<T> enumClass) {
        System.out.println(message);
        while (true) {
            String input = scanner.nextLine().trim();
            for (T constanst : enumClass.getEnumConstants()) {
                if (constanst.name().equalsIgnoreCase(input)) {
                    return constanst;
                }
            }
            System.err.println("Giá trị không đúng, vui lòng nhập lại");
        }

    }

    public static String validateString(Scanner scanner,String message ,StringRule stringRule) {
        System.out.println(message);
        while (true){
            String value = scanner.nextLine().trim();
            if(value.isEmpty()){
                System.err.println("không được để trống");
            }
            if (stringRule.isValidString(value)) {
                return value;
            }
        }
    }

    public static LocalDate validateDate(String message, Scanner scanner) {
        System.out.println(message);
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.err.println("Định dạng ngày tháng không đúng (dd/MM/yyyy), vui lòng nhập lại.");
            }
        }
    }
}
