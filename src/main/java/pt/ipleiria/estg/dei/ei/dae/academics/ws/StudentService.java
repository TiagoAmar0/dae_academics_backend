package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SubjectDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.StudentBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.mail.MessagingException;
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

    @EJB
    private EmailBean emailBean;

    @GET
    @Path("/")
    public Response getAllStudentsWS(){
        var dtos = toStudentDTOs(studentBean.getAllStudents());

        return Response.ok(dtos).build();
    }

    private StudentDTO toDTO(Student student){
        return new StudentDTO(student.getUsername(), student.getName(), student.getEmail(), student.getCourse().getCode(), student.getCourse().getName());
    }

    private SubjectDTO toDTO(Subject subject){
        return new SubjectDTO(subject.getCode(), subject.getName(), subject.getCourse().getCode(), subject.getCourse().getName(), subject.getCourseYear(), subject.getScholarYear());
    }

    private List<SubjectDTO> toSubjectDTOs(List<Subject> subjects){
        return subjects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<StudentDTO> toStudentDTOs(List<Student> students){
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewStudent(StudentCreateDTO studentDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
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

    @GET
    @Path("{username}/subjects")
    public Response getStudentSubjects(@PathParam("username") String username){
        Student student = studentBean.findStudent(username);
        if(student != null){
            var dtos = toSubjectDTOs(student.getSubjects());
            return Response.ok(dtos).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @POST
    @Path("/{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email)
            throws MyEntityNotFoundException, MessagingException {
        Student student = studentBean.findStudent(username);
        if (student == null) {
            throw new MyEntityNotFoundException("Student with username '" + username
                    + "' not found in our records.");
        }
        emailBean.send(student.getEmail(), email.getSubject(), email.getMessage());
        return Response.status(Response.Status.OK).entity("E-mail sent").build();
    }
}
