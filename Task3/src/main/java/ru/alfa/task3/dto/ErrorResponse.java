package ru.alfa.task3.dto;

public class ErrorResponse {

    private String status;


    public ErrorResponse() {
    }

    public ErrorResponse(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
