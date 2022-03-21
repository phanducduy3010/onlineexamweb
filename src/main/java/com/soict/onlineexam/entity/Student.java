package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngovangiang.onlineexam.entity.type.UserRole;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(
        foreignKey = @ForeignKey(name = "FK_Student_User")
)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_student_code", columnNames = "code")
        }
)
public class Student extends User {

    @Column(length = 8, nullable = false)
    @NonNull
    private String code;

    @JsonIgnore
    @Setter(AccessLevel.PACKAGE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            foreignKey = @ForeignKey(name = "FK_StudentClass_Student"),
            inverseForeignKey = @ForeignKey(name = "FK_StudentClass_Class")
    )
    @Fetch(FetchMode.JOIN)
    private List<Class> learningClasses = new ArrayList<>();

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(orphanRemoval = true, mappedBy = "student")
    private List<Result> results;

    public Student(@NonNull String email, @NonNull String password, @NonNull String name, @NonNull String code) {
        super(email, password, name);
        setRole(UserRole.STUDENT);
        this.code = code;
    }

    public void addLearningClasses(Class cls) {
        if (cls == null || learningClasses.contains(cls)) return;
        learningClasses.add(cls);
        cls.getLearningStudents().add(this);
    }

    public void removeLearningClasses(Class cls) {
        if (cls == null) return;
        learningClasses.remove(cls);
        cls.getLearningStudents().remove(cls);
    }

    public void addResult(Result result) {
        if (result == null || results.contains(result)) return;
        result.setStudent(this);
    }

    public void removeResutl(Result result) {
        if (result == null || !results.contains(result)) return;
        result.setStudent(null);
    }
}
