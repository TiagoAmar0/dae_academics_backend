package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Teacher;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private StudentBean studentBean;
    @EJB
    private CourseBean courseBean;
    @EJB
    private SubjectBean subjectBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private TeacherBean teacherBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try {
            System.out.println("Hello Java EE!");

            courseBean.create(1, "EI");
            studentBean.create("foo", "bar", "John Doe", "foobar@mail.com", 1);
            subjectBean.create(1, "DAE", 1, "2000/01", "2021/22");
            studentBean.enrollStudentInSubject("foo", 1);
            administratorBean.create("admin", "admin", "Admin", "admin@academics.pt");
            teacherBean.create("jane", "doe", "Jane Doe", "jane@mail.com", "D.S.01");
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
