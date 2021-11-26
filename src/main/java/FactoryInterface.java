import java.util.List;

public interface FactoryInterface {
    public Stop createStop(StopNameType stopName);
    public Line createLine(LineNameType lineName);
    public LineSegment createLineSegment(LineNameType lineName, StopNameType nextStopName);
}
