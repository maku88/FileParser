package FileParser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 28.08.13
 * Time: 00:15
 * To change this template use File | Settings | File Templates.
 */
public class Simulation {

    private Map<Integer,ReadDuration> readDurationMap = new TreeMap<Integer, ReadDuration>();
    private int simulationID;

    public Simulation(int simulationID) {
        this.simulationID=simulationID;
    }

    public void addDuration(int readCount, long duration) {

        if(readDurationMap.containsKey(readCount)) {
            readDurationMap.get(readCount).addStat(duration);
        }else {
            readDurationMap.put(readCount,new ReadDuration(duration));
        }
    }

    public int getSimulationID() {
        return simulationID;
    }

    public void printDurations() {
        for(Integer i : readDurationMap.keySet()) {
            System.out.println(i + " " + readDurationMap.get(i).getAvgDuration());
        }
    }
}
