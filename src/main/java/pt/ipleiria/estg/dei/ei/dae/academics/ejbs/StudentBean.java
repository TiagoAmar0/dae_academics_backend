package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class StudentBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private CourseBean courseBean;

    @EJB
    private SubjectBean subjectBean;

    public void create(String username, String password, String name, String email, int courseCode) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {

        if(findStudent(username) != null)
            throw new MyEntityExistsException("A student with the username '" + username + "'already exists");

        var course = courseBean.findCourse(courseCode);
        if(course == null)
            throw new MyEntityNotFoundException("Course with code " + courseCode + " not found!");

        try {
            var student = new Student(username, password, name, email, course);
            em.persist(student);
            course.addStudent(student);
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }

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

    public void updateStudent(String username, String password, String name, String email, int courseCode){
        Student student = findStudent(username);
        if(student != null){
            Course course = courseBean.findCourse(courseCode);
            if(course != null){
                em.lock(student, LockModeType.OPTIMISTIC);
                student.setCourse(course);
                student.setName(name);
                student.setEmail(email);
                student.setPassword(password);

                em.merge(student);
            } else {
                System.err.println("ERROR_FINDING_COURSE");
            }
        } else {
            System.err.println("ERROR_FINDING_STUDENT");
        }
    }
}
