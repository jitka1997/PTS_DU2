import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeType {
    private final Time time;

    public TimeType(String time) throws ParseException {
        this.time = new Time(new SimpleDateFormat("hh-mm").parse(time).getTime());
    }

    public Time getTime() {
        return time;
    }
}
