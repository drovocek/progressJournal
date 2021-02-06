package edu.volkov.progressjournal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student extends AbstractBaseEntity {

    @NotBlank(message = "First name must not be empty")
    @Size(max = 50, message = "First name size must be between 0 and 50")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Size(max = 50, message = "Last name size must be between 0 and 50")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Patronymic must not be empty")
    @Size(max = 50, message = "Patronymic size must be between 0 and 50")
    @Column(name = "patronymic")
    private String patronymic;

}
