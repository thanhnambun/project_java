package ra.edu.business.model.candidate;

import ra.edu.business.service.candidate.CandidateService;
import ra.edu.validate.CandidateValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.io.Serializable;
import java.sql.Date;
import java.util.Scanner;

public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idSequace =0;
    private int id;
    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    private CandidateGender Gender;
    private Date Dod;
    private String description;
    private int experience;
    private CabdidateStatus status;
    private CabdidateRole role;

    public Candidate() {
        this.id = ++ idSequace ;
    }

    public Candidate(String name, String email, String password, String phone, CandidateGender gender, Date dod, String description, int experience, CabdidateStatus status, CabdidateRole role) {
        this.id = ++ idSequace ;
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Gender = gender;
        Dod = dod;
        this.description = description;
        this.experience = experience;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public CandidateGender getGender() {
        return Gender;
    }

    public void setGender(CandidateGender gender) {
        Gender = gender;
    }

    public Date getDod() {
        return Dod;
    }

    public void setDod(Date dod) {
        Dod = dod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public CabdidateStatus getStatus() {
        return status;
    }

    public void setStatus(CabdidateStatus status) {
        this.status = status;
    }

    public CabdidateRole getRole() {
        return role;
    }

    public void setRole(CabdidateRole role) {
        this.role = role;
    }

    public void inputData(Scanner sc, CandidateService candidateService) {
        this.id = ++ idSequace ;
        Name = Validator.validateString(sc, "Vui lòng nhập tên ứng viên: ", new StringRule(100, 0));
        Email = CandidateValidator.validateEmail(sc, "Vui lòng nhập email của ứng viên: ", candidateService);
        Password = Validator.validateString(sc, "Vui lòng nhập mật khẩu: ", new StringRule(100, 0));
        Phone = inputPhone(sc,"vui lòng nhập số điện thaoij");
        Gender = Validator.validateStatus(sc, "Vui lòng nhập giới tính (male/female/other): ", CandidateGender.class);
        this.status = Validator.validateStatus(sc, "Nhập trạng thái tài khoản (active/inactive): ", CabdidateStatus.class);
        this.description = Validator.validateString(sc, "Nhập mô tả bản thân (có thể để trống): ", new StringRule(100, 0));
        this.experience = Validator.validateInt(sc);
        Dod = Date.valueOf(Validator.validateDate("Vui lòng nhập ngày sinh (dd/MM/yyyy): ", sc));
        this.role = Validator.validateStatus(sc, "Nhập vai trò (Admin/cadidate): ", CabdidateRole.class);

    }

    public String inputPhone(Scanner scanner, String message) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập số điện thoại của sinh viên, vui lòng nhập lại");
                continue;
            }
            if (Validator.isValidPhoneNumberVN(value)) {
                return value;
            }
            System.err.println("Không đúng số điện thoại di đông VN, vui lòng nhập lại");
        }
    }

}
