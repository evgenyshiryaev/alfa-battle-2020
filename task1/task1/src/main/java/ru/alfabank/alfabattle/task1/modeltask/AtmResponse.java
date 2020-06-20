package ru.alfabank.alfabattle.task1.modeltask;

import lombok.Data;


@Data
public class AtmResponse {

    private int deviceId;

    private String latitude;

    private String longitude;

    private String city;

    private String location;

    private boolean payments;

}
