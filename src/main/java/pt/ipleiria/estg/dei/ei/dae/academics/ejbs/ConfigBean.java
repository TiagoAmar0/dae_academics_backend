package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Teacher;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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


    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        courseBean.create(1, "EI");
        studentBean.create("foo", "bar", "John Doe","foobar@mail.com", 1);
        subjectBean.create(1, "DAE", 1, "2000/01", "2021/22");
        studentBean.enrollStudentInSubject("foo", 1);
        administratorBean.create("admin", "admin", "Admin", "admin@academics.pt");
        teacherBean.create("jane", "doe", "Jane Doe", "jane@mail.com", "D.S.01");
    }
}
