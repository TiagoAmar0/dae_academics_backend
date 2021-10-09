package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(
                name = "getAllCourses",
                query = " SELECT c FROM Course c ORDER BY c.name"
        )
})

@Entity
@Table(name="courses", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class Course {
    @Id
    private int code;

    @NotNull
    private String name;

    @NotNull
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Student> students;

    @NotNull
    @OneToMany(mappedBy = "course")
    private List<Subject> subjects;

    public Course() {
        this.students = new LinkedList<>();
        this.subjects = new LinkedList<>();
    }

    public Course(int code, String name, List<Student> students, List<Subject> subjects) {
        this.code = code;
        this.name = name;
        this.students = students;
        this.subjects = subjects;
    }

    public Course(int code, String name) {
        this();
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        if(!this.students.contains(student)){
            this.students.add(student);
        }
    }

    public void removeStudent(Student student){
        this.students.remove(student);
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
        return o instanceof Course && this.code == ((Course) o).code;
    }

    @Override
    public int hashCode(){
        return Objects.hash(code, name, students);
    }
}
