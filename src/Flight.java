public class Flight {
    public String flightCode;
    public ATC departureATC;
    public ATC arrivalATC;
    public ACC acc;
    public boolean isNew;
    public int[] operationTimes;
    public String ACCCode;
    public int admissionTime;
    public int operationNumber;
    String departureAirport ;
    String arrivalAirport ;

    public Flight(String[] line) {
         departureAirport = line[3];
         arrivalAirport = line[4];
        String flightCode = line[1];
        ACCCode = line[2];
//        this.arrivalATC = new ATC(arrivalAirport + lastThreeDigits(airportHash(arrivalAirport)));
//        this.departureATC = new ATC(departureAirport + lastThreeDigits(airportHash(departureAirport)));
        this.flightCode = flightCode;
        //this.acc = new ACC(ACCCode);
        isNew = true;
        operationTimes = new int[21];
        operationTimes[0] = Integer.parseInt(line[0]);
        for (int i = 5; i < line.length; i++) {
            operationTimes[i - 5] = Integer.parseInt(line[i]);
        }
        admissionTime = Integer.parseInt(line[0]);
        operationNumber = 0;
    }

    public static int lastThreeDigits(int i) {
        return i % 1000;
    }

    public int airportHash(String airportCode) {
        int hashVal = 0;
        for (int i = 0; i < 3; i++) {
            int asciiVal = (int) ((Math.pow(31, i)) * (int) (airportCode.charAt(i)));
            hashVal += asciiVal;
        }
        return hashVal;
    }
}
