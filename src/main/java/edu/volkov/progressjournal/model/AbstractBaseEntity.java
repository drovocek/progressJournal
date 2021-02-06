package edu.volkov.progressjournal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractBaseEntity implements Persistable<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

}
