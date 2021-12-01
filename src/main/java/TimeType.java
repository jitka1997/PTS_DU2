import java.util.Objects;

public class TimeType implements Comparable<TimeType> {
    private long time;

    public TimeType(long time) {
        this.time = time;
    }

    public TimeType(TimeType time) {
        this.time = time.getTime();
    }

    public long getTime() {
        return time;
    }

    private void setTime(long time) {
        this.time = time;
    }

    public void add(TimeType time) {
        setTime(time.getTime() + this.time);
    }

    public void subtract(TimeType time) {
        setTime(this.time - time.getTime());
    }

    public static TimeType plus(TimeType time1, TimeType time2) {
        TimeType newTime = new TimeType(time1);
        newTime.add(time2);
        return newTime;
    }

    public static TimeType minus(TimeType time1, TimeType time2) {
        TimeType newTime = new TimeType(time1);
        newTime.subtract(time2);
        return newTime;
    }

    public static TimeType max(TimeType time1, TimeType time2){
        return new TimeType(Long.max(time1.getTime(), time2.getTime()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeType timeType = (TimeType) o;
        return getTime() == timeType.getTime();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime());
    }

    @Override
    public int compareTo(TimeType time) {
        return (int) minus(this, time).getTime();
    }

    @Override
    public String toString() {
        return Long.toString(time);
    }
}
