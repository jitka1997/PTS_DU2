package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(FinalLineSegmentEntityPK.class)
public class FinalLineSegmentEntity {
    private int place;
    private int timeDiff;
    private int capacity;
    private String lineName;
    private String nextStop;
    private String startingTime;
    private String numOfPass;

    @Id
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Basic
    public int getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(int timeDiff) {
        this.timeDiff = timeDiff;
    }

    @Basic
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Id
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Basic
    public String getNextStop() {
        return nextStop;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }

    @Basic
    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    @Basic
    public String getNumOfPass() {
        return numOfPass;
    }

    public void setNumOfPass(String numOfPass) {
        this.numOfPass = numOfPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalLineSegmentEntity that = (FinalLineSegmentEntity) o;
        return getPlace() == that.getPlace() && getLineName().equals(that.getLineName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlace(), getLineName());
    }

    @Override
    public String toString() {
        return "FinalLineSegmentEntity{" + "place=" + place + ", timeDiff=" + timeDiff + ", capacity=" + capacity + ", lineName='" + lineName + '\'' + ", nextStop='" + nextStop + '\'' + ", startingTime='" + startingTime + '\'' + ", numOfPass='" + numOfPass + '\'' + '}';
    }
}
