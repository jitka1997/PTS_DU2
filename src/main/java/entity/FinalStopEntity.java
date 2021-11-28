package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FinalStopEntity {
    private String name;
    private String lineNames;

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public String getLineNames() {
        return lineNames;
    }

    public void setLineNames(String lineNames) {
        this.lineNames = lineNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalStopEntity that = (FinalStopEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FinalStopEntity{" + "name='" + name + '\'' + ", lineNames='" + lineNames + '\'' + '}';
    }
}
