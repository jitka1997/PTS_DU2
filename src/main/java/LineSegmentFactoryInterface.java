public interface LineSegmentFactoryInterface {
    public LineSegmentInterface createLineSegment(LineNameType lineName, int i);
    public void updateNumOfPass(LineNameType lineName, StopNameType nextStop, TimeType startTime);
}
