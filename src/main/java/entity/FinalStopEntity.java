package entity;

import javax.persistence.*;

@NamedNativeQuery(name = "stopByName", query = "select array_to_string(array_agg" +
                "(distinct ls.line_name), ',') AS lineNames, s.name  from stop s, " +
                "line_segment ls, line l where (ls.next_stop = s.name or l" +
                ".first_stop = s.name) and ls.line_name = l.line_name and s.name = ? group by s" +
                ".name order by s.name", resultClass = FinalStopEntity.class)
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
