package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.StudentBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getAllStudentsWS(){
        var dtos = toDTOs(studentBean.getAllStudents());

        return Response.ok(dtos).build();
    }

    private StudentDTO toDTO(Student student){
        return new StudentDTO(student.getUsername(), student.getName(), student.getEmail(), student.getCourse().getCode(), student.getCourse().getName());
    }

    private List<StudentDTO> toDTOs(List<Student> students){
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewStudent(StudentCreateDTO studentDTO){
        studentBean.create(
                studentDTO.getUsername(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode()
        );

        Student newStudent = studentBean.findStudent(studentDTO.getUsername());
        if(newStudent == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        return Response.status(Response.Status.CREATED)
                .entity(toDTO(newStudent))
                .build();
    }

    @GET
    @Path("{username}")
    public  Response getStudentDetails(@PathParam("username") String username){
        var student = studentBean.findStudent(username);
        var dto = toDTO(student);

        return Response.ok(dto).build();
    }
}
