package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class SubjectBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private CourseBean courseBean;

    public List<Subject> getAllSubjects(){
        return em.createNamedQuery("getAllSubjects").getResultList();
    }

    public void create(int code, String name, int courseCode, String courseYear, String scholarYear){
        var course = courseBean.findOrFail(courseCode);

        var subject = new Subject(code, name, course, courseYear, scholarYear);

        em.persist(subject);
    }

    public Subject findOrFail(int code){
        var subject = this.findSubject(code);

        if(subject == null){
            throw new NotFoundException("Subject with code '" + code + "' not found");
        }

        return subject;
    }

    public Subject findSubject(int code){
        return em.find(Subject.class, code);
    }
}
