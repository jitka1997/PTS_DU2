package entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FinalLineSegmentEntityPK implements Serializable {
    private int place;
    private String lineName;
    private int numOfPass;
    private int startingTime;

    @Id
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Id
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Id
    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalLineSegmentEntityPK that = (FinalLineSegmentEntityPK) o;

        if (place != that.place) return false;
        if (lineName != null ? !lineName.equals(that.lineName) : that.lineName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = place;
        result = 31 * result + (lineName != null ? lineName.hashCode() : 0);
        return result;
    }
}
