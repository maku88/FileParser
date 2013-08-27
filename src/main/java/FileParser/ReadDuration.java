package FileParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 28.08.13
 * Time: 00:05
 * To change this template use File | Settings | File Templates.
 */
public class ReadDuration {

    List<Long> durations = new ArrayList<Long>();
    long counter = 0;


    public ReadDuration(long duration) {
        counter++;
        durations.add(duration);
    }

    public void addStat(long duration) {
        durations.add(duration);
        counter++;
    }

    public long getAvgDuration() {
        long totalDuration =0;

        for(Long l : durations) {
            totalDuration+=l;
        }
        return totalDuration/counter;
    }


}
