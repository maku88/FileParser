package FileParser;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 28.08.13
 * Time: 00:04
 * To change this template use File | Settings | File Templates.
 */
public class SimulationLogParser {

    static String[] dirs ={
            "C:\\Dropbox\\Dropbox\\magisterka\\simulations\\27.08_TTL\\client\\sym_logs"
    };

    static Map<Integer, Simulation> simulationMap = new TreeMap<Integer, Simulation>();

    public static void main(String[] args) {

        for(String dir : dirs) {

            final File folder = new File(dir);
            if(folder.isDirectory()) {
                for(File f: folder.listFiles()) {
                    if(f.getName().endsWith(".log") ) {
                        readFile(f);
                    }
                }
            }
        }

        System.out.println(simulationMap.size());

    }

    private static List<Simulation> readFile(File f) {
        List<Simulation> simulations = new ArrayList<Simulation>();
        try{
            FileInputStream fstream = new FileInputStream(f);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int simulationID = 1;
            while ((strLine = br.readLine()) != null) {

                if(strLine.contains(" RES;")) {

                    String[] array = strLine.split(";");
                    addDuration(simulationID, Integer.parseInt(array[3]), Long.parseLong(array[6]));

                } else if(strLine.contains("WRITING LOG FILES")) {
                    simulationID++;
                }
            }
            in.close();
            return simulations;
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
            return simulations;
        }
    }

    private static void addDuration(int simulationID, int readCount, long duration) {
        if(simulationMap.containsKey(simulationID)) {
            simulationMap.get(simulationID).addDuration(readCount,duration);
        }else {
            Simulation s = new Simulation(simulationID);
            s.addDuration(readCount,duration);
            simulationMap.put(simulationID, s);
        }
    }


}
