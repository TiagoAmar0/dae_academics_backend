package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

public class CourseDTO {

    private int code;

    private String name;

    public CourseDTO() {
    }

    public CourseDTO(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
