package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

public class StudentCreateDTO extends StudentDTO {

    private String password;

    public StudentCreateDTO(){

    }

    public StudentCreateDTO(String username, String name, String email, String password, int courseCode, String courseName, String password1) {
        super(username, name, email, courseCode, courseName);
        this.password = password1;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
