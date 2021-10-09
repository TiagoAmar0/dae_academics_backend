package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(
                name = "getAllSubjects",
                query = " SELECT s FROM Subject s ORDER BY s.course.name, s.scholarYear DESC, s.courseYear DESC, s.name"
        )
})

@Entity
@Table(name = "SUBJECTS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "COURSE_CODE", "SCHOLAR_YEAR"}))
public class Subject {
    @Id
    private int code;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    private Course course;

    @NotNull
    @Column(name="course_year")
    private String courseYear;

    @NotNull
    @Column(name = "scholar_year")
    private String scholarYear;

    @NotNull
    @ManyToMany
    @JoinTable(name = "SUBJECTS_STUDENTS",
            joinColumns = @JoinColumn(
                name = "SUBJECT_CODE",
                referencedColumnName = "CODE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "STUDENT_USERNAME",
                    referencedColumnName = "USERNAME"
            )
    )
    private List<Student> students;

    public Subject(){
        this.students = new LinkedList<>();
    }

    public Subject(int code, String name, Course course, String courseYear, String scholarYear) {
        this();
        this.code = code;
        this.name = name;
        this.course = course;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
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

    @Override
    public boolean equals(Object o){
        return o instanceof Subject &&  this.code == ((Subject)o).code;
    }

    @Override
    public int hashCode(){
        return Objects.hash(code, name, course, courseYear, scholarYear);
    }
}
