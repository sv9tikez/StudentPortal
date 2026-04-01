package ua.edu.ifntuog.studentportal.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.edu.ifntuog.studentportal.enums.RoleType;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private RoleType name;
}
