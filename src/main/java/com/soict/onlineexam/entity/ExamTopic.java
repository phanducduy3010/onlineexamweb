package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
public class ExamTopic implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ExamTopic_Exam"))
    @EqualsAndHashCode.Include
    @NonNull
    @JsonIgnore
    private Exam exam;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ExamTopic_Topic"))
    @EqualsAndHashCode.Include
    @NonNull
    private Topic topic;

    @NonNull
    private Integer numberOfQuestions;

    public ExamTopic(Exam exam, Topic topic) {
        this.exam = exam;
        this.topic = topic;
    }
}
