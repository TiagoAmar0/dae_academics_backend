package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Stateless
public class StudentBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private CourseBean courseBean;

    @EJB
    private SubjectBean subjectBean;

    public void create(String username, String password, String name, String email, int courseCode){

        var course = courseBean.findOrFail(courseCode);
        var student = new Student(username, password, name, email, course);
        em.persist(student);
        course.addStudent(student);
    }

    public List<Student> getAllStudents(){
        return (List<Student>) em.createNamedQuery("getAllStudents").getResultList();
    }

    public Student findStudent(String username){
        return em.find(Student.class, username);
    }

    public void enrollStudentInSubject(String username, int subjectCode){
        var student = findStudent(username);
        var subject = subjectBean.findOrFail(subjectCode);

        if(!student.getCourse().equals(subject.getCourse()))
            throw new ClientErrorException("You can't enroll a student in this subject.", Response.Status.CONFLICT);

        student.addSubject(subject);
        subject.addStudent(student);
    }

}
