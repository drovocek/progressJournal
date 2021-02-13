package edu.volkov.progressjournal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.volkov.progressjournal.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends AbstractBaseEntity {

    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    @NotBlank(message = "First name must not be empty")
    @Size(max = 50, message = "First name size must be between 0 and 50")
    @Column(name = "first_name")
    private String firstName;

    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    @NotBlank(message = "Last name must not be empty")
    @Size(max = 50, message = "Last name size must be between 0 and 50")
    @Column(name = "last_name")
    private String lastName;

    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    @NotBlank(message = "Patronymic must not be empty")
    @Size(max = 50, message = "Patronymic size must be between 0 and 50")
    @Column(name = "patronymic")
    private String patronymic;

    @Formula("(SELECT AVG(je.mark) FROM Journal_Entry je WHERE je.student_id=id)")
    private Integer averageRating;

    public Student(Student student) {
        super(student.getId());
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.patronymic = student.getPatronymic();
    }

    public Student(Integer id, String firstName, String lastName, String patronymic) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public Integer getAverageRating() {
        return (averageRating == null) ? 0 : averageRating;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
