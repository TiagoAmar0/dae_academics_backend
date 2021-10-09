package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

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


    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        courseBean.create(1, "EI");
        studentBean.create("foo", "bar", "John Doe","foobar@mail.com", 1);
        subjectBean.create(1, "DAE", 1, "2000/01", "2021/22");
        studentBean.enrollStudentInSubject("foo", 1);
    }
}
