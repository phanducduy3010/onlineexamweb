package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
//@NamedEntityGraph(
//        name = "exam",
//        attributeNodes = {
//                @NamedAttributeNode(value = "topics", subgraph = "graph.exam-topics")
//        },
//        subgraphs = {
//                @NamedSubgraph(name = "graph.exam-topics", attributeNodes = @NamedAttributeNode(value = "topic", subgraph = "graph.questions")),
//                @NamedSubgraph(name = "graph.questions", attributeNodes = @NamedAttributeNode(value = "questions"))
//        }
//)
public class Exam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 100, nullable = false)
    @NonNull
    private String name;

    @NonNull
    private LocalDateTime startAt;

    @NonNull
    private LocalDateTime finishAt;

    private Boolean isOpen = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Exam_Class"))
    private Class ownerClass;

    @JsonIgnore
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "exam")
    @Fetch(FetchMode.SUBSELECT)
    private List<ExamTopic> topics = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "exam", orphanRemoval = true)
    private List<Result> results;

    public void addResult(Result result) {
        if (result == null || results.contains(result)) return;
        result.setExam(this);
    }

    public void removeResult(Result result) {
        if (result == null || !results.contains(result)) return;
        result.setExam(null);
    }

    public void addTopic(Topic topic, Integer numberOfQuestions) {
        if (topic == null) return;
        ExamTopic examTopic = new ExamTopic(this, topic, numberOfQuestions);
        if (topics.contains(examTopic)) return;
        topics.add(examTopic);
    }

    public void removeTopic(Topic topic) {
        if (topic == null) return;
        ExamTopic examTopic = new ExamTopic(this, topic);
        if (!topics.contains(examTopic)) return;

        topics.remove(examTopic);
        examTopic.setTopic(null);
        examTopic.setExam(null);
    }

    public void setOwnerClass(Class ownerClass) {
        if (this.ownerClass != null) {
            this.ownerClass.getExams().remove(this);
        }

        this.ownerClass = ownerClass;
        if (ownerClass != null) {
            ownerClass.getExams().add(this);
        }
    }
}
