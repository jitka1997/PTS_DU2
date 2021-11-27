import java.util.List;

public interface LineFactoryInterface {
    public Line createLine(LineNameType lineName, LineSegmentFactoryInterface lineSegmentFactory);
}
