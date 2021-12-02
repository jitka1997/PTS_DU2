import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeStopFactory implements StopFactoryInterface{
    List<String> stops;
    int next;
    public FakeStopFactory(List<String> stops){
        this.stops = stops;
        next = 0;
    }

    @Override
    public Stop createStop(StopNameType stopName) {
        StopNameType nextStop = new StopNameType(stops.get(next));
        next++;
        next %= stops.size();
        return new Stop(nextStop, new ArrayList<>());
    }
}
