package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

public class StudentDTO {
    private String username;
    private String name;
    private String email;
    private int courseCode;
    private String courseName;

    public StudentDTO() {
    }

    public StudentDTO(String username, String name, String email, int courseCode, String courseName) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
