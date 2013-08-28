package FileParser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{


    static String[] dirs = {
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\ttl\\60000",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\ttl\\60000",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\ttl\\90000",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\ttl\\120000",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\ttl\\180000",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\ttl\\360000",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\25",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\50",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\100",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\125",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\150",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\200",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\225",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\250",
//            "D:\\m\\dropbox\\Dropbox\\magisterka\\simulations\\27.08\\size\\300"
//            "C:\\Dropbox\\Dropbox\\magisterka\\simulations\\27.08_2\\wykresy\\1000",
//            "C:\\Dropbox\\Dropbox\\magisterka\\simulations\\27.08_2\\wykresy\\1500"

//            "E:\\Dropbox\\magisterka\\simulations\\28.08_NONE\\2\\Wykresy"

//            "E:\\Dropbox\\magisterka\\simulations\\28.08_CFM\\wykresy"
            "E:\\Dropbox\\magisterka\\simulations\\28.08_LRU\\wykresy"
    };

    static final String outputDir = "E:\\Dropbox\\magisterka\\simulations\\28.08_LRU\\wykresy";

    public static void main( String[] args )
    {
        for(String dir : dirs ) {

            String[] dirParts = dir.split(Pattern.quote("\\"));
//            String dirType = dirParts[7];
//            String outputFileName = "Wynik_"+dirType+"_"+dirParts[8]+".csv";
            String outputFileName = "Wynik.csv";

            ListMultimap<Integer, String> records = ArrayListMultimap.create();

            final File folder = new File(dir);

            if(folder.isDirectory()) {
                for(File f: folder.listFiles()) {
                    if(f.getName().endsWith(".txt") && ! f.getName().startsWith("Wynik")) {
                        System.out.println(f.getName());
                        records.putAll(readFile(f));

                    }
                }
            }
            writeFile(outputDir+outputFileName,records);
        }
    }

    private static ListMultimap<Integer, String> readFile(File f) {
            ListMultimap<Integer, String> records = ArrayListMultimap.create();
        try{
            FileInputStream fstream = new FileInputStream(f);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String key = "";

            String[] fileNameParts = f.getName().split(";");

            if(f.getAbsolutePath().contains("size")) key = fileNameParts[5];
            else key = fileNameParts[6];

            while ((strLine = br.readLine()) != null) {
//            if(! strLine.startsWith("E:")) {
                String[] s = strLine.split(";");
//                System.out.println("->"+key+":"+s[1]);
                records.put(Integer.parseInt(key),s[1]);
//            }
            }
            in.close();
            return records;
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
               return records;
        }
    }

    private static void writeFile(String fileName,ListMultimap<Integer, String> records) {
        try {

            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));

            String firstLine = "x;";
            TreeSet<Integer> keySet = new TreeSet<Integer>();
            keySet.addAll(records.keySet());


            for(Integer key : keySet) {
                firstLine+=key+";";
            }
            writer.println(firstLine);

            for(int i = 1; i<30; i++ ) {
                String line = i+";";
                for(Integer key : keySet) {
                    if(records.get(key).size() >i ) line+=records.get(key).get(i)+";";
                    else line+=";";
                }
                System.out.println(line);
                writer.println(line);
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
