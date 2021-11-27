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
        setTime(time.getTime() - this.time);
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

    @Override
    public int compareTo(TimeType time) {
        return (int) minus(this, time).getTime();
    }
}
