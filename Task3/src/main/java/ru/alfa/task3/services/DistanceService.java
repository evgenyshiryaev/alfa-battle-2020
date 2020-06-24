package ru.alfa.task3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfa.task3.dto.BranchesWithDistance;
import ru.alfa.task3.entity.Branches;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistanceService {

    BranchesService branchesService;

    @Autowired
    public DistanceService(BranchesService branchesService) {
        this.branchesService = branchesService;
    }

    public BranchesWithDistance getNearBranch(Double lat, Double lon) {
        List<Branches> branches = branchesService.findAll();
        List<BranchesWithDistance> branchesWithDistances = branches.stream()
                .map(BranchesWithDistance::new)
                .collect(Collectors.toList());

        branchesWithDistances.forEach(val -> val.setDistance(countDistance(lat, lon, val.getLat(), val.getLon())));
        branchesWithDistances.sort(Comparator.comparing(BranchesWithDistance::getDistance));

        return branchesWithDistances.get(0);
    }

    public Double countDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        return distance(lat1, lat2, lon1, lon2, 0, 0);
    }

    public double distance(double lat1, double lat2, double lon1,
                           double lon2, double el1, double el2) {

        final int R = 6371; // радиус земля

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // конвертируем в метро

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
