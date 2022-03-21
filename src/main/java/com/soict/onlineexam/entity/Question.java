package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    @NonNull
    private String content;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Question_Topic"))
    private Topic topic;

    @Setter(AccessLevel.PACKAGE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "question", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Answer> answers = new ArrayList<>();

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setTopic(Topic topic) {
        if (this.topic != null) {
            this.topic.getQuestions().remove(topic);
        }

        this.topic = topic;
        if (topic != null) {
            topic.getQuestions().add(this);
        }
    }

    public void addAnswer(Answer answer) {
        if (answer == null || answers.contains(answer)) return;
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        if (answer == null || !answers.contains(answer)) return;
        answer.setQuestion(null);
    }
}
