package edu.volkov.progressjournal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.volkov.progressjournal.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "subject")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subject extends AbstractBaseEntity {

    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    @NotBlank(message = "Name must not be empty")
    @Size(max = 50, message = "Name size must be between 0 and 50")
    @Column(name = "name")
    private String name;

    public Subject(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Subject(Subject subject) {
        super(subject.getId());
        this.name = subject.getName();
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
