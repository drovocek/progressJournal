package edu.volkov.progressjournal.model;

import lombok.*;

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
@Table(name = "subject")
public class Subject extends AbstractBaseEntity {

    @NotBlank(message = "Name must not be empty")
    @Size(max = 50, message = "Name size must be between 0 and 50")
    @Column(name = "name")
    private String name;

}
