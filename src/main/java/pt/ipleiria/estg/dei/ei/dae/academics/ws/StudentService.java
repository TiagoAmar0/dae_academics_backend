package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.StudentBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("students")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class StudentService {
    @EJB
    private StudentBean studentBean;

    @GET
    @Path("/")
    public List<StudentDTO> getAllStudentsWS(){
        return toDTOs(studentBean.getAllStudents());
    }

    private StudentDTO toDTO(Student student){
        return new StudentDTO(student.getUsername(), student.getName(), student.getEmail());
    }

    private List<StudentDTO> toDTOs(List<Student> students){
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
