package ru.alfa.task3.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "queue_log")
public class QueueLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate data;

    @Column
    private LocalTime startTimeOfWait;

    @Column
    private LocalTime endTimeOfWait;

    @Column
    private LocalTime endTimeOfService;

    @ManyToOne
    @JoinColumn(name = "branches_id")
    private Branches branches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getStartTimeOfWait() {
        return startTimeOfWait;
    }

    public void setStartTimeOfWait(LocalTime startTimeOfWait) {
        this.startTimeOfWait = startTimeOfWait;
    }

    public LocalTime getEndTimeOfWait() {
        return endTimeOfWait;
    }

    public void setEndTimeOfWait(LocalTime endTimeOfWait) {
        this.endTimeOfWait = endTimeOfWait;
    }

    public LocalTime getEndTimeOfService() {
        return endTimeOfService;
    }

    public void setEndTimeOfService(LocalTime endTimeOfService) {
        this.endTimeOfService = endTimeOfService;
    }

    public Branches getBranches() {
        return branches;
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }
}
