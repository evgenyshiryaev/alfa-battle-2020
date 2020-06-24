package ru.alfa.task3.dto;

import ru.alfa.task3.entity.Branches;

public class BranchesWithPredicting extends Branches {

    private Integer dayOfWeek;
    private Integer hourOfDay;
    private Long predicting;


    public BranchesWithPredicting(Branches branches) {
        this.setId(branches.getId());
        this.setAddress(branches.getAddress());
        this.setTitle(branches.getTitle());
        this.setLat(branches.getLat());
        this.setLon(branches.getLon());
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(Integer hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public Long getPredicting() {
        return predicting;
    }

    public void setPredicting(Long predicting) {
        this.predicting = predicting;
    }
}
