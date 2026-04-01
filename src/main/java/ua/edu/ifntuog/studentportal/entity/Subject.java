package ua.edu.ifntuog.studentportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "subjects")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> courses;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
