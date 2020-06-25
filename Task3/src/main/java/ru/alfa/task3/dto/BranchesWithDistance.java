package ru.alfa.task3.dto;

import ru.alfa.task3.entity.Branches;

public class BranchesWithDistance extends Branches {
    private Long distance; //метры

    public BranchesWithDistance(Branches branches) {
        this.setId(branches.getId());
        this.setAddress(branches.getAddress());
        this.setTitle(branches.getTitle());
        this.setLat(branches.getLat());
        this.setLon(branches.getLon());
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

}
