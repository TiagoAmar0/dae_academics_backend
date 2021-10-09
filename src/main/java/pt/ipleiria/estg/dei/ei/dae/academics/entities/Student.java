package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="STUDENTS")
@NamedQueries({
        @NamedQuery(
                name = "getAllStudents",
                query = " SELECT s FROM Student s ORDER BY s.name"
        )
})

public class Student implements Serializable {

    @Id
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

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
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public Student(String username, String password, String name, String email, Course course, List<Subject> subjects) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.course = course;
        this.subjects = subjects;
    }

    public Student() {
        this.subjects = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
