package com.soypita.battle.testcases.models.task1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmResponse {

    private int deviceId;

    private String latitude;

    private String longitude;

    private String city;

    private String location;

    private boolean payments;

}
