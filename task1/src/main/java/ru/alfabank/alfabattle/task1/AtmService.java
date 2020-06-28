package ru.alfabank.alfabattle.task1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.alfabank.alfabattle.task1.modelalfa.ATMDetails;


@Service
public class AtmService {

    @Autowired
    private AtmWebSocketClient webSocketClient;


    private final Map<Integer, ATMDetails> atmById = new HashMap<>();
    //private final Map<Integer, ATMStatus> atmStatusById = new HashMap<>();


    public void setAtms(List<ATMDetails> atms) {
        atms.forEach(atm -> atmById.put(atm.getDeviceId(), atm));
        //storeWebSocketResponseToFile(atms);
    }

    //public void setAtmStatuses(List<ATMStatus> atmStatuses) {
    //    atmStatuses.forEach(atmStatus -> atmStatusById.put(atmStatus.getDeviceId(), atmStatus));
    //}


    public ATMDetails getById(int id) {
        return atmById.get(id);
    }


    public int getNearest(String latitude, String longitude, boolean payments) {
        List<ATMDetails> nearestAtms = getNearestAtms(latitude, longitude);

        return nearestAtms.stream()
                .filter(atm -> !payments || "Y".equals(atm.getServices().getPayments()))
                .findFirst().get().getDeviceId();
    }


    public List<Integer> getNearestWithAlfik(String latitude, String longitude, int alfik) {
        List<ATMDetails> nearestAtms = getNearestAtms(latitude, longitude);

        AtomicInteger restAlfik = new AtomicInteger(alfik);

        return nearestAtms.stream()
                .map(ATMDetails::getDeviceId)
                .takeWhile(deviceId -> {
                    if (restAlfik.get() <= 0) {
                        return false;
                    }
                    int atmAfik = webSocketClient.getAtmAlfik(deviceId);
                    restAlfik.addAndGet(-atmAfik);
                    return true;
                })
                .collect(Collectors.toList());
        
    }


    private List<ATMDetails> getNearestAtms(String latitude, String longitude) {
        return atmById.values().stream()
                .sorted((atm0, atm1) -> Double.compare(
                        getDistance(atm0.getCoordinates().getLatitude(), atm0.getCoordinates().getLongitude(),
                                latitude, longitude),
                        getDistance(atm1.getCoordinates().getLatitude(), atm1.getCoordinates().getLongitude(),
                                latitude, longitude)))
                .collect(Collectors.toList());
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


    //private static void storeWebSocketResponseToFile(List<ATMDetails> atms) {
    //    Random random = ThreadLocalRandom.current();
    //    List<WebSocketResponse> atmResponces = atms.stream()
    //            .map(atm -> new WebSocketResponse(
    //                    atm.getDeviceId(), BigDecimal.valueOf(random.nextInt(1_000_000) + 1)))
    //            .collect(Collectors.toList());
    //    ObjectMapper objectMapper = new ObjectMapper();
    //    try {
    //        String json = objectMapper.writeValueAsString(atmResponces);
    //        Files.writeString(Paths.get("d:\\result.json"), json);
    //    } catch (Exception e) {
    //        log.error("Cannot store json", e);
    //    }
    //}

}
