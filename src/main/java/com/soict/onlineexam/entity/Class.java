package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngovangiang.onlineexam.entity.type.ClassType;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_class_code", columnNames = "code")
        }
)
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 6, nullable = false)
    @NonNull
    private String code;

    @Column(length = 7)
    private String semeter;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ClassType type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Class_Teacher"))
    private Teacher teacher;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "learningClasses")
    private List<Student> learningStudents= new ArrayList();

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Class_Subject"))
    @Fetch(FetchMode.JOIN)
    private Subject subject;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ownerClass")
    private List<Exam> exams;

    public void setTeacher(Teacher teacher) {
        if (this.teacher != null) {
            this.teacher.getTeachingClasses().remove(this);
        }

        this.teacher = teacher;
        if (teacher != null) {
            teacher.getTeachingClasses().add(this);
        }
    }

    public void setSubject(Subject subject) {
        if (this.subject != null) {
            this.subject.getClasses().remove(this);
        }

        this.subject = subject;
        if (subject != null) {
            subject.getClasses().add(this);
        }
    }

    public void addLearningStudent(Student student) {
        if (student == null) return;
        student.addLearningClasses(this);
    }

    public void removeLearningStudent(Student student) {
        if (student == null) return;
        student.removeLearningClasses(this);
    }

    public void addExam(Exam exam) {
        if (exam == null || exams.contains(exam)) return;
        exam.setOwnerClass(this);
    }

    public void removeExam(Exam exam) {
        if (exam == null || !exams.contains(exam)) return;
        exam.setOwnerClass(null);
    }
}
