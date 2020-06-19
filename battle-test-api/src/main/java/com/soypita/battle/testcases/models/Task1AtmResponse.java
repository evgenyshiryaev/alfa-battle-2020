package com.soypita.battle.testcases.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task1AtmResponse {

    private int deviceId;

    private String latitude;

    private String longitude;

    private String city;

    private String location;

}
