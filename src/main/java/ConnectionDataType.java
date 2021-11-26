import java.util.List;

public class ConnectionDataType {
    private final List<LineNameType> lines;
    private final List<StopNameType> stops;
    private final List<TimeType> departures;


    public ConnectionDataType(List<LineNameType> lines, List<StopNameType> stops, List<TimeType> departures) {
        this.lines = lines;
        this.stops = stops;
        this.departures = departures;
    }

    public List<LineNameType> getLines() {
        return lines;
    }

    public List<StopNameType> getStops() {
        return stops;
    }

    public List<TimeType> getDepartures() {
        return departures;
    }
}
