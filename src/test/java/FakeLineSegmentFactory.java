import java.util.List;

public class FakeLineSegmentFactory implements LineSegmentFactoryInterface{
    private final List<String> stops;
    private int next;

    public FakeLineSegmentFactory(List<String> stops){
        this.stops = stops;
        next = 0;
    }

    @Override
    public LineSegmentInterface createLineSegment(LineNameType lineName, int i) {
        StopNameType nextStop = new StopNameType(stops.get(next));
        next++;
        next %= stops.size();
        return new FakeLineSegment(nextStop);
    }
}
