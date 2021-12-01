package entity;

import javax.persistence.*;
import java.util.Objects;

@NamedNativeQuery(name = "lineSegmentByLinenNameAnId", query = "select ls.line_name AS lineName, ls.place, " +
        "ls.time_diff AS timeDiff, ls.capacity, ls.next_stop AS nextStop, " +
        "lsnp.num_of_pass AS numOfPass, starting_time AS startingTime from " +
        "line_segment ls, line_segs_num_of_pass lsnp where lsnp.place = ls" +
        ".place and ls.line_name = lsnp.line_name and ls.line_name = ? and ls.place = ? order by " +
        "ls.line_name, ls.place", resultClass = FinalLineSegmentEntity.class)
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
