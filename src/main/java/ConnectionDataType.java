import java.util.List;
import java.util.Objects;

public class ConnectionDataType {
    private final List<LineNameType> lines;
    private final List<StopNameType> stops;
    private final List<TimeType> arrivals;


    public ConnectionDataType(List<LineNameType> lines, List<StopNameType> stops,
            List<TimeType> arrivals) {
        this.lines = lines;
        this.stops = stops;
        this.arrivals = arrivals;
    }

    public List<LineNameType> getLines() {
        return lines;
    }

    public List<StopNameType> getStops() {
        return stops;
    }

    public List<TimeType> getArrivals() {
        return arrivals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionDataType that = (ConnectionDataType) o;
        return getLines().equals(that.getLines()) && getStops().equals(
                that.getStops()) && arrivals.equals(that.arrivals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLines(), getStops(), getArrivals());
    }

    @Override
    public String toString() {
        return "ConnectionDataType{" + "lines=" + lines + ", stops=" + stops + ", arrivals=" + arrivals + '}';
    }
}
