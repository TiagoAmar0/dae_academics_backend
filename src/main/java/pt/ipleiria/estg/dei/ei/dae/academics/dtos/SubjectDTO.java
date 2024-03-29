package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

public class SubjectDTO {
    private int code;
    private String name;
    private int courseCode;
    private String courseName;
    private String courseYear;
    private String schoolarYear;

    public SubjectDTO() {
    }

    public SubjectDTO(int code, String name, int courseCode, String courseName, String courseYear, String schoolarYear) {
        this.code = code;
        this.name = name;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.schoolarYear = schoolarYear;
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

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getSchoolarYear() {
        return schoolarYear;
    }

    public void setSchoolarYear(String schoolarYear) {
        this.schoolarYear = schoolarYear;
    }
}
