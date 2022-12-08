import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public class ACC {
    public String code; // 4 character string
    public int time;
    public String[] airportTable;
    public PriorityQueue<Flight> priorityQueue;
    public ArrayList<Flight> flights;
    public ArrayList<String> ATCs;
    public ArrayList<ATC> ATCsUNCODED;

    public ACC(String code) {
        this.code = code;
        time = 0;
        airportTable = new String[1000];
        flights = new ArrayList<>();
        priorityQueue = new PriorityQueue<>(31, new FlightComparator());
        ATCs = new ArrayList<>();
        ATCsUNCODED = new ArrayList<>();
    }

    public static String lastThreeDigits(int i) {
        i = i % 1000;
        String hashVal;
        if (i < 10) {
            hashVal = "00" + i;
        } else if (i < 100 && i > 10) {
            hashVal = "0" + i;
        } else {
            hashVal = String.valueOf(i);
        }
        return hashVal;
    }

    public int airportHash(String airportCode) {
        int hashVal = 0;
        for (int i = 0; i < 3; i++) {
            int asciiVal = (int) ((Math.pow(31, i)) * (int) (airportCode.charAt(i)));
            hashVal += asciiVal;

        }
        return hashVal;
    }

    public void placeTable(String airportCode) {
        String hashValSTR = lastThreeDigits(airportHash(airportCode));
        int hashVal = Integer.parseInt(hashValSTR);
        for (int i = 0; i < 1000; i++) {
            int index = (hashVal + i < 1000) ? hashVal + i : hashVal + i - 1000;
            if (Objects.equals(airportTable[index], null)) {
                airportTable[index] = airportCode + hashValSTR;
                ATCs.add(airportCode + hashValSTR);
                // System.out.println(airportTable[index]);
                return;
            }
        }
    }





}