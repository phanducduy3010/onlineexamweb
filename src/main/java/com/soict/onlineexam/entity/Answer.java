package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
//@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
//    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    private String content;

    private Boolean correct = false;

    @JsonIgnore
    @ManyToMany(mappedBy = "answers")
    List<Result> results;

    public Answer(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Answer_Question"))
    private Question question;

    public void setQuestion(Question question) {
        if (this.question != null) {
            this.question.getAnswers().remove(question);
        }

        this.question = question;
        if (question != null) {
            question.getAnswers().add(this);
        }
    }
}
