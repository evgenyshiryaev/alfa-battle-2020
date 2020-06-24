package ru.alfabank.alfabattle2020.elasticredits.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonJsonList {

    private List<PersonJson> persons;

}
