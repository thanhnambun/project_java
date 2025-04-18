package ra.edu;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.business.service.candidate.CandidateServiceImp;
import ra.edu.business.service.login.LoginService;
import ra.edu.business.service.login.LoginServiceImp;
import ra.edu.business.service.register.RegisterService;
import ra.edu.business.service.register.RegisterServiceImp;
import ra.edu.presntation.AdminMain;
import ra.edu.presntation.CandidateUI;
import ra.edu.validate.CandidateValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainApplication {
    static Scanner sc = new Scanner(System.in);
    static final String TOKEN_FILE = "candidate_token.dat";

    public static void main(String[] args) throws Exception {
        CandidateService candidateService = new CandidateServiceImp();
        LoginService loginService = new LoginServiceImp();
        RegisterService registerService = new RegisterServiceImp();

        Account loggedInUser = readLoggedInCandidate();
        if (loggedInUser != null) {
            System.out.println("\nTự động đăng nhập với tài khoản: " + loggedInUser.getUsername());
            TimeUnit.SECONDS.sleep(randomDelay());
            if (loggedInUser.getRole() == AccountRole.admin) {
                AdminMain.Menu(sc);
            } else {
                CandidateUI.Menu(sc);
            }
        }

        while (true) {
            System.out.println("\n============= CHỌN VAI TRÒ ===============");
            System.out.println("1. Đăng nhập ");
            System.out.println("2. Đăng ký với vai trò ứng viên");
            System.out.println("3. Thoát");
            System.out.println("============================================");
            System.out.print("Lựa chọn (1-3): ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số từ 1 đến 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    login(sc, loginService);
                    break;
                case 2:
                    register(sc, candidateService, registerService);
                    break;
                case 3:
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void login(Scanner sc,  LoginService loginService) throws IOException {
        String email = CandidateValidator.inputEmail(sc, "Vui lòng nhập email: ");
        String password = Validator.validateString(sc, "Vui lòng nhập mật khẩu: ", new StringRule(100, 0));
        System.out.println(email);
        System.out.println(password);
        Account account = loginService.login(email, password);

        if (account == null) {
            System.out.println("Tài khoản hoặc mật khẩu không đúng. Vui lòng nhập lại.");
            return;
        }

        saveLoggedInCandidate(account);

        if (account.getRole() == AccountRole.admin) {
            AdminMain.Menu(sc);
        } else {
            CandidateUI.Menu(sc);
        }
    }

    private static void register(Scanner sc, CandidateService candidateService, RegisterService registerService) throws IOException {
        Account account = new Account();
        Candidate candidate = new Candidate();
        candidate.inputData(sc, candidateService);

        if (registerService.register(candidate,account)) {
            saveLoggedInCandidate(account);
            if (account.getRole() == AccountRole.admin) {
                AdminMain.Menu(sc);
            } else {
                CandidateUI.Menu(sc);
            }
        } else {
            System.out.println("Không đăng ký thành công.");
        }
    }

    static void saveLoggedInCandidate(Account Account) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TOKEN_FILE))) {
            oos.writeObject(Account);
        } catch (IOException e) {
            System.out.println("Không thể lưu thông tin đăng nhập.");
        }
    }

    static Account readLoggedInCandidate() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TOKEN_FILE))) {
            return (Account) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    static int randomDelay() {
        return new java.util.Random().nextInt(2) + 1;
    }

    public static void logout() {
        new File(TOKEN_FILE).delete();
        System.out.println("Đã đăng xuất.");
    }
}
