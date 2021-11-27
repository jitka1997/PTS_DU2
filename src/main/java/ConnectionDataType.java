import java.util.List;

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

    public List<TimeType> getDepartures() {
        return arrivals;
    }
}
