package ru.alfabank.alfabattle.task1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.alfabank.alfabattle.task1.modelalfa.ATMDetails;
import ru.alfabank.alfabattle.task1.modelalfa.ATMStatus;


@Service
public class AtmService {

    private final Map<Integer, ATMDetails> atmById = new HashMap<>();
    private final Map<Integer, ATMStatus> atmStatusById = new HashMap<>();


    public void setAtms(List<ATMDetails> atms) {
        atms.forEach(atm -> atmById.put(atm.getDeviceId(), atm));
    }

    public void setAtmStatuses(List<ATMStatus> atmStatuses) {
        atmStatuses.forEach(atmStatus -> atmStatusById.put(atmStatus.getDeviceId(), atmStatus));
    }


    public ATMDetails getById(int id) {
        return atmById.get(id);
    }


    public int getNearest(String latitude, String longitude) {
        List<ATMDetails> nearestAtms = atmById.values().stream()
                .sorted((atm0, atm1) -> Double.compare(
                        getDistance(atm0.getCoordinates().getLatitude(), atm0.getCoordinates().getLongitude(),
                                latitude, longitude),
                        getDistance(atm1.getCoordinates().getLatitude(), atm1.getCoordinates().getLongitude(),
                                latitude, longitude)))
                .collect(Collectors.toList());

        return nearestAtms.get(0).getDeviceId();
    }


    private static double getDistance(
            String latitude0, String longitude0,
            String latitude1, String longitude1) {
        double lat0, long0, lat1, long1;
        try {
            lat0 = Double.parseDouble(latitude0);
            long0 = Double.parseDouble(longitude0);
            lat1 = Double.parseDouble(latitude1);
            long1 = Double.parseDouble(longitude1);
        } catch (Exception e) {
            // skip - max distance
            return Double.MAX_VALUE;
        }

        double latitudeSize = Math.abs(lat0 - lat1);
        double longitudeSize = Math.abs(long0 - long1);
        return Math.sqrt(Math.pow(latitudeSize, 2) + Math.pow(longitudeSize, 2));
    }

}
