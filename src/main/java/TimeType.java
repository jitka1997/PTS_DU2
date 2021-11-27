public class TimeType {
    private long time;
    private final long mod;

    public TimeType(long time) {
        this.time = time;
        mod = 1000;
    }

    public TimeType(TimeType time) {
        this.time = time.getTime();
        mod = 1000;
    }

    public long getTime() {
        return time;
    }

    private void setTime(long time) {
        this.time = time;
    }

    public void add(TimeType time) {
        setTime((time.getTime() + this.time) % mod);
    }

    public void subtract(TimeType time) {
        setTime(((time.getTime() - this.time) + mod) % mod);
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

    public boolean isLowerOrEqual(TimeType time) {
        return this.equals(time) || (this.time < time.getTime());
    }
}
