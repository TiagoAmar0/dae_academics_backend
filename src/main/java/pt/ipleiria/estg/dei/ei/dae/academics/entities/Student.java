package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllStudents",
                query = " SELECT s FROM Student s ORDER BY s.name"
        )
})

public class Student extends User implements Serializable {
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NotNull
    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    @NotNull
    @ManyToOne
    @JoinColumn(name="COURSE_CODE")
    private Course course;

    public Student(String username, String password, String name, String email, Course course) {
        super(username, password, name, email);
        this.subjects = new LinkedList<>();
        this.course = course;
    }

    public Student(String username, String password, String name, String email, Course course, List<Subject> subjects) {
        super(username, password, name, email);
        this.course = course;
        this.subjects = subjects;
    }

    public Student() {
        this.subjects = new LinkedList<>();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void addSubject(Subject subject){
        if(!subjects.contains(subject))
            subjects.add(subject);
    }

    public void removeSubject(Subject subject){
        subjects.remove(subject);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Student && Objects.equals(this.username, ((Student) o).username);
    }

    @Override
    public int hashCode(){
        return Objects.hash(username, password, name, email, course);
    }
}
