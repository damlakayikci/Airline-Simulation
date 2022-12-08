import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Project3 {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
       // File file = new File("/Users/damlakayikci/Desktop/Project3yeni/cases/inputs/case18.in");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        PrintStream outputFile = new PrintStream(args[1]);
        System.setOut(outputFile);

        String[] firstLine = reader.readLine().strip().split(" ");
        final int ACC_LINES = Integer.parseInt(firstLine[0]);
        final int FLIGHT_LINES = Integer.parseInt(firstLine[1]);
        ACC[] arrayACC = new ACC[ACC_LINES]; // There are ACC_LINES ACCs, one for every line

        for (int i = 0; i < ACC_LINES; i++) {
            // This operation runs for every ACC.
            String[] lineA = reader.readLine().strip().split(" ");
            arrayACC[i] = new ACC(lineA[0]);
            for (int j = 1; j < lineA.length; j++) {
                // These are the airports connected to that ACC.
                arrayACC[i].placeTable(lineA[j]);
                arrayACC[i].ATCsUNCODED.add(new ATC(lineA[j], arrayACC[i]));
            }
        }
        for (int i = 0; i < FLIGHT_LINES; i++) {
            Flight flight = new Flight(reader.readLine().strip().split(" "));
            for (int j = 0; j < ACC_LINES; j++) {
                // for every ACC line,
                // add flights to ACC's flight list and make ACC of the flight acc
                if (Objects.equals(arrayACC[j].code, flight.ACCCode)) {
                    arrayACC[j].flights.add(flight);
                    flight.acc = arrayACC[j];
                }
                for (ATC atc : arrayACC[j].ATCsUNCODED) {
                    if (Objects.equals(atc.code, flight.arrivalAirport)) {
                        flight.arrivalATC = atc;
                    }
                    if (Objects.equals(atc.code, flight.departureAirport)) {
                        flight.departureATC = atc;
                    }
                }
            }
        }

        for (ACC acc : arrayACC) {
            acc.priorityQueue.addAll(acc.flights);
            while (!acc.priorityQueue.isEmpty()) {
                Flight flight = acc.priorityQueue.poll();
                if (flight.operationNumber == 0 || flight.operationNumber == 10 || flight.operationNumber == 2 || flight.operationNumber == 12) {
                    // ACC
                    flight.isNew = false;
//                    if (acc.time < flight.admissionTime) {
//                        acc.time = flight.admissionTime;
//                    } else {
//                        flight.admissionTime = acc.time;
//                    }
                    acc.time = Math.max(acc.time,flight.admissionTime);
                    flight.admissionTime = acc.time;

                    if (flight.operationTimes[flight.operationNumber] <= 30) {
                        int incrementTime = flight.operationTimes[flight.operationNumber];
                        flight.admissionTime += incrementTime;
                        if (acc.time == 0) {
                            acc.time += flight.admissionTime;
                        } else{
                            acc.time += incrementTime;}

                        ++flight.operationNumber;

                        acc.priorityQueue.add(flight);
                    } else {
                        flight.admissionTime += 30;
                        flight.operationTimes[flight.operationNumber] -= 30;
                        acc.time += 30;
                        acc.priorityQueue.add(flight);
                    }

                } else if (flight.operationNumber == 3 || flight.operationNumber == 5 || flight.operationNumber == 7 || flight.operationNumber == 13 || flight.operationNumber == 15 || flight.operationNumber == 17 || flight.operationNumber == 9 || flight.operationNumber == 19) {
                    // departure ATC and arrival ATC
                    ATC atc = flight.arrivalATC;
                    if (flight.operationNumber < 10) {
                        atc = flight.departureATC;
                    }
//                    if (atc.time > flight.admissionTime) {
//                        flight.admissionTime = atc.time;
//                    }
                    atc.time = Math.max(atc.time,flight.admissionTime);
                    flight.admissionTime = atc.time;
                    int incrementTime = flight.operationTimes[flight.operationNumber];
                    flight.admissionTime += incrementTime;
                    if (atc.time == 0) {
                        atc.time = flight.admissionTime;
                    } else {
                        atc.time += incrementTime;
                    }
//                    acc.time += incrementTime;
                    if (flight.operationNumber == 9 || flight.operationNumber == 19) {
                        // from ATC to ACC
                        flight.isNew = true;
                    }
                    ++flight.operationNumber;
                    acc.priorityQueue.add(flight);


                } else if (flight.operationNumber == 20) {
                    // Last operation
                    flight.isNew = false;
                    acc.time = Math.max(acc.time,flight.admissionTime);
                    flight.admissionTime = acc.time;
                    if (flight.operationTimes[flight.operationNumber] <= 30) {
                        int incrementTime = flight.operationTimes[flight.operationNumber];
                        flight.admissionTime += incrementTime;
                        acc.time += incrementTime;
                    } else {
                        flight.admissionTime += 30;
                        flight.operationTimes[flight.operationNumber] -= 30;
                        acc.time += 30;
                        acc.priorityQueue.add(flight);
                    }
                }
                else{
                    // waitings
                    int incrementTime = flight.operationTimes[flight.operationNumber];
                    flight.admissionTime += incrementTime;
                    if (flight.operationNumber %2 == 1){
                        flight.isNew= true;
                    }
                    ++flight.operationNumber;
                    acc.priorityQueue.add(flight);
                }
//                int duration = flight.operationTimes[flight.operationNumber];
//                int time = acc.time;
//                if (Objects.equals(acc.code, "ZFSG"))
//                    System.out.println((time - duration) + " | " +"time: "+ time + " | " +"ftime: " + flight.admissionTime + " | " + flight.flightCode + " | " + acc.code + " | " + duration + " | " + flight.operationNumber);
            }
            System.out.print(acc.code + " " + acc.time + " ");
            for (int i = 0; i < acc.ATCs.size(); i++) {
                if (i != acc.ATCs.size() - 1) {
                    System.out.print(acc.ATCs.get(i) + " ");
                } else {
                    System.out.println(acc.ATCs.get(i));
                }
            }

        }
    }
}
