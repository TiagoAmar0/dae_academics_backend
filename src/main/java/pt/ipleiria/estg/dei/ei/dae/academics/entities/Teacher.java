package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Teacher extends User {
    @NotNull
    private String office;

    @NotNull
    @ManyToMany
    @JoinTable(name = "SUBJECTS_TEACHERS",
            joinColumns = @JoinColumn(
                    name = "SUBJECT_CODE",
                    referencedColumnName = "CODE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "TEACHER_USERNAME",
                    referencedColumnName = "USERNAME"
            )
    )
    private List<Subject> subjects;

    public Teacher() {
        this.subjects = new LinkedList<>();
    }

    public Teacher(String username, String password, String name, String email, String office, List<Subject> subjects) {
        super(username, password, name, email);
        this.office = office;
        this.subjects = subjects;
    }

    public Teacher(String username, String password, String name, String email, String office) {
        super(username, password, name, email);
        this.office = office;
        this.subjects = new LinkedList<>();
    }
}
