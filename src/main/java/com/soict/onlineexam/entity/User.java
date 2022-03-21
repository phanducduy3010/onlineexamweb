package com.ngovangiang.onlineexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngovangiang.onlineexam.entity.type.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_user_email", columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 40, nullable = false)
    @NonNull
    private String email;

    @JsonIgnore
    @Column(length = 68, nullable = false)
    @NonNull
    private String password;

    @Column(length = 40, nullable = false)
    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 7)
    @Setter(AccessLevel.PROTECTED)
    private UserRole role = UserRole.STUDENT;
}
