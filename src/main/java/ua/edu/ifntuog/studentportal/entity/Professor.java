package ua.edu.ifntuog.studentportal.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "professors")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Professor {
    @Id
    private Long id;

    @Column(name = "academic_title", nullable = false)
    private String academicTitle;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> courses;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
