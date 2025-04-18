package ra.edu.business.model.technology;

import ra.edu.business.model.Status;
import ra.edu.business.service.technology.TechnologyService;
import ra.edu.validate.StringRule;
import ra.edu.validate.TechnologyValidator;
import ra.edu.validate.Validator;

import java.util.Scanner;

public class Technology {
    private int id;
    private String name;
    private Status status;

    public Technology() {
    }

    public Technology(int id, String name, Status status) {
        this.id = id;
        this.name = name;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void inputData(Scanner sc , TechnologyService technologyService) {
        this.name = TechnologyValidator.isExitName(sc,"vui lòng nhập tên công nghệ ",technologyService);
    }

    @Override
    public String toString() {
        return "Technology{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
