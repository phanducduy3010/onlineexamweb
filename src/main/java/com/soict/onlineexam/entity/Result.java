package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime submitAt;

    private Integer score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Result_Exam"))
    private Exam exam;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Result_Student"))
    private Student student;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            foreignKey = @ForeignKey(name = "FK_ResultAnswer_Result"),
            inverseForeignKey = @ForeignKey(name = "FK_ResultAnswer_Answer")
    )
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        if (answer == null || answers.contains(answer)) return;
        answers.add(answer);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
    }

    public void setExam(Exam exam) {
        if (this.exam != null) {
            this.exam.getResults().remove(exam);
        }

        this.exam = exam;
        if (exam != null) {
            exam.getResults().add(this);
        }
    }

    public void setStudent(Student student) {
        if (this.student != null) {
             this.student.getResults().remove(student);
        }

        this.student = student;
        if (student != null) {
            student.getResults().add(this);
        }
    }
}
