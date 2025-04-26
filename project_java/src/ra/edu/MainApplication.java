package ra.edu;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateStatus;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.business.service.candidate.CandidateServiceImp;
import ra.edu.business.service.login.LoginService;
import ra.edu.business.service.login.LoginServiceImp;
import ra.edu.business.service.register.RegisterService;
import ra.edu.business.service.register.RegisterServiceImp;
import ra.edu.presntation.AdminMain;
import ra.edu.presntation.CandidateUI;
import ra.edu.utils.ConsoleColor;
import ra.edu.validate.AccountValidator;
import ra.edu.validate.CandidateValidator;

import java.io.*;
import java.util.Scanner;

public class MainApplication {
    private static final Scanner sc = new Scanner(System.in);
    private static final String TOKEN_FILE = "candidate_token.dat";

    public static void main(String[] args) throws Exception {
        CandidateService candidateService = new CandidateServiceImp();
        LoginService loginService = new LoginServiceImp();
        RegisterService registerService = new RegisterServiceImp();

        Account savedAccount = readLoggedInCandidate();
        if (savedAccount != null) {
            System.out.println(ConsoleColor.YELLOW + "\nTự động đăng nhập với tài khoản: " + savedAccount.getUsername() + ConsoleColor.RESET);
            navigateByRole(savedAccount);
        }

        while (true) {
            showMainMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1 -> login(loginService);
                case 2 -> register(candidateService, registerService);
                case 3 -> {
                    System.out.println(ConsoleColor.GREEN + "Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!" + ConsoleColor.RESET);
                    System.exit(0);
                }
                default -> System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ. Vui lòng thử lại." + ConsoleColor.RESET);
            }
        }
    }

    private static void login(LoginService loginService) throws IOException {
        String email = CandidateValidator.inputEmail(sc, "Vui lòng nhập email: ");
        String password = AccountValidator.validatePassword(sc, "Vui lòng nhập mật khẩu: ");
        Account account = loginService.login(email, password);

        if (account == null) {
            System.out.println(ConsoleColor.RED + "Tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại." + ConsoleColor.RESET);
            return;
        }

        saveLoggedInCandidate(account);
        navigateByRole(account);
    }

    private static void register(CandidateService candidateService, RegisterService registerService) throws IOException {
        Candidate candidate = new Candidate();
        candidate.inputData(sc, candidateService);

        Account account = new Account();
        account.setUsername(candidate.getEmail());
        account.setPassword(AccountValidator.validatePassword(sc, "Vui lòng nhập mật khẩu: "));

        if (registerService.register(candidate, account)) {
            System.out.println(ConsoleColor.GREEN + "Đăng ký thành công! Chuyển đến đăng nhập..." + ConsoleColor.RESET);
            login(new LoginServiceImp());
        } else {
            System.out.println(ConsoleColor.RED + "Không thể đăng ký. Vui lòng thử lại." + ConsoleColor.RESET);
        }
    }

    private static void navigateByRole(Account account) throws IOException {
        if (account.getRole() == AccountRole.admin) {
            System.out.println(ConsoleColor.CYAN + "Đăng nhập thành công với vai trò Quản trị viên." + ConsoleColor.RESET);
            AdminMain.Menu(sc);
        } else {
            CandidateService candidateService = new CandidateServiceImp();
            Candidate candidate = candidateService.findById(account.getId());

            if (candidate == null) {
                System.err.println(ConsoleColor.RED + "Không tìm thấy thông tin ứng viên. Vui lòng liên hệ quản trị viên." + ConsoleColor.RESET);
                return;
            }

            if (candidate.getStatus() == CandidateStatus.block) {
                System.err.println(ConsoleColor.RED + "Tài khoản của bạn đã bị khóa." + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.GREEN + "Đăng nhập thành công. Chào mừng " + candidate.getName() + "!" + ConsoleColor.RESET);
                CandidateUI.menu(sc, account.getId());
            }
        }
    }

    private static void showMainMenu() {
        System.out.println(ConsoleColor.CYAN + "\n============= CHỌN VAI TRÒ ===============" + ConsoleColor.RESET);
        System.out.println("1. Đăng nhập ");
        System.out.println("2. Đăng ký với vai trò ứng viên");
        System.out.println("3. Thoát");
        System.out.println(ConsoleColor.CYAN + "==========================================" + ConsoleColor.RESET);
        System.out.print("Lựa chọn (1-3): ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "Lựa chọn không hợp lệ. Vui lòng nhập số." + ConsoleColor.RESET);
            return -1;
        }
    }

    private static void saveLoggedInCandidate(Account account) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TOKEN_FILE))) {
            oos.writeObject(account);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "Không thể lưu thông tin đăng nhập." + ConsoleColor.RESET);
        }
    }

    private static Account readLoggedInCandidate() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TOKEN_FILE))) {
            return (Account) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static void logout() {
        new File(TOKEN_FILE).delete();
        System.out.println(ConsoleColor.YELLOW + "Đã đăng xuất." + ConsoleColor.RESET);
    }
}
