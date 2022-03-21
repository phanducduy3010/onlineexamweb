package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 50, nullable = false)
    @NonNull
    private String name;

    private String description;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Topic_Subject"))
    private Subject subject;

    @JsonIgnore
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "topic")
    @Fetch(FetchMode.SUBSELECT)
    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setSubject(Subject subject) {
        if (this.subject != null) {
            this.subject.getTopics().remove(this);
        }

        this.subject = subject;
        if (subject != null) {
            subject.getTopics().add(this);
        }
    }

    public void addQuestion(Question question) {
        if (question == null || questions.contains(question)) return;
        question.setTopic(this);
    }

    public void removeQuestion(Question question) {
        if (question == null || !questions.contains(question)) return;
        question.setTopic(null);
    }
}
