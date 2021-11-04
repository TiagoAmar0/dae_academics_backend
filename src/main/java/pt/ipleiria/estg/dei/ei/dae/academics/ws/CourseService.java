package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.CourseDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.CourseBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("courses")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class CourseService {
    @EJB
    private CourseBean courseBean;

    @GET
    @Path("/")
    @RolesAllowed({"Administrator", "Student"})
    public Response all(){
        var courses = courseBean.getAllCourses();
        var dtos = toDTOs(courses);

        return Response.ok(dtos).build();
    }

    /*public List<CourseDTO> getAllCoursesWS(){
        return toDTOs(courseBean.getAllCourses());
    }*/

    private List<CourseDTO> toDTOs(List<Course> courses){
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CourseDTO toDTO(Course course){
        return new CourseDTO(course.getCode(), course.getName());
    }


}
