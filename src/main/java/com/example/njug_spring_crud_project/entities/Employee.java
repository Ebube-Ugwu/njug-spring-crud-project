package com.example.njug_spring_crud_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(name = "department")
    @NotBlank
    @Size(max = 50)
    private String department;

    @Column(name = "salary")
    @NotBlank
    @DecimalMin("0.00")
    private BigDecimal salary;

    @Column(name = "date_of_joining")
    @PastOrPresent
    private LocalDate dateOfJoining;

    @NotNull
    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "created_at" ,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at" ,updatable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
