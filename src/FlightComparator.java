import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight flight1, Flight flight2) {
        if (flight1.admissionTime < flight2.admissionTime) {
            return -1;
        } else if (flight1.admissionTime > flight2.admissionTime) {
            return 1;
        } else {
            if (flight1.isNew && !flight2.isNew) {
                return -1;
            } else if (!flight1.isNew && flight2.isNew) {
                return 1;
            } else {
                if (flight1.flightCode.compareTo(flight2.flightCode) < 0) {
                    return -1;
                } else if (flight1.flightCode.compareTo(flight2.flightCode) > 0) {
                    return 1;
                }
                return 0;
            }
        }
    }
}
