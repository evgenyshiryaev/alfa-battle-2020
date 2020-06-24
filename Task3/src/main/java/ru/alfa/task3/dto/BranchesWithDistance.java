package ru.alfa.task3.dto;

import ru.alfa.task3.entity.Branches;

public class BranchesWithDistance extends Branches {
    private Double distance;

    public BranchesWithDistance(Branches branches) {
        this.setId(branches.getId());
        this.setAddress(branches.getAddress());
        this.setTitle(branches.getTitle());
        this.setLat(branches.getLat());
        this.setLon(branches.getLon());
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
