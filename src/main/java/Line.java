import java.util.List;

public class Line {
    private final LineNameType name;
    private final List<TimeType> startingTimes;
    private final StopNameType firstStop;

    public Line(LineNameType name, List<TimeType> startingTimes, StopNameType firstStop) {
        this.name = name;
        this.startingTimes = startingTimes;
        this.firstStop = firstStop;
    }
}
