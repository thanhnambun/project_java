package ra.edu.business.model.recruitmenPosition;

import ra.edu.business.model.Status;
import ra.edu.validate.RecruitmentPositionValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.time.LocalDate;
import java.util.Date;
import java.time.Instant;
import java.util.Scanner;

public class RecruitmentPosition {
    private int id;
    private String name;
    private String description;
    private double minSalary;
    private double maxSalary;
    private int minExperience;
    private LocalDate createdDate;
    private LocalDate expiredDate;
    private Status status;

    public RecruitmentPosition() {
    }

    public RecruitmentPosition(int id, String name, String description, double minSalary, double maxSalary, int minExperience, LocalDate createdDate, LocalDate expiredDate, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.minExperience = minExperience;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.status = status;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(int minExperience) {
        this.minExperience = minExperience;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void inputData(Scanner sc) {
        this.name = Validator.validateString(sc, "vui lòng nhập tên vị trí cần tuyển dụng ", new StringRule(100, 0));
        this.description = Validator.validateString(sc, "vui lòng nhập mô tả ", new StringRule(255, 0));
        this.minSalary = inputOptionalSalary(sc, "Vui lòng nhập lương tối thiểu");
        this.maxSalary = inputOptionalSalary(sc, "Vui lòng nhập lương tối đa");

        while (!RecruitmentPositionValidator.validateMinSalary(this.minSalary, this.maxSalary)) {
            System.out.println("Lương tối thiểu phải nhỏ hơn lương tối đa. Nhập lại:");
            this.minSalary = inputOptionalSalary(sc, "Vui lòng nhập lại lương tối thiểu");
            this.maxSalary = inputOptionalSalary(sc, "Vui lòng nhập lại lương tối đa");
        }
        this.minExperience = Validator.validateInt(sc, "vui lòng nhập kinh nghiệm tối thiểu");
        this.createdDate = LocalDate.now();
        this.expiredDate = inputExpiredDate(sc);
        this.status = Validator.validateStatus(sc, "vui lòng nhập trạng thái của vị trí ứng tuyển", Status.class);
    }

    public static Double inputOptionalSalary(Scanner sc, String message) {
        while (true) {
            System.out.print(message + " (có thể bỏ trống): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                double salary = Double.parseDouble(input);
                if (salary < 0) {
                    System.out.println("Lương không được âm!");
                } else {
                    return salary;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ hoặc để trống.");
            }
        }
    }


    public LocalDate inputExpiredDate(Scanner sc) {
        while (true) {
            LocalDate expiredDate = Validator.validateDate("Vui lòng nhập ngày hết hạn (yyyy-MM-dd):", sc);
            if (expiredDate.isAfter(createdDate)) {
                return expiredDate;
            } else {
                System.err.printf("Ngày hết hạn (%s) phải sau ngày tạo (%s). Vui lòng nhập lại.%n", expiredDate, createdDate);
            }
        }
    }


    @Override
    public String toString() {
        return "RecruitmentPosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", minExperience=" + minExperience +
                ", createdDate=" + createdDate +
                ", expiredDate=" + expiredDate +
                ", status=" + status +
                '}';
    }
}
