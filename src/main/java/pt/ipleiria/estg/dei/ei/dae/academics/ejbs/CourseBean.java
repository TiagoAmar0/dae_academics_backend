package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class CourseBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int code, String name){
        var course = new Course(code, name);
        em.persist(course);

    }

    public List<Course> getAllCourses(){
        return em.createNamedQuery("getAllCourses", Course.class).getResultList();
    }

    public Course findCourse(int code){
        return em.find(Course.class, code);
    }

    public Course findOrFail(int code){
        var course = this.findCourse(code);

        if(course == null){
            throw new NotFoundException("Course with code '" + code + "' not found");
        }

        return course;
    }
}
