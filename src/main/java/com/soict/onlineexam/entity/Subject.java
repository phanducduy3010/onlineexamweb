package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
                @UniqueConstraint(name = "UK_subject_code", columnNames = "code"),
                @UniqueConstraint(name = "UK_subject_name", columnNames = "name")
        }
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 6, nullable = false)
    @NonNull
    private String code;

    @Column(length = 100, nullable = false)
    @NonNull
    private String name;

    @JsonIgnore
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subject")
    private List<Class> classes = new ArrayList();

    @JsonIgnore
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subject")
    private List<Topic> topics = new ArrayList<>();

    public void addClass(Class cls) {
        if (cls == null || classes.contains(cls)) return;
        cls.setSubject(this);
    }

    public void removeClass(Class cls) {
        if (cls == null || !classes.contains(cls)) return;
        cls.setSubject(null);
    }

    public void addTopic(Topic topic) {
        if (topic == null || classes.contains(topic)) return;
        topic.setSubject(this);
    }

    public void removeTopic(Topic topic) {
        if (topic == null || !topics.contains(topic)) return;
        topic.setSubject(null);
    }
}
