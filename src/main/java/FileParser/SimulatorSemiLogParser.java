package FileParser;

import com.google.common.collect.ListMultimap;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 28.08.13
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class SimulatorSemiLogParser {

    static String[] dirs ={
            "E:\\Dropbox\\magisterka\\simulations\\28.08_CFM\\client\\1",
            "E:\\Dropbox\\magisterka\\simulations\\28.08_CFM\\client\\2",
            "E:\\Dropbox\\magisterka\\simulations\\28.08_CFM\\client\\3"

    };

    static Map<Integer, Simulation> simulationMap = new TreeMap<Integer, Simulation>();

    public static void main(String[] args) {

        String outputFile = "E:\\Dropbox\\magisterka\\simulations\\28.08_CFM\\mistakes.txt";

        for(String dir : dirs) {

            final File folder = new File(dir);
            if(folder.isDirectory()) {
                for(File f: folder.listFiles()) {
                    if(f.getName().endsWith(".txt") ) {
                        readFile(f);
                    }
                }
            }
        }

        System.out.println(simulationMap.size());

        for(Integer i : simulationMap.keySet()) {
            System.out.println(i + " " + simulationMap.get(i).getFalseCount() + " " +  simulationMap.get(i).getTrueCount());
        }


        writeFile(outputFile, simulationMap);



    }

    private static List<Simulation> readFile(File f) {
        List<Simulation> simulations = new ArrayList<Simulation>();
        try{
            FileInputStream fstream = new FileInputStream(f);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            String[] fileNameParts = f.getName().split("_");

            int simulationID = Integer.parseInt(fileNameParts[1]);

            while ((strLine = br.readLine()) != null) {

                if(strLine.contains("RES")) {
                    String[] lineParts = strLine.split(";");

                    boolean correct = Boolean.parseBoolean(lineParts[7]);
                    handleLine(simulationID,correct);

                }

            }
            in.close();
            return simulations;
        }catch (Exception e){//Catch exception if any
            e.printStackTrace();
            return simulations;
        }
    }

    private static void handleLine(int simulationID, boolean correct) {
        if(simulationMap.containsKey(simulationID)) {
            if(correct) simulationMap.get(simulationID).addTrue();
            else simulationMap.get(simulationID).addFalse();
        }else {
            Simulation s = new Simulation(simulationID);
            if(correct) s.addTrue();
            else s.addFalse();
            simulationMap.put(simulationID, s);
        }
    }


    private static void writeFile(String fileName,Map<Integer, Simulation> results) {
        try {

            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));

            for(Integer i : results.keySet()) {
                String line = i+";"+results.get(i).getTrueCount()+";"+results.get(i).getFalseCount();
                writer.println(line);
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
