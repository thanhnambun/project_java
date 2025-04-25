package ra.edu.business.model.candidate;

import ra.edu.business.model.Status;
import ra.edu.business.service.candidate.CandidateService;
import ra.edu.validate.CandidateValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.io.Serializable;
import java.sql.Date;
import java.util.Scanner;

public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String phone;
    private CandidateGender gender;
    private Date dob;
    private String description;
    private int experience;
    private CandidateStatus status;

    public Candidate() {
    }

    public Candidate(String name, String email, String phone, CandidateGender gender, Date dob,
                     String description, int experience, CandidateStatus status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.description = description;
        this.experience = experience;
        this.status = status;
    }

    // Getters và Setters (chỉnh tên chuẩn camelCase)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CandidateGender getGender() {
        return gender;
    }

    public void setGender(CandidateGender gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public CandidateStatus getStatus() {
        return status;
    }

    public void setStatus(CandidateStatus status) {
        this.status = status;
    }

    public void inputData(Scanner sc, CandidateService candidateService) {
        name = Validator.validateString(sc, "Nhập tên ứng viên: ", new StringRule(100, 0));
        email = CandidateValidator.validateEmail(sc, "Nhập email ứng viên: ", candidateService);
        phone = CandidateValidator.validatePhone(sc,"vui lòng nhập số điện thoại",candidateService);
        gender = Validator.validateStatus(sc, "Nhập giới tính (male/female/other): ", CandidateGender.class);
        status = CandidateStatus.active;
        description = Validator.validateString(sc, "Nhập mô tả bản thân: ", new StringRule(100, 0));
        experience = Validator.validateInt(sc,"vui lòng nhập số năm kinh nghiệm");
        dob = Date.valueOf(Validator.validateDate("Nhập ngày sinh (yyyy-MM-dd): ", sc));
    }

    public String inputPhone(Scanner scanner, String message) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập số điện thoại, vui lòng nhập lại");
                continue;
            }
            if (Validator.isValidPhoneNumberVN(value)) {
                return value;
            }
            System.err.println("Số điện thoại không hợp lệ, vui lòng nhập lại");
        }
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", description='" + description + '\'' +
                ", experience=" + experience +
                ", status=" + status +
                '}';
    }
}
