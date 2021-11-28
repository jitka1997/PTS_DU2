package entity;

import javax.persistence.*;

@Entity
public class FinalLineEntity {
    private String lineName;
    private String firstStop;
    private String startingTimes;
    private int numOfLineSegs;

    @Id
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Basic
    public String getFirstStop() {
        return firstStop;
    }

    public void setFirstStop(String firstStop) {
        this.firstStop = firstStop;
    }

    @Basic
    public String getStartingTimes() {
        return startingTimes;
    }

    @Basic
    public int getNumOfLineSegs() {
        return numOfLineSegs;
    }

    public void setNumOfLineSegs(int numOfLineSegs) {
        this.numOfLineSegs = numOfLineSegs;
    }

    public void setStartingTimes(String startingTimes) {
        this.startingTimes = startingTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalLineEntity that = (FinalLineEntity) o;

        if (lineName != null ? !lineName.equals(that.lineName) : that.lineName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lineName != null ? lineName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FinalLineEntity{" + "lineName='" + lineName + '\'' + ", firstStop='" + firstStop + '\'' + ", startingTimes='" + startingTimes + '\'' + ", numOfLineSegs=" + numOfLineSegs + '}';
    }
}
