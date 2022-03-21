package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngovangiang.onlineexam.entity.type.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(
        foreignKey = @ForeignKey(name = "FK_Teacher_User")
)
public class Teacher extends User {

    @JsonIgnore
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "teacher")
    private List<Class> teachingClasses = new ArrayList<>();

    public Teacher(String email, String password, String name) {
        super(email, password, name);
        setRole(UserRole.TEACHER);
    }

    public void addClass(Class cls) {
        if (cls == null || teachingClasses.contains(cls)) return;
        cls.setTeacher(this);
    }

    public void removeClass(Class cls) {
        if (cls == null || !teachingClasses.contains(cls)) return;
        cls.setTeacher(null);
    }
}
