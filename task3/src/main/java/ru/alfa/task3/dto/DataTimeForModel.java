package ru.alfa.task3.dto;

public class DataTimeForModel {
    private Long branchesId;
    private Integer dayOgWeek;
    private Integer hourOfDay;

    public DataTimeForModel(Long branchesId, Integer dayOgWeek, Integer hourOfDay) {
        this.branchesId = branchesId;
        this.dayOgWeek = dayOgWeek;
        this.hourOfDay = hourOfDay;
    }

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public Integer getDayOgWeek() {
        return dayOgWeek;
    }

    public void setDayOgWeek(Integer dayOgWeek) {
        this.dayOgWeek = dayOgWeek;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(Integer hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataTimeForModel that = (DataTimeForModel) o;

        if (branchesId != null ? !branchesId.equals(that.branchesId) : that.branchesId != null) return false;
        if (dayOgWeek != null ? !dayOgWeek.equals(that.dayOgWeek) : that.dayOgWeek != null) return false;
        return hourOfDay != null ? hourOfDay.equals(that.hourOfDay) : that.hourOfDay == null;
    }

    @Override
    public int hashCode() {
        int result = branchesId != null ? branchesId.hashCode() : 0;
        result = 31 * result + (dayOgWeek != null ? dayOgWeek.hashCode() : 0);
        result = 31 * result + (hourOfDay != null ? hourOfDay.hashCode() : 0);
        return result;
    }
}
